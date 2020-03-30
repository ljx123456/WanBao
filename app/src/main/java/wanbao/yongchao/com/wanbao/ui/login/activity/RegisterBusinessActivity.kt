package wanbao.yongchao.com.wanbao.ui.login.activity

import android.content.Intent
import android.graphics.Color
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import cn.qqtheme.framework.picker.TimePicker
import kotlinx.android.synthetic.main.activity_register_business.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.ui.login.dialog.BusinessTypeDialog
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.pickers.pickerUtils
import wanbao.yongchao.com.wanbao.utils.utils.CameraSelect
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.lang.Exception
import java.util.regex.Pattern
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.dialog.AreaDialog
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.BusinessBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.QiniuPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.RegisterBusinessPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.QiniuView
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.RegisterBusinessView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import kotlin.collections.ArrayList


class RegisterBusinessActivity : BaseActivity(), CameraSelect.CameraSelectFace,QiniuView,AreaDialog.CallBack,BusinessTypeDialog.CallBack,RegisterBusinessView{
    override fun sendSucceedImage(fileUrlList: ArrayList<String>) {

        ImageLoad.setUserHead("http://pic.bixinyule.com/"+fileUrlList[0],registerBusinessHead)
        avatar="http://pic.bixinyule.com/"+fileUrlList[0]
        if (!file!!.exists()) {
            return
        } else {
            deletePic(file!!.absolutePath)
        }
//            if (!file!!.exists()) {
//                return
//            } else {
//                deletePic(file!!.absolutePath)
//            }

        if (avatar!=""&&edit_business_name.text!=null&&edit_business_name.text.isNotEmpty()&&edit_business_phone.text!=null&&edit_business_phone.text.isNotEmpty()
                &&edit_business_time.text!=null&&edit_business_time.text.isNotEmpty()&&type_id!=0&&a_id!=0&&edit_business_address!=null
                &&edit_business_address.text.isNotEmpty()&&edit_business_map.text!=null&&edit_business_map.text.isNotEmpty()){
            btn_register_business.isEnabled=true
            btn_register_business.setTextColor(Color.parseColor("#ffffff"))
        }else{
            btn_register_business.isEnabled=false
            btn_register_business.setTextColor(Color.parseColor("#40ffffff"))
        }
    }

    override fun sendFileErrorImage() {

    }

    override fun getRegisterBusinessRequest(data: BusinessBean) {
        Toast.Tips("注册成功")
        try {
            DBUtils.DelUser()
        }catch (e:Exception){}
        var user=BusinessBean.DataBeanX()
        user.data=BusinessBean.DataBeanX.DataBean()
        user.token=data.data.token
        user.data.accountState=data.data.data.accountState
        user.data.authState=data.data.data.authState
        user.data.avatar=data.data.data.avatar
        user.data.nickname=data.data.data.nickname
        user.data.role=data.data.data.role
        user.data.id=data.data.data.id
        DBUtils.setBusinessDB(user)
        wanbao.yongchao.com.wanbao.db.user.setChange(true)
        intentUtils.intentMain()
    }

    override fun getRegisterBusinessError() {

    }

    override fun type(type_id: Int) {
        this.type_id=type_id
        if (avatar!=""&&edit_business_name.text!=null&&edit_business_name.text.isNotEmpty()&&edit_business_phone.text!=null&&edit_business_phone.text.isNotEmpty()
                &&edit_business_time.text!=null&&edit_business_time.text.isNotEmpty()&&a_id!=0&&edit_business_address!=null
                &&edit_business_address.text.isNotEmpty()&&edit_business_map.text!=null&&edit_business_map.text.isNotEmpty()){
            btn_register_business.isEnabled=true
            btn_register_business.setTextColor(Color.parseColor("#ffffff"))
        }else{
            btn_register_business.isEnabled=false
            btn_register_business.setTextColor(Color.parseColor("#40ffffff"))
        }
    }

    override fun choose(p_id: Int, c_id: Int, a_id: Int) {
        this.p_id=p_id
        this.c_id=c_id
        this.a_id=a_id
        if (avatar!=""&&edit_business_name.text!=null&&edit_business_name.text.isNotEmpty()&&edit_business_phone.text!=null&&edit_business_phone.text.isNotEmpty()
                &&edit_business_time.text!=null&&edit_business_time.text.isNotEmpty()&&type_id!=0&&edit_business_address!=null
                &&edit_business_address.text.isNotEmpty()&&edit_business_map.text!=null&&edit_business_map.text.isNotEmpty()){
            btn_register_business.isEnabled=true
            btn_register_business.setTextColor(Color.parseColor("#ffffff"))
        }else{
            btn_register_business.isEnabled=false
            btn_register_business.setTextColor(Color.parseColor("#40ffffff"))
        }
    }

    private var cameraSelect = CameraSelect(this)
    private var file: File?=null
    private lateinit var picker :TimePicker
    private lateinit var header:View
    private lateinit var footer:View
    private lateinit var tv_start:TextView
    private lateinit var tv_end:TextView
    private lateinit var tv_sumit:TextView
    private lateinit var tv_cancel:TextView
    private var type_id=0
    private var isStartTime=true
    private var startHour=0
    private var endHour=0
    private var startMin=0
    private var endMin=0
    private var openTime=""
    private var endTime=""
    private var province=""
    private var city=""
    private var area=""
    private var p_id=0
    private var c_id=0
    private var a_id=0
    private var isFlag=true//是否第一次选取时间
    private val presenter=RegisterBusinessPresenter(this,this,this)
    private val qiniuPresenter by lazy { QiniuPresenter(this,this,this) }
    private var avatar=""



    override fun returnCameraImageList(list: ArrayList<String>) {
        if (list!=null&&list.size>0) {
            Log.e("测试", "开始上传")
            //TODO 上传头像与删除剪切头像
//        if (type==2) {
            qiniuPresenter.setImage(list)
            file = File(list[0])

        }
    }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_register_business

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        cameraSelect.initSinglePhotoSdk(this)
        picker = TimePicker(this)

    }

    override fun clickListener() {

        Click.viewClick(register_business_back).subscribe {
            finish()
        }

        Click.viewClick(registerBusinessHead).subscribe {
            cameraSelect.openCamera()
        }

        edit_business_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var edit=edit_business_name.text.toString()
                var str=stringFilter(edit)
                if (!edit.equals(str)){
                    edit_business_name.setText(str)
                    edit_business_name.setSelection(str.length)
                    Toast.Tips("名称仅支持中英文与数字\n         且不超16个字")
                }else{
                    if (avatar!=""&&edit_business_name.text!=null&&edit_business_name.text.isNotEmpty()&&edit_business_phone.text!=null&&edit_business_phone.text.isNotEmpty()
                            &&edit_business_time.text!=null&&edit_business_time.text.isNotEmpty()&&type_id!=0&&a_id!=0&&edit_business_address!=null
                            &&edit_business_address.text.isNotEmpty()&&edit_business_map.text!=null&&edit_business_map.text.isNotEmpty()){
                        btn_register_business.isEnabled=true
                        btn_register_business.setTextColor(Color.parseColor("#ffffff"))
                    }else{
                        btn_register_business.isEnabled=false
                        btn_register_business.setTextColor(Color.parseColor("#40ffffff"))
                    }
                }

            }
        })

        edit_business_address.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                edit_business_map.text=""
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        Click.viewClick(edit_business_time).subscribe {
            getTime()
        }

        Click.viewClick(edit_business_type).subscribe {
            var dialog=BusinessTypeDialog(this,edit_business_type)
            dialog.setCallBack(this)
            dialog.show(supportFragmentManager,"")
        }

        Click.viewClick(edit_business_area).subscribe {
//            var dialog=BusinessAreaDialog(edit_business_area)
            var dialog= AreaDialog(edit_business_area)
            dialog.setCallBack(this)
            dialog.show(supportFragmentManager,"")
        }


        Click.viewClick(edit_business_map).subscribe {

            if (edit_business_area.text!=null&&edit_business_area.text.isNotEmpty()) {
                if (edit_business_area.text != null && edit_business_area.text.toString().length > 0) {
                    var sp = edit_business_area.text.toString().split(" ")
                    province = sp[0]
                    city = sp[1]
                    area = sp[2]
                }
                if (edit_business_address.text != null && edit_business_address.text.toString().length > 0) {
                    intentUtils.intentRegisterBusinessMap(province, city, area, edit_business_address.text.toString())
                } else {
                    intentUtils.intentRegisterBusinessMap(province, city, area)
                }
            }else{
                Toast.Tips("请先选择区域地址")
            }
        }

        Click.viewClick(btn_register_business).subscribe {
            var body=BusinessBody()
            body.account=user.getAccount()
            //TODO 头像
            body.avatar=avatar
            body.nickname=edit_business_name.text.toString()
            body.phoneModel=android.os.Build.BRAND
            Log.e("测试",android.os.Build.BRAND)
            Log.e("测试",android.os.Build.MODEL)
            body.phone=edit_business_phone.text.toString()
            body.openTime=openTime
            body.closeTime=endTime
            Log.e("测试o",openTime)
            Log.e("测试e",endTime)
            body.typeId=type_id.toString()
            body.provinceId=p_id.toString()
            body.cityId=c_id.toString()
            body.countyId=a_id.toString()
            body.address=edit_business_address.text.toString()
            body.latitude=user.getlat()
            body.longitude=user.getlng()
            if (user.getLocationId()!="") {
                body.locationId = user.getLocationId()
                Log.e("测试loca", user.getLocationId())
            }
            presenter.getRegisterBusiness(body)
        }




    }

    override fun onResume() {
        super.onResume()

        if (user.getlat()!=""&&user.getlng()!=""&&user.getBusinessAddress()!=""){
            Log.e("测试",user.getBusinessAddress())
            Log.e("测试",user.getlat())
            Log.e("测试loca", user.getLocationId())
            edit_business_address.setText(user.getBusinessAddress())
            edit_business_map.text="已选取"
        }else{
            edit_business_map.text=""
        }

        if (avatar!=""&&edit_business_name.text!=null&&edit_business_name.text.isNotEmpty()&&edit_business_phone.text!=null&&edit_business_phone.text.isNotEmpty()
                &&edit_business_time.text!=null&&edit_business_time.text.isNotEmpty()&&type_id!=0&&a_id!=0&&edit_business_address!=null
                &&edit_business_address.text.isNotEmpty()&&edit_business_map.text!=null&&edit_business_map.text.isNotEmpty()){
            btn_register_business.isEnabled=true
            btn_register_business.setTextColor(Color.parseColor("#ffffff"))
        }else{
            btn_register_business.isEnabled=false
            btn_register_business.setTextColor(Color.parseColor("#40ffffff"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        user.setlat("")
        user.setlng("")
        user.setBusinessAddress("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraSelect.onActivityCameraResult(requestCode, resultCode, data)
    }


    fun getTime() {
        if (isFlag){
            isFlag=false
            pickerUtils.showTimePicker(picker)
//        picker.setSelectedItem(years, months, dates)
            picker.setBackgroundColor(Color.parseColor("#2C2C2F"))
            picker.setTopBackgroundColor(Color.parseColor("#212123"))
            picker.setLabelTextColor(Color.parseColor("#d6ffffff"))
            picker.setTitleText("")
            picker.setTitleTextColor(Color.parseColor("#333333"))
            picker.setTitleTextSize(16)
            picker.setTextColor(Color.parseColor("#FCC725"))
            picker.setSelectedItem(6,0)

//        picker.setOnDatePickListener(cn.qqtheme.framework.picker.DatePicker.OnYearMonthDayPickListener { year, month, day ->
//            years = year.toInt()
//            months = month.toInt()
//            dates = day.toInt()
//            var age=c.get(Calendar.YEAR)-years
//            if ((c.get(Calendar.MONTH) + 1)>=months&&c.get(Calendar.DATE)>=dates){
//                edit_user_age.setText("$year/$month/$day，${age}岁")
//            }else{
//                edit_user_age.setText("$year/$month/$day，${age-1}岁")
//            }
//            if (file!=null&&edit_user_name.text!=null&&edit_user_name.text.toString().length>0&&(chooseFemale||chooseMale)){
//                btn_register_user.isEnabled=true
//                btn_register_user.setTextColor(Color.parseColor("#ffffff"))
//            }else{
//                btn_register_user.isEnabled=false
//                btn_register_user.setTextColor(Color.parseColor("#40ffffff"))
//            }
//
////            birthday="$year-$month-$day"+" 00:00:00"
//        })
//        Click.viewClick(picker.submitButton).subscribe {
//            Toast.Tips("确定")
//        }
//
//        Click.viewClick(picker.cancelButton).subscribe {
//            Toast.Tips("确定")
//        }
            header=View.inflate(this,R.layout.layout_time_pick_top,null)
            picker.setHeaderView(header)
            footer=View.inflate(this,R.layout.layout_time_pick_bottom,null)
            picker.setFooterView(footer)
            tv_start=footer.findViewById<TextView>(R.id.tv_start_time)
            tv_end=footer.findViewById<TextView>(R.id.tv_end_time)
            tv_sumit=header.findViewById<TextView>(R.id.tv_summit)
            tv_cancel=header.findViewById<TextView>(R.id.tv_cancel)

            tv_start.setTextColor(Color.parseColor("#FCC725"))
            tv_end.setTextColor(Color.parseColor("#d9ffffff"))

//        header.findViewById<TextView>(R.id.tv_summit).setOnClickListener {
//            picker.dismiss()
//        }
            tv_start.text = "当日  ${picker.selectedHour}:${picker.selectedMinute}"
            startHour=picker.selectedHour.toInt()
            startMin=picker.selectedMinute.toInt()
            openTime="${picker.selectedHour}:${picker.selectedMinute}:00"
            picker.setOnWheelListener(object :TimePicker.OnWheelListener{
                override fun onHourWheeled(p0: Int, p1: String?) {
                    if (isStartTime){
                        startHour=p0
                        startMin=picker.selectedMinute.toInt()
                        Log.e("测试",p1)
                        tv_start.text="当日  ${p1}:${picker.selectedMinute}"
                        openTime="${p1}:${picker.selectedMinute}:00"
                        if (tv_end.text.toString().contains("日")){
                            if (startHour>endHour){
                                tv_end.text=tv_end.text.toString().replaceBeforeLast("日","次")
                            }else if (startHour==endHour&&startMin>endMin){
                                tv_end.text=tv_end.text.toString().replaceBeforeLast("日","次")
                            }else{
                                tv_end.text=tv_end.text.toString().replaceBeforeLast("日","当")
                            }
                        }
                    }else {
                        endHour=p0
                        endMin=picker.selectedMinute.toInt()
                        endTime="${p1}:${picker.selectedMinute}:00"
//                    if (footer.findViewById<TextView>(R.id.tv_end_time).text.toString().contains("日")){
//                        if (startHour>endHour){
//                            footer.findViewById<TextView>(R.id.tv_end_time).text=footer.findViewById<TextView>(R.id.tv_end_time).text.toString().replaceBeforeLast("日","次")
//                        }else if (startHour==endHour&&startMin>endMin){
//                            footer.findViewById<TextView>(R.id.tv_end_time).text=footer.findViewById<TextView>(R.id.tv_end_time).text.toString().replaceBeforeLast("日","次")
//                        }else{
//                            footer.findViewById<TextView>(R.id.tv_end_time).text=footer.findViewById<TextView>(R.id.tv_end_time).text.toString().replaceBeforeLast("日","当")
//                        }
//                    }else{
                        if (startHour>endHour){
                            tv_end.text="次日  ${p1}:${picker.selectedMinute}"
                        }else if (startHour==endHour&&startMin>endMin){
                            tv_end.text="次日  ${p1}:${picker.selectedMinute}"
                        }else{
                            tv_end.text="当日  ${p1}:${picker.selectedMinute}"
                        }
//                    }
                    }

                    if (tv_start.text.toString().contains("日")&&tv_end.text.toString().contains("日")){
                        tv_sumit.setTextColor(Color.parseColor("#d9ffffff"))
                    }else{
                        tv_sumit.setTextColor(Color.parseColor("#d6ffffff"))
                    }
                }

                override fun onMinuteWheeled(p0: Int, p1: String?) {
                    if (isStartTime) {
                        startMin=p0
                        startHour=picker.selectedHour.toInt()
                        tv_start.text = "当日  ${picker.selectedHour}:${p1}"
                        openTime="${picker.selectedHour}:${p1}:00"
                        if (tv_end.text.toString().contains("日")) {
                            if (startHour > endHour) {
                                tv_end.text = tv_end.text.toString().replaceBefore("日","次")
                            } else if (startHour == endHour && startMin > endMin) {
                                tv_end.text = tv_end.text.toString().replaceBefore("日","次")
                            } else {
                                tv_end.text = tv_end.text.toString().replaceBefore("日","当")
                            }
                        }
                    }else{
                        endMin=p0
                        endHour=picker.selectedHour.toInt()
                        endTime="${picker.selectedHour}:${p1}:00"
                        if (startHour>endHour){
                            tv_end.text = "次日  ${picker.selectedHour}:${p1}"
                        }else if (startHour==endHour&&startMin>endMin){
                            tv_end.text = "次日  ${picker.selectedHour}:${p1}"
                        }
                        else{
                            tv_end.text = "当日  ${picker.selectedHour}:${p1}"
                        }
                    }
                    if (tv_start.text.toString().contains("日")&&tv_end.text.toString().contains("日")){
                        tv_sumit.setTextColor(Color.parseColor("#d9ffffff"))
                    }else{
                        tv_sumit.setTextColor(Color.parseColor("#d6ffffff"))
                    }
                }
            })

            Click.viewClick( tv_sumit).subscribe {
                if (tv_start.text.toString().contains("日")&&tv_end.text.toString().contains("日")){
                    edit_business_time.text=footer.findViewById<TextView>(R.id.tv_start_time).text.toString()+" ~ "+footer.findViewById<TextView>(R.id.tv_end_time).text.toString()
                    picker.dismiss()
                }else{
                    Toast.Tips("请选择营业时间和歇业时间")
                }
                if (avatar!=""&&edit_business_name.text!=null&&edit_business_name.text.isNotEmpty()&&edit_business_phone.text!=null&&edit_business_phone.text.isNotEmpty()
                        &&edit_business_time.text!=null&&edit_business_time.text.isNotEmpty()&&type_id!=0&&a_id!=0&&edit_business_address!=null
                        &&edit_business_address.text.isNotEmpty()&&edit_business_map.text!=null&&edit_business_map.text.isNotEmpty()){
                    btn_register_business.isEnabled=true
                    btn_register_business.setTextColor(Color.parseColor("#ffffff"))
                }else{
                    btn_register_business.isEnabled=false
                    btn_register_business.setTextColor(Color.parseColor("#40ffffff"))
                }
                header.findViewById<TextView>(R.id.tv_title).text="营业时间"
                isStartTime=true

            }

            Click.viewClick( tv_cancel).subscribe {
                isStartTime=true
                header.findViewById<TextView>(R.id.tv_title).text="营业时间"
                picker.dismiss()

            }

            Click.viewClick(tv_start).subscribe {
                isStartTime=true
                tv_start.setTextColor(Color.parseColor("#FCC725"))
                tv_end.setTextColor(Color.parseColor("#d9ffffff"))
                header.findViewById<TextView>(R.id.tv_title).text="营业时间"
            }

            Click.viewClick(tv_end).subscribe {
                isStartTime=false
                tv_end.setTextColor(Color.parseColor("#FCC725"))
                tv_start.setTextColor(Color.parseColor("#d9ffffff"))
                header.findViewById<TextView>(R.id.tv_title).text="歇业时间"
            }
        }

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
            var p= Pattern.compile(regEx)
            var m=p.matcher(str)
            s=m.replaceAll("").trim()
        }catch (e: Exception){

        }
        return  s
    }





}


