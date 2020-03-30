package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_blogger_auth.*

import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.QiniuPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.QiniuView
import wanbao.yongchao.com.wanbao.ui.mine.dialog.AuthBloggerTypeDialog
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthRealNameBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthRealNameBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.AuthRealNamePresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.AuthRealNameView
import wanbao.yongchao.com.wanbao.ui.release.dialog.SelectPhotoDialog
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.CameraSelect
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.lang.Exception
import java.util.ArrayList
import java.util.regex.Pattern

class AuthBloggerActivity:BaseActivity(), CameraSelect.CameraSelectFace, SelectPhotoDialog.SelectPhotoDialogFace , QiniuView,AuthBloggerTypeDialog.AuthType, AuthRealNameView {
    override fun getAuthRealNameRequest(data: AuthRealNameBean) {
        user.setChange(true)
        intentUtils.intentAuthCenter()
    }

    override fun setAuthType(id: Int, type: String) {
        tv_auth_type.text=type
        typeId=id

        if (idCardFront!=""&&idCardBack!=""&&handPhoto!=""&&typeId!=-1&&bloggerPhoto!=""){
            btn_blogger.isEnabled=true
            btn_blogger.setBackgroundColor(Color.parseColor("#FCC725"))
            btn_blogger.setTextColor(Color.parseColor("#000000"))
        }else{
            btn_blogger.isEnabled=false
            btn_blogger.setBackgroundColor(Color.parseColor("#3B3B3D"))
            btn_blogger.setTextColor(Color.parseColor("#40FFFFFF"))
        }
    }

    override fun sendSucceedImage(fileUrlList: ArrayList<String>) {

        layout_blogger_default.visibility= View.GONE
        layout_blogger.visibility= View.VISIBLE
        ImageLoad.setImage("http://pic.bixinyule.com/"+fileUrlList[0],iv_blogger)
        bloggerPhoto="http://pic.bixinyule.com/"+fileUrlList[0]

        if (file!=null) {
            if (!file!!.exists()) {
                return
            } else {
//                file!!.delete()
                deletePic(file!!.absolutePath)
            }
        }

        if (idCardFront!=""&&idCardBack!=""&&handPhoto!=""&&typeId!=-1){
            btn_blogger.isEnabled=true
            btn_blogger.setBackgroundColor(Color.parseColor("#FCC725"))
            btn_blogger.setTextColor(Color.parseColor("#000000"))
        }else{
            btn_blogger.isEnabled=false
            btn_blogger.setBackgroundColor(Color.parseColor("#3B3B3D"))
            btn_blogger.setTextColor(Color.parseColor("#40FFFFFF"))
        }
    }

    override fun sendFileErrorImage() {

    }

    override fun photoBtn() {
        flag=true
        val rxPermission= RxPermissions(this)
        rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA).subscribe {
            if (it) {
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Log.e("测试","hahah")
                    uri = FileProvider.getUriForFile(this, "wanbao.yongchao.com.wanbao", cameraPath!!)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } else {
                    uri = Uri.fromFile(cameraPath!!)
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(intent, 2)
            }else{
                Toast.Tips("请打开相机权限")
            }
        }
    }

    override fun videoBtn() {
        flag=false
        cameraSelect.openCamera()
    }

    override fun returnCameraImageList(list: ArrayList<String>) {
        Log.e("测试返回结果",list.size.toString())
        if (list!=null&&list.size>0&& File(list[0]).length()>0) {
            list.forEach {
                Log.e("测试返回结果图片",it)
            }
            photoPresenter.setImage(list)
//            if (list.size==1) {
//                file = File(list[0])
//            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraSelect.onActivityCameraResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imgPath = cameraPath!!.absolutePath
            } else {
                imgPath = uri!!.getEncodedPath()
            }
            Log.e("拍照返回图片路径:", imgPath)
            var list= ArrayList<String>()
            list.add(imgPath)
            file= File(imgPath)
            photoPresenter.setImage(list)
        }
    }

    private val photoPresenter by lazy { QiniuPresenter(this,this,this) }
    private val presenter by lazy { AuthRealNamePresenter(this,this,this) }

    private var cameraSelect = CameraSelect(this)
    private lateinit var dialog : SelectPhotoDialog
    private var imgPath=""
    private var url=""
    private var cameraPath: File?=null
    private var uri: Uri?=null
    private var file: File?=null
    private var flag=false

    private var name=""
    private var card=""
    private var idCardFront=""
    private var idCardBack=""
    private var handPhoto=""
    private var bloggerPhoto=""
    private var typeId=-1

    private lateinit var typeDialog:AuthBloggerTypeDialog

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_blogger_auth

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        name=intent.getStringExtra("name")
        card=intent.getStringExtra("card")
        idCardFront=intent.getStringExtra("front")
        idCardBack=intent.getStringExtra("back")
        handPhoto=intent.getStringExtra("hand")
        cameraSelect.initSingleCamera(this)
        dialog= SelectPhotoDialog(this)
        dialog.setCamera()
        dialog.setDialogFace(this)
        cameraPath= File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg")
        typeDialog= AuthBloggerTypeDialog(this)
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }

        Click.viewClick(layout_blogger_default).subscribe {
            dialog.showDialog()
        }

        Click.viewClick(tv_blogger_change).subscribe {
            dialog.showDialog()
        }


        Click.viewClick(tv_auth_type).subscribe {
            typeDialog.show(supportFragmentManager,"")
        }


//        edit_auth_info.addTextChangedListener(object :TextWatcher{
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                var edit=edit_auth_info.text.toString()
//                var str=stringFilter(edit)
//                if (!edit.equals(str)){
//                    edit_auth_info.setText(str)
//                    edit_auth_info.setSelection(str.length)
//                    Toast.Tips("名称仅支持中英文与数字\n         且不超12个字")
//                }
//
//
//                if (idCardFront!=""&&idCardBack!=""&&handPhoto!=""&&str.length>0&&typeId!=-1&&bloggerPhoto!=""){
//                    btn_blogger.isEnabled=true
//                    btn_blogger.setBackgroundColor(Color.parseColor("#FCC725"))
//                    btn_blogger.setTextColor(Color.parseColor("#000000"))
//                }else{
//                    btn_blogger.isEnabled=false
//                    btn_blogger.setBackgroundColor(Color.parseColor("#3B3B3D"))
//                    btn_blogger.setTextColor(Color.parseColor("#40FFFFFF"))
//                }
//
//            }
//        })


        Click.viewClick(btn_blogger).subscribe {
            var body=AuthRealNameBody()
            body.name=name
            body.idCardNo=card
            body.idCardFront=idCardFront
            body.idCardBack=idCardBack
            body.handPhoto=handPhoto
            body.type="2"
            body.typeId=typeId.toString()
//            body.subject=edit_auth_info.text.toString()
            body.industryData=bloggerPhoto
            presenter.getAuthRealName(body)
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

    private fun stringFilter(str:String):String{
        var s=""
        try {
            var regEx="[^a-zA-Z0-9\\u4E00-\\u9FA5]"//正则表达式
            var p= Pattern.compile(regEx)
            var m=p.matcher(str)
            s=m.replaceAll("").trim()
        }catch (e: Exception){

        }
        return  s
    }
}