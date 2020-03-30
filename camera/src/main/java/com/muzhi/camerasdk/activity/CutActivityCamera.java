package com.muzhi.camerasdk.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.muzhi.camerasdk.R;
import com.muzhi.camerasdk.library.utils.PhotoUtils;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;
import com.muzhi.camerasdk.model.Constants;
import com.muzhi.camerasdk.view.CropImageView;

import java.io.File;
import java.util.ArrayList;


public class CutActivityCamera extends CameraBaseActivity {

    private CropImageView mCropView;
    private TextView btn_done;

    private ProgressDialog progressDialog;
    private CameraSdkParameterInfo parameterInfo;
    private RadioGroup layout_crop, layout_tab;
    private LinearLayout layout_rotation;
    private Bitmap sourceMap;
    private boolean isFlag=false;

    // Handle机制
    @SuppressLint("HandlerLeak")
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            String path = PhotoUtils.saveAsBitmap(mContext, mCropView.getCroppedBitmap());
            if (PhotoPickActivityCamera.instance != null) {
                getPhotoResultComplate(path);
            } else {
                getForResultComplate(path);
            }
            finish();
        }
    };


    //相册裁剪返回
    public void getPhotoResultComplate(String path) {

        PhotoPickActivityCamera.instance.getForResultComplate(path);
        PhotoPickActivityCamera.instance.finish();
        PhotoPickActivityCamera.instance = null;

    }

    //相机裁剪的图片
    public void getForResultComplate(String path) {
        ArrayList<String> list = new ArrayList<String>();
        list.add(path);

        Intent intent = new Intent();
        parameterInfo.setImageList(list);
        Bundle b = new Bundle();
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, parameterInfo);
        intent.putExtras(b);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camerasdk_activity_cut);
        showLeftIcon();
        setActionBarTitle("裁剪");

        Bundle b = getIntent().getExtras();
        try {
            parameterInfo = (CameraSdkParameterInfo) b.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER);
        } catch (Exception e) {
        }

        findViews();

        if (parameterInfo != null) {
            if (parameterInfo.isShowTailor()) {
                layout_tab.setVisibility(View.GONE);
                layout_crop.setVisibility(View.GONE);
            } else {
                layout_tab.setVisibility(View.VISIBLE);
                layout_crop.setVisibility(View.VISIBLE);
            }
            File file = new File(parameterInfo.getImageList().get(0));
            if(file.exists()){
                isFlag=true;
            }else {
                isFlag=false;
            }
            sourceMap = PhotoUtils.getBitmap(parameterInfo.getImageList().get(0));
            mCropView.setImageBitmap(sourceMap);
        } else {
            sourceMap = Constants.bitmap;
            mCropView.setImageBitmap(sourceMap);
        }
    }

    private void findViews() {
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        btn_done = (TextView) findViewById(R.id.camerasdk_title_txv_right_text);
        btn_done.setVisibility(View.VISIBLE);
        btn_done.setText("确定");

        layout_crop = (RadioGroup) findViewById(R.id.layout_crop);
        layout_tab = (RadioGroup) findViewById(R.id.layout_tab);
        layout_rotation = (LinearLayout) findViewById(R.id.layout_rotation);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("加载中···");
        progressDialog.setCancelable(false);
        initEvent();

    }

    private void initEvent() {
        btn_done.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isFlag){
                    done();
                }else {
                    finish();
                }

            }
        });

        if (parameterInfo!=null&&parameterInfo.isFlag()){
            mCropView.setCropMode(CropImageView.CropMode.RATIO_3_2);
            Log.e("测试","剪裁3:2");
        }else {
            mCropView.setCropMode(CropImageView.CropMode.RATIO_CUSTOM);
            mCropView.setCustomRatio(1, 1);
            Log.e("测试","剪裁1:1");
        }
        layout_tab.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                if (arg1 == R.id.button_crop) {
                    layout_crop.setVisibility(View.VISIBLE);
                    layout_rotation.setVisibility(View.GONE);
                } else {
                    layout_crop.setVisibility(View.GONE);
                    layout_rotation.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.ratation_left).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceMap = PhotoUtils.rotateImage(sourceMap, -90);
                mCropView.setImageBitmap(sourceMap);
            }
        });
        findViewById(R.id.ratation_right).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceMap = PhotoUtils.rotateImage(sourceMap, 90);
                mCropView.setImageBitmap(sourceMap);
            }
        });
        findViewById(R.id.ratation_vertical).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceMap = PhotoUtils.reverseImage(sourceMap, -1, 1);
                mCropView.setImageBitmap(sourceMap);
            }
        });
        findViewById(R.id.ratation_updown).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceMap = PhotoUtils.reverseImage(sourceMap, 1, -1);
                mCropView.setImageBitmap(sourceMap);
            }
        });


    }

    private void done() {
        if (parameterInfo != null) {
            new Thread() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.show();
                            mHandler.sendEmptyMessage(0x111);
                        }
                    });

                }
            }.start();

        } else {
            Constants.bitmap = mCropView.getCroppedBitmap();
            setResult(Constants.RequestCode_Croper);
            finish();
        }
    }


}
