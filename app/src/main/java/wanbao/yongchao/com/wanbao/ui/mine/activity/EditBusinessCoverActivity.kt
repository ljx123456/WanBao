package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.content.Intent
import android.util.Log
import kotlinx.android.synthetic.main.activity_edit_business_cover.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.image.ImageInfo
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.QiniuPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.QiniuView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditBusinessBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditBusinessBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditBusinessPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditBusinessView
import wanbao.yongchao.com.wanbao.ui.release.adapter.PictureAdapter
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.CameraSelect
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File

class EditBusinessCoverActivity:BaseActivity() , CameraSelect.CameraSelectFace, QiniuView ,EditBusinessView{
    override fun getEditBusinessRequest(data: EditBusinessBean) {
        user.setChange(true)
        Toast.Tips("修改成功")
        finish()
    }

    override fun returnCameraImageList(list: java.util.ArrayList<String>) {
        Log.e("测试返回结果",list.size.toString())
        if (list!=null&&list.size>0&& File(list[0]).length()>0) {
            list.forEach {
                Log.e("测试返回结果图片",it)
            }
            photoPresenter.setImage(list)
        }
    }

    override fun sendSucceedImage(fileUrlList: ArrayList<String>) {
        var list=ArrayList<String>()
        fileUrlList.forEach {
            list.add("http://pic.bixinyule.com/"+it)
        }
        if (adapter!=null){
            adapter!!.addList(list)
        }else{
            adapter= PictureAdapter(this,9)
            adapter!!.addList(list)
            gv_picture.adapter=adapter
        }
    }

    override fun sendFileErrorImage() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraSelect.onActivityCameraResult(requestCode, resultCode, data)
    }

    private var adapter: PictureAdapter?=null
    private var cameraSelect = CameraSelect(this)

    private val photoPresenter by lazy { QiniuPresenter(this,this,this) }
    private val presenter by  lazy { EditBusinessPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_edit_business_cover

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        if (intent.getStringArrayListExtra("images")!=null&&intent.getStringArrayListExtra("images").size>0){
            adapter= PictureAdapter(this,9)
            adapter!!.addList(intent.getStringArrayListExtra("images"))
            gv_picture.adapter=adapter
        }
        tv_tips.text="您最多可以上传9张图以展示自己的商铺，\n" +
                "照片至少保留一张"
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }

        gv_picture.setOnItemClickListener { parent, view, position, id ->
            if (adapter!=null&&adapter!!.list.size<9&&position==parent.childCount-1){
//                cameraSelect.openCamera()
                cameraSelect.initMultiCameraSdk(this,(9-adapter!!.list.size))
                cameraSelect.openCamera()
            }else{
                var imagelist = ArrayList<ImageInfo>()
                adapter!!.list.forEach {
                    imagelist.add(ImageInfo(it, false, 2))
                }
                intentUtils.intentImage(false, imagelist, position)
            }
        }

        Click.viewClick(tv_keep).subscribe {
            if (adapter!=null&&adapter!!.list.size>0){
                var body=EditBusinessBody()
                body.type="1"
                body.images=adapter!!.list
                presenter.setEditBusiness(body)
            }else{
                Toast.Tips("至少需要有一张图片")
            }
        }
    }
}