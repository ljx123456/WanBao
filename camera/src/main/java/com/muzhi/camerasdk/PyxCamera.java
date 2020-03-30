package com.muzhi.camerasdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.muzhi.camerasdk.activity.CutActivityCamera;
import com.muzhi.camerasdk.activity.PhotoPickActivityCamera;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;
import com.muzhi.camerasdk.utils.FileUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.functions.Consumer;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.muzhi.camerasdk.model.CameraSdkParameterInfo.EXTRA_PARAMETER;
import static com.muzhi.camerasdk.model.CameraSdkParameterInfo.TAILOR_PICTURE_FROM_CAMERA;
import static com.muzhi.camerasdk.model.CameraSdkParameterInfo.TAKE_PICTURE_PREVIEW;
import static com.muzhi.camerasdk.model.CameraSdkParameterInfo.TAKE_PICTURE_SINGLE_CAMERA;


public class PyxCamera implements SelectCameraDialog.SelectCameraDialogFace {

    private File mTmpFile;
    private Activity activity;
    private Fragment fragment;
    private SelectCameraDialog selectCameraDialog;
    private CameraSdkParameterInfo cameraSdkParameterInfo;
    private CameraImageCallBack cameraImageCallBack;

    public void setCameraImageCallBack(CameraImageCallBack cameraImageCallBack) {
        this.cameraImageCallBack = cameraImageCallBack;
    }

    public PyxCamera(Activity activity, CameraSdkParameterInfo cameraSdkParameterInfo) {
        this.activity = activity;
        this.cameraSdkParameterInfo = cameraSdkParameterInfo;
        this.selectCameraDialog = new SelectCameraDialog(activity);
        this.selectCameraDialog.setDialogFace(this);
    }

    public PyxCamera(Fragment activity, CameraSdkParameterInfo cameraSdkParameterInfo) {
        this.fragment = activity;
        this.activity = fragment.getActivity();
        this.cameraSdkParameterInfo = cameraSdkParameterInfo;
        this.selectCameraDialog = new SelectCameraDialog(fragment.getContext());
        this.selectCameraDialog.setDialogFace(this);
    }

    public void setCameraSdkParameterInfo(CameraSdkParameterInfo cameraSdkParameterInfo) {
        this.cameraSdkParameterInfo = cameraSdkParameterInfo;
    }

    public void openCamera() {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) openCameraSdk();
                else Toast.makeText(activity, "请打开相机，内存读取权限", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openCameraSdk() {
        if (cameraSdkParameterInfo.isSingleMode()
                && cameraSdkParameterInfo.isCutoutImage()
                && cameraSdkParameterInfo.isOpenDialog()) {
            selectCameraDialog.showDialog();
        } else {
            openPhotoPick();
        }
    }

    @Override
    public void cameraBtn() {
        showCameraAction();
    }

    @Override
    public void photoBtn() {
        openPhotoPick();
    }

    //打开相册
    private void openPhotoPick() {
        Bundle b = new Bundle();
        b.putSerializable(EXTRA_PARAMETER, cameraSdkParameterInfo);
        Intent intent = new Intent(activity, PhotoPickActivityCamera.class);
        intent.putExtras(b);
        if (fragment != null) {
            fragment.startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);
        } else {
            activity.startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);
        }
    }


    //系统拍照(单选、裁剪调用)
    private void showCameraAction() {
        if (cameraSdkParameterInfo.isSingleMode() && cameraSdkParameterInfo.isCutoutImage()) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {

                mTmpFile = FileUtils.createTmpFile(activity);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(mTmpFile));

                if (fragment != null) {
                    fragment.startActivityForResult(cameraIntent, TAKE_PICTURE_SINGLE_CAMERA);
                } else {
                    activity.startActivityForResult(cameraIntent, TAKE_PICTURE_SINGLE_CAMERA);
                }
            } else {
                Toast.makeText(activity, R.string.camerasdk_msg_no_camera, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //适配7.0以上的调用系统相机
    private Uri getUriForFile(File file) {
        return FileProvider.getUriForFile(activity, "wanbao.yongchao.com.wanbao", file);
    }


    public void onActivityCameraResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //相册、图片回调
            case CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY:
                if (data != null) setImageListData(data);
                break;
            //多选回调
            case TAKE_PICTURE_PREVIEW:
                if (data != null) setImageDeResult(data);
                break;
            //相机回调
            case TAKE_PICTURE_SINGLE_CAMERA:
                setSingleModeResult();
                break;
            //裁剪回调
            case TAILOR_PICTURE_FROM_CAMERA:
                if (data != null) setImageListData(data);
                break;
        }
    }

    private void setImageDeResult(Intent data) {
        if (data == null) {
            return;
        }
        int position = data.getIntExtra("position", -1);
        cameraSdkParameterInfo.getImageList().remove(position);
    }


    private void setSingleModeResult() {
        if (cameraSdkParameterInfo.isSingleMode()) {
            cameraSdkParameterInfo.getImageList().clear();
            cameraSdkParameterInfo.getImageList().add(mTmpFile.getPath());
            stateCutActivity();
        }
    }


    //单张裁剪
    private void stateCutActivity() {
        Bundle b = new Bundle();
        b.putSerializable(EXTRA_PARAMETER, cameraSdkParameterInfo);
        Intent intent = new Intent(activity, CutActivityCamera.class);
        intent.putExtras(b);

        if (fragment != null) fragment.startActivityForResult(intent, TAILOR_PICTURE_FROM_CAMERA);
        else activity.startActivityForResult(intent, TAILOR_PICTURE_FROM_CAMERA);

    }


    private ArrayList<String> getImageList(Bundle bundle) {
        if (bundle != null) {
            cameraSdkParameterInfo = (CameraSdkParameterInfo) bundle.getSerializable(EXTRA_PARAMETER);
            ArrayList<String> list = cameraSdkParameterInfo.getImageList();
            return list;
        } else {
            return null;
        }
    }


    //删除照片调用
    public void deleteImage(int index) {
        cameraSdkParameterInfo.getImageList().remove(index);
    }


    private void setImageListData(Intent data) {
        if (data == null) {
            return;
        }
        if (mTmpFile!=null&&mTmpFile.exists()){
//            deletePic(mTmpFile.getAbsolutePath());
//            Log.e("测试","存在");
            mTmpFile.delete();
        }else {
//            Log.e("测试","不存在");
        }
        ArrayList<String> list = getImageList(data.getExtras());
        cameraImageCallBack.returnCameraImageList(list);
    }


    public interface CameraImageCallBack {

        void returnCameraImageList(ArrayList<String> list);

    }
}
