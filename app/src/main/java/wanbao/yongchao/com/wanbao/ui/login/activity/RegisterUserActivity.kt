package wanbao.yongchao.com.wanbao.ui.login.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import cn.qqtheme.framework.picker.DatePicker
import kotlinx.android.synthetic.main.activity_register_user.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.UserBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.RegisterUserBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.QiniuPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.RegisterUserPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.QiniuView
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.RegisterUserView
import wanbao.yongchao.com.wanbao.ui.login.utils.TagsUtils
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.pickers.pickerUtils
import wanbao.yongchao.com.wanbao.utils.utils.CameraSelect
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class RegisterUserActivity :BaseActivity(), CameraSelect.CameraSelectFace,QiniuView,RegisterUserView{
    override fun sendSucceedImage(fileUrlList: ArrayList<String>) {
        ImageLoad.setUserHead("http://pic.bixinyule.com/"+fileUrlList[0],registerUserHead)
        avatar="http://pic.bixinyule.com/"+fileUrlList[0]
        if (!file!!.exists()) {
            return
        } else {
            deletePic(file!!.absolutePath)
        }
        if (edit_user_name.text!=null&&edit_user_name.text.toString()!=""&&edit_user_name.text.toString().length>0&&edit_user_age.text!=null&&edit_user_age.text.toString()!=""&&edit_user_age.text.toString().length>0&&(chooseMale||chooseFemale)){
            btn_register_user.isEnabled=true
            btn_register_user.setTextColor(Color.parseColor("#ffffff"))
        }else{
            btn_register_user.isEnabled=false
            btn_register_user.setTextColor(Color.parseColor("#40ffffff"))
        }
    }

    override fun sendFileErrorImage() {

    }

    override fun getRegisterUserRequest(data: UserBean) {
        Toast.Tips("注册成功")
        TagsUtils.setTagList(ArrayList<TagsBean.DataBean>())
        try {
            DBUtils.DelBusiness()
        }catch (e:Exception){}
        var user=UserBean.DataBeanX()
        user.data=UserBean.DataBeanX.DataBean()
        user.token=data.data.token
        user.data.accountState=data.data.data.accountState
        user.data.authState=data.data.data.authState
        user.data.avatar=data.data.data.avatar
        user.data.nickname=data.data.data.nickname
        user.data.role=data.data.data.role
        user.data.id=data.data.data.id
        DBUtils.setUserDB(user)
        wanbao.yongchao.com.wanbao.db.user.setChange(true)
        intentUtils.intentMain()
    }

    override fun getRegisterUserError() {

    }

    override fun returnCameraImageList(list: ArrayList<String>) {
        if (list!=null&&list.size>0) {
            Log.e("测试","开始上传")
//        if (type==2) {
            qiniuPresentr.setImage(list)
            file = File(list[0])

//        }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraSelect.onActivityCameraResult(requestCode, resultCode, data)
    }

    private val qiniuPresentr by lazy { QiniuPresenter(this,this,this) }
    private var cameraSelect = CameraSelect(this)
    private var file: File?=null
    private var avatar=""
    private var isFirst=true
    private var chooseMale=false
    private var chooseFemale=false
    private var birthday=""
    private var gender=0
    private val presenter by lazy { RegisterUserPresenter(this,this,this) }


    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_register_user

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        cameraSelect.initSinglePhotoSdk(this)
    }

    override fun clickListener() {
        Click.viewClick(register_user_back).subscribe {
            finish()
            TagsUtils.setTagList(ArrayList<TagsBean.DataBean>())
        }

//        Click.viewClick(register_user_close).subscribe {
//            ShowDialog.showCustomDialogNoTitle(this,"退出将放弃注册\n是否确定？","放弃注册","取消",object : DialogInterface.OnClickListener{
//                override fun onClick(dialog: DialogInterface, which: Int) {
//                    when (which) {
//                        DialogInterface.BUTTON_POSITIVE -> {
//                            dialog.dismiss()
//                            TagsUtils.setTagList(ArrayList<String>())
//                            //TODO
//                        }
//                        DialogInterface.BUTTON_NEGATIVE -> {
//                            dialog.dismiss()
//                        }
//                    }
//                }
//            })
//        }

        Click.viewClick(registerUserHead).subscribe {
            cameraSelect.openCamera()
        }

        edit_user_name.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var edit=edit_user_name.text.toString()
                var str=stringFilter(edit)
                if (!edit.equals(str)){
                    edit_user_name.setText(str)
                    edit_user_name.setSelection(str.length)
                    Toast.Tips("名称仅支持中英文与数字\n         且不超12个字")
                }

                if (str.length>0&&avatar!=""&&edit_user_age.text!=null&&edit_user_age.text.toString().length>0&&(chooseFemale||chooseMale)){
                    btn_register_user.isEnabled=true
                    btn_register_user.setTextColor(Color.parseColor("#ffffff"))
                }else{
                    btn_register_user.isEnabled=false
                    btn_register_user.setTextColor(Color.parseColor("#40ffffff"))
                }
            }
        })

        Click.viewClick(edit_user_age).subscribe {
            getTime()
        }

        Click.viewClick(tv_sex_female).subscribe {
            if (isFirst){
                ShowDialog.showCustomDialogNoTitle(this,"完成注册后性别将不能更改","知道了",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        dialog.dismiss()
                        isFirst=false
                    }
                })
            }
            if (!chooseFemale){
                tv_sex_female.setBackgroundResource(R.drawable.tv_sex_bg)
                tv_sex_female.setTextColor(Color.parseColor("#FFFFFF"))
                chooseFemale=true
                if (chooseMale){
                    tv_sex_male.setBackgroundResource(R.drawable.tv_sex_default_bg)
                    tv_sex_male.setTextColor(Color.parseColor("#40FFFFFF"))
                    chooseMale=false
                }
            }
            gender=2

            if (avatar!=""&&edit_user_name.text!=null&&edit_user_name.text.toString().length>0&&edit_user_age.text!=null&&edit_user_age.text.toString().length>0){
                btn_register_user.isEnabled=true
                btn_register_user.setTextColor(Color.parseColor("#ffffff"))
            }else{
                btn_register_user.isEnabled=false
                btn_register_user.setTextColor(Color.parseColor("#40ffffff"))
            }
        }

        Click.viewClick(tv_sex_male).subscribe {
            if (isFirst){
                ShowDialog.showCustomDialogNoTitle(this,"完成注册后性别将不能更改","知道了",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        dialog.dismiss()
                        isFirst=false
                    }
                })
            }
            gender=1
            if (!chooseMale){
                tv_sex_male.setBackgroundResource(R.drawable.tv_sex_bg)
                tv_sex_male.setTextColor(Color.parseColor("#FFFFFF"))
                chooseMale=true
                if (chooseFemale){
                    tv_sex_female.setBackgroundResource(R.drawable.tv_sex_default_bg)
                    tv_sex_female.setTextColor(Color.parseColor("#40FFFFFF"))
                    chooseFemale=false
                }
            }

            if (avatar!=""&&edit_user_name.text!=null&&edit_user_name.text.toString().length>0&&edit_user_age.text!=null&&edit_user_age.text.toString().length>0){
                btn_register_user.isEnabled=true
                btn_register_user.setTextColor(Color.parseColor("#ffffff"))
            }else{
                btn_register_user.isEnabled=false
                btn_register_user.setTextColor(Color.parseColor("#40ffffff"))
            }
        }

        Click.viewClick(layout_none_tag).subscribe {
            intentUtils.intentRegisterTags()
        }

        Click.viewClick(tv_register_tag).subscribe {
            intentUtils.intentRegisterTags()
        }

        Click.viewClick(btn_register_user).subscribe {
            //TODO 完成用户注册
            var body=RegisterUserBody()
            body.account= user.getAccount()
            body.avatar=avatar
            body.nickname=edit_user_name.text.toString()
            if (user.getLocationLat()!=""&&user.getLocationLng()!=""){
                body.latitude=user.getLocationLat()
                body.longitude=user.getLocationLng()
                Log.e("测试",body.latitude+"  "+body.longitude)
            }
            body.birthday=birthday
            body.phoneModel=android.os.Build.BRAND
            body.gender=gender.toString()
            if (TagsUtils.getTagList().size>0) {
                var tagId = ArrayList<Int>()
                TagsUtils.getTagList().forEach {
                    tagId.add(it.id)
                }
                body.tagIds=tagId
            }
            presenter.getRegisterUser(body)
        }

    }

    override fun onResume() {
        super.onResume()
        if (TagsUtils.getTagList().size>0){
            layout_none_tag.visibility=View.GONE
            layout_tag.visibility=View.VISIBLE
            var list=ArrayList<String>()
            TagsUtils.getTagList().forEach {
                list.add(it.description)
            }
            userTagsLab.setList(list)
        }else{
            layout_none_tag.visibility=View.VISIBLE
            layout_tag.visibility=View.GONE
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
                edit_user_age.setText("$year/$month/$day，${age}岁")
            }else{
                edit_user_age.setText("$year/$month/$day，${age-1}岁")
            }
            if (avatar!=""&&edit_user_name.text!=null&&edit_user_name.text.toString().length>0&&(chooseFemale||chooseMale)){
                btn_register_user.isEnabled=true
                btn_register_user.setTextColor(Color.parseColor("#ffffff"))
            }else{
                btn_register_user.isEnabled=false
                btn_register_user.setTextColor(Color.parseColor("#40ffffff"))
            }

//            birthday="$year-$month-$day"+" 00:00:00"
        })
        picker.show()
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
            var p=Pattern.compile(regEx)
            var m=p.matcher(str)
            s=m.replaceAll("").trim()
        }catch (e:Exception){

        }
        return  s
    }
}