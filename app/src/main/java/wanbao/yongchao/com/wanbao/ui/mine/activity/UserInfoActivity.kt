package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.content.Intent
import android.graphics.Color
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import cn.qqtheme.framework.picker.DatePicker
import kotlinx.android.synthetic.main.activity_user_info.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.QiniuPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.QiniuView
import wanbao.yongchao.com.wanbao.ui.login.utils.TagsUtils
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditUserBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.UserInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditUserBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.UserInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.MineUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditUserView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineUserView
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.pickers.pickerUtils
import wanbao.yongchao.com.wanbao.utils.utils.CameraSelect
import wanbao.yongchao.com.wanbao.utils.utils.Click
import java.io.File
import java.util.*

class UserInfoActivity:BaseActivity(), CameraSelect.CameraSelectFace, QiniuView, MineUserView , EditUserView {
    override fun getEditUserRequest(data: EditUserBean) {
        user.setChange(true)
    }

    override fun returnCameraImageList(list: java.util.ArrayList<String>) {
        if (list!=null&&list.size>0) {
            Log.e("测试","开始上传")
            qiniuPresentr.setImage(list)
            file = File(list[0])
        }
    }

    override fun sendSucceedImage(fileUrlList: ArrayList<String>) {
        ImageLoad.setUserHead("http://pic.bixinyule.com/"+fileUrlList[0],iv_header)
        avatar="http://pic.bixinyule.com/"+fileUrlList[0]

        var body=EditUserBody()
        body.type="2"
        body.avatar=avatar
        editPresenter.setEditUser(body)
        if (!file!!.exists()) {
            return
        } else {
            deletePic(file!!.absolutePath)
        }
    }

    override fun sendFileErrorImage() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraSelect.onActivityCameraResult(requestCode, resultCode, data)
    }

    private val editPresenter by lazy { EditUserPresenter(this,this,this) }

    private val qiniuPresentr by lazy { QiniuPresenter(this,this,this) }
    private var cameraSelect = CameraSelect(this)
    private var file: File?=null
    private var avatar=""
    private var birthday=""
    private var sign=""

    private val infoPresenter by lazy { MineUserPresenter(this,this,this) }
    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_user_info

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        cameraSelect.initSinglePhotoSdk(this)
    }

    override fun clickListener() {

    }

    override fun getUserInfoRequest(data: UserInfoBean) {
        ImageLoad.setUserHead(data.data.avatar,iv_header)
        tv_name.text=data.data.nickname
        tv_age.text=data.data.birthday.split(" ")[0].replace("-","/")+",${data.data.age}岁"
        if (data.data.gender=="1"){
            tv_sex.text="男"
        }else{
            tv_sex.text="女"
        }

        if (data.data.signature!=null&&data.data.signature!=""){
            sign=data.data.signature
            tv_signer.text=data.data.signature
        }else{
            sign=""
        }

        if (data.data.tags!=null&&data.data.tags.size>0){
            tv_tag.text=" "
            layout_tags.visibility= View.VISIBLE
            var list=ArrayList<String>()
            var tags=ArrayList<TagsBean.DataBean>()
            data.data.tags.forEach {
                list.add(it.description)
                var bean=TagsBean.DataBean()
                bean.description=it.description
                bean.id=it.id
                tags.add(bean)
            }
            userTagsLab.setList(list)
            TagsUtils.setTagList(tags)
        }else{
            TagsUtils.setTagList(ArrayList<TagsBean.DataBean>())
            tv_tag.hint="添加个人说明，让大家更了解你"
            layout_tags.visibility= View.GONE
        }

        Click.viewClick(iv_header).subscribe {
            cameraSelect.openCamera()
        }

        Click.viewClick(layout_name).subscribe {
            intentUtils.intentEditUserName(data.data.nickname)
        }

        Click.viewClick(layout_birthday).subscribe {
            getTime()
        }

        Click.viewClick(layout_signer).subscribe {
            intentUtils.intentEditUserSign(sign)
        }

        Click.viewClick(layout_tag).subscribe {
            intentUtils.intentRegisterTags("编辑")
        }

        Click.viewClick(iv_back).subscribe {
            finish()
        }

    }

    override fun getUserInfoError() {

    }

    override fun onResume() {
        super.onResume()
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        if (user != null) {
            var info = user.loadAll()
            if (info != null && info.size > 0) {
                infoPresenter.getUserInfo(UserInfoBody(info.get(0).token))
            }else{
                intentUtils.intentLogin()
            }
        }else{
            intentUtils.intentLogin()
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


    /**
     * 设置生日
     */
    val c = Calendar.getInstance()//可以对每个时间域单独修改
    var years = c.get(Calendar.YEAR)
    var months = c.get(Calendar.MONTH) + 1
    var dates = c.get(Calendar.DATE)
    fun getTime() {
        var picker = DatePicker(this)
        pickerUtils.showPicker(picker)
        picker.setSelectedItem(years, months, dates)
        picker.setBackgroundColor(Color.parseColor("#2C2C2F"))
        picker.setTopBackgroundColor(Color.parseColor("#212123"))
        picker.setLabelTextColor(Color.parseColor("#d6ffffff"))
        picker.setTitleText("")
        picker.setTitleTextColor(Color.parseColor("#333333"))
        picker.setTitleTextSize(16)
        picker.setTextColor(Color.parseColor("#FCC725"))

        picker.setOnDatePickListener(cn.qqtheme.framework.picker.DatePicker.OnYearMonthDayPickListener { year, month, day ->
            years = year.toInt()
            months = month.toInt()
            dates = day.toInt()
            birthday="$year-$month-$day 00:00:00"
            var age=c.get(Calendar.YEAR)-years
            if ((c.get(Calendar.MONTH) + 1)>=months&&c.get(Calendar.DATE)>=dates){
                tv_age.setText("$year/$month/$day，${age}岁")
            }else{
                tv_age.setText("$year/$month/$day，${age-1}岁")
            }
            var body=EditUserBody()
            body.type="4"
            body.birthday=birthday
            editPresenter.setEditUser(body)
//            birthday="$year-$month-$day"+" 00:00:00"
        })
        picker.show()
    }
}