package wanbao.yongchao.com.wanbao.utils.utils

import android.content.Intent
import android.support.v4.app.Fragment
import com.muzhi.camerasdk.face.PickerCamera
import com.muzhi.camerasdk.model.CameraSdkParameterInfo
import wanbao.yongchao.com.wanbao.base.BaseActivity

import java.util.ArrayList

class CameraFace(private var face: CameraFace.CameraSelectFace) :PickerCamera.CameraImageCallBack{

    private lateinit var cameraPyx: PickerCamera
    private var cameraInfo = CameraSdkParameterInfo()

    //单选BaseActivity
    fun initSingleCameraSdk(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraPyx = PickerCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选Fragment
    fun initSingleCameraSdk(context: Fragment) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraPyx =  PickerCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }


    //多选BaseActivity
    fun initMultiCameraSdk(context: BaseActivity) {
        cameraInfo.isOpenDialog = false
        cameraPyx =  PickerCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选BaseActivity不提示dialog
    fun initCameraSdk(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraInfo.isOpenDialog=false
        cameraPyx =  PickerCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //多选Fragment
    fun initMultiCameraSdk(context: Fragment) {
        cameraInfo.isOpenDialog = false
        cameraInfo.maxImage = 9
        cameraPyx =  PickerCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //设置选择数量
    fun setImageNumber(imageNumber: Int) {
        cameraInfo.maxImage = imageNumber
    }

    //生命周期
    fun onActivityCameraResult(requestCode: Int, resultCode: Int, data: Intent?) {
        cameraPyx.let { it.onActivityCameraResult(requestCode, resultCode, data) }
    }


    //图片回调
    override fun returnCameraImageList(list: ArrayList<String>) {
        face.returnCameraImageList(list)
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

    interface CameraSelectFace {
        fun returnCameraImageList(list: ArrayList<String>)
    }
}