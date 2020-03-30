package wanbao.yongchao.com.wanbao.utils.utils

import android.content.Intent
import android.support.v4.app.Fragment
import com.muzhi.camerasdk.PhotoCamera
//import com.muzhi.camerasdk.PyxCamera
import com.muzhi.camerasdk.model.CameraSdkParameterInfo
import wanbao.yongchao.com.wanbao.base.BaseActivity

import java.lang.Exception
import java.util.ArrayList

class PhotoSelet(private var face: PhotoSelectFace) : PhotoCamera.CameraImageCallBack {


    private lateinit var cameraPyx: PhotoCamera
    private var cameraInfo = CameraSdkParameterInfo()


    //单选BaseActivity
    fun initSingleCameraSdk(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.setCutoutImage(true)
        cameraInfo.isShowTailor = true
        cameraPyx = PhotoCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选不剪裁BaseActivity
    fun initSinglePhoto(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.setCutoutImage(false)
        cameraInfo.isShowTailor = true
        cameraPyx = PhotoCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选Fragment
    fun initSingleCameraSdk(context: Fragment) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraPyx = PhotoCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }


    //多选BaseActivity
    fun initMultiCameraSdk(context: BaseActivity,size:Int) {
        cameraInfo.isOpenDialog = true
        cameraInfo.isShowTailor = true
        cameraInfo.isCutoutImage=false
        cameraInfo.maxImage=size
        cameraPyx = PhotoCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选BaseActivity不提示dialog
    fun initCameraSdk(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraInfo.isOpenDialog=false
        cameraPyx =PhotoCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //多选Fragment
    fun initMultiCameraSdk(context: Fragment) {
        cameraInfo.isOpenDialog = false
        cameraInfo.maxImage = 9
        cameraPyx = PhotoCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //设置选择数量
    fun setImageNumber(imageNumber: Int) {
        cameraInfo.maxImage = imageNumber
    }

    //生命周期
    fun onActivityPhotoResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            cameraPyx.let { it.onActivityPhotoResult(requestCode, resultCode, data) }
        }catch (e:Exception){

        }
    }


    //图片回调
    override fun returnPhotoImageList(list: ArrayList<String>) {
        face.returnPhotoImageList(list)
    }


    //删除图片
    fun deleteItem(position: Int) {
        cameraPyx.deleteImage(position)
    }


    //打开相册
    fun openCamera() = cameraPyx.let { it.openCamera() }


    //编辑资料专用-打开相册
    fun userInfoOpenCamera(imageNumber: Int) {
        cameraInfo = CameraSdkParameterInfo()
        cameraInfo.isOpenDialog = false
        cameraInfo.maxImage = imageNumber
        cameraPyx.setCameraSdkParameterInfo(cameraInfo)
        cameraPyx.openCamera()
    }



    interface PhotoSelectFace {
        fun returnPhotoImageList(list: ArrayList<String>)
    }

}