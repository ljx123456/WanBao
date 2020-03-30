package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.content.Intent
import android.os.Handler
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import kotlinx.android.synthetic.main.activity_edit_user_cover.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.QiniuPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.QiniuView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditUserBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditUserBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditUserView
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.utils.CameraSelect
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File

class EditUserAvatarActivity:BaseActivity() , CameraSelect.CameraSelectFace, QiniuView, EditUserView {
    override fun getEditUserRequest(data: EditUserBean) {
        Toast.Tips("已更改")
        user.setChange(true)
        ImageLoad.setImage(cover, iv_cover)

    }

    override fun returnCameraImageList(list: java.util.ArrayList<String>) {
        Log.e("测试返回结果",list.size.toString())
        if (list!=null&&list.size>0&& File(list[0]).length()>0) {
            list.forEach {
                Log.e("测试返回结果图片",it)
            }
            qiniuPresenter.setImage(list)
            if (list.size==1) {
                file = File(list[0])
            }
        }
    }

    override fun sendSucceedImage(fileUrlList: ArrayList<String>) {
        cover="http://pic.bixinyule.com/" + fileUrlList[0]
//        var list=ArrayList<String>()
//        list.add(cover)
        if (!file!!.exists()) {
            return
        } else {
//                file!!.delete()
            deletePic(file!!.absolutePath)
        }
        var body= EditUserBody()
        body.avatar=cover
        body.type="2"
        presenter.setEditUser(body)
    }

    override fun sendFileErrorImage() {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraSelect.onActivityCameraResult(requestCode, resultCode, data)
    }

    private var cameraSelect = CameraSelect(this)
    private var file: File?=null
    private val qiniuPresenter by lazy { QiniuPresenter(this,this,this) }
    private var cover=""
    private val presenter by lazy { EditUserPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_edit_user_cover

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        cameraSelect.initSinglePhotoSdk(this)
        Handler().postDelayed(object :Runnable{
            override fun run() {
                var params=iv_cover.layoutParams
                params.width=iv_cover.width
                params.height=iv_cover.width
                iv_cover.layoutParams=params
            }
        },500)
        ImageLoad.setImage(intent.getStringExtra("url"),iv_cover)
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }

        Click.viewClick(btn_cover).subscribe {
            cameraSelect.openCamera()
        }
    }

    private fun deletePic(path:String){
        if(!TextUtils.isEmpty(path)){
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val contentResolver = this.getContentResolver()//cutPic.this是一个上下文
//            val url =  MediaStore.Images.Media.DATA + "='" + path + "'"
//            //删除图片
//            contentResolver.delete(uri, url, null)
            val url =  MediaStore.Images.Media.DATA + "=?"
            //删除图片
            contentResolver.delete(uri, url, arrayOf(path))
        }
    }
}