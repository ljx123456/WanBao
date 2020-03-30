package wanbao.yongchao.com.wanbao.utils.utils

import android.content.Intent
import android.support.v4.app.Fragment
import com.muzhi.camerasdk.PyxCamera
import com.muzhi.camerasdk.model.CameraSdkParameterInfo
import wanbao.yongchao.com.wanbao.base.BaseActivity
import java.lang.Exception

import java.util.*

/**
 * Created by Administrator on 2018/8/11 0011.
 */
class CameraSelect(private var face: CameraSelectFace) : PyxCamera.CameraImageCallBack {


    private lateinit var cameraPyx: PyxCamera
    private var cameraInfo = CameraSdkParameterInfo()


    //单选BaseActivity
    fun initSingleCameraSdk(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraPyx = PyxCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选不打开dialogBaseActivity
    fun initSinglePhotoSdk(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraInfo.isOpenDialog=false
        cameraPyx = PyxCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选不剪裁BaseActivity
    fun initSingleCamera(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = false
        cameraInfo.isShowTailor = true
        cameraPyx = PyxCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选Fragment
    fun initSingleCameraSdk(context: Fragment) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraPyx = PyxCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }


    //多选BaseActivity
    fun initMultiCameraSdk(context: BaseActivity,maxIamge:Int) {
        cameraInfo.isOpenDialog = false
        cameraInfo.maxImage = maxIamge
        cameraPyx = PyxCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选BaseActivity不提示dialog
    fun initCameraSdk(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraInfo.isOpenDialog=false
        cameraPyx = PyxCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //单选BaseActivity不提示dialog剪裁比例3:2
    fun initCameraSdkFlag(context: BaseActivity) {
        cameraInfo.isSingleMode = true
        cameraInfo.isCutoutImage = true
        cameraInfo.isShowTailor = true
        cameraInfo.isFlag=true
        cameraInfo.isOpenDialog=false
        cameraPyx = PyxCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //多选Fragment
    fun initMultiCameraSdk(context: Fragment) {
        cameraInfo.isOpenDialog = false
        cameraInfo.maxImage = 9
        cameraPyx = PyxCamera(context, cameraInfo)
        cameraPyx.setCameraImageCallBack(this)
    }

    //设置选择数量
    fun setImageNumber(imageNumber: Int) {
        cameraInfo.maxImage = imageNumber
    }

    //生命周期
    fun onActivityCameraResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            cameraPyx.let { it.onActivityCameraResult(requestCode, resultCode, data) }
        }catch (e: Exception){

        }
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