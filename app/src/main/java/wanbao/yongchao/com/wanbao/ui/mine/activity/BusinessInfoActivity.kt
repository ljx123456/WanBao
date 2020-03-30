package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.content.Intent
import android.graphics.Color
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import cn.qqtheme.framework.picker.TimePicker
import kotlinx.android.synthetic.main.activity_business_info.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.dialog.AreaDialog
import wanbao.yongchao.com.wanbao.ui.login.dialog.BusinessTypeDialog
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.QiniuPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.QiniuView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditBusinessBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditUserBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.UserInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditBusinessBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.UserInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditBusinessPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.MineUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditBusinessView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditUserView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineUserView
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.pickers.pickerUtils
import wanbao.yongchao.com.wanbao.utils.utils.CameraSelect
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.math.BigDecimal

class BusinessInfoActivity: BaseActivity(), CameraSelect.CameraSelectFace, QiniuView, MineUserView, EditBusinessView , AreaDialog.CallBack, BusinessTypeDialog.CallBack{
    override fun choose(p_id: Int, c_id: Int, a_id: Int) {
        this.p_id=p_id
        this.c_id=c_id
        this.a_id=a_id
        tv_address.text=""
        tv_location.text="待选取"
        isMap=true
    }

    override fun type(type_id: Int) {
        var body=EditBusinessBody()
        body.type="7"
        body.typeId=type_id.toString()
        editPresenter.setEditBusiness(body)
    }

    override fun getEditBusinessRequest(data: EditBusinessBean) {
        user.setChange(true)
    }

    override fun getUserInfoRequest(data: UserInfoBean) {
        ImageLoad.setUserHead(data.data.avatar,iv_header)
        tv_name.text=data.data.nickname
        if (data.data.phone!=null&&data.data.phone.size>0){
            if (data.data.phone.size==1){
                tv_phone.text=data.data.phone[0]
            }else{
                tv_phone.text=data.data.phone[0]+" / "+data.data.phone[1]
            }
        }
        tv_time.text=data.data.openTime+" ~ "+data.data.closeTime
        tv_type.text=data.data.businessTypeName

        if (data.data.perPersonConsume!=null){
            tv_money.text=BigDecimal(data.data.perPersonConsume).setScale(0,BigDecimal.ROUND_DOWN).toString()+"RMB/人"
        }else{
            tv_money.text=""
        }

        tv_area.text=data.data.provinceName+" "+data.data.cityName+" "+data.data.county
        tv_address.text=data.data.address
        if (data.data.locationId!=null&&data.data.locationId!=""){
            tv_location.text="已获取"
        }else{
            tv_location.text="待获取"
        }

        if (data.data.signature!=null&&data.data.signature!=""){
            sign=data.data.signature
            tv_signer.text=data.data.signature
        }else{
            tv_signer.text=""
        }

        Click.viewClick(layout_name).subscribe {
            intentUtils.intentEditBusinessName(data.data.nickname)
        }

        Click.viewClick(layout_phone).subscribe {
            intentUtils.intentEditBusinessPhone(data.data.phone)
        }

        Click.viewClick(layout_money).subscribe {
            intentUtils.intentBusinessMoney()
        }

        Click.viewClick(layout_signer).subscribe {
            intentUtils.intentBusinessSign(sign)
        }

        Click.viewClick(layout_address).subscribe {
            intentUtils.intentEditBusinessAddress(data.data.address)
        }

        Click.viewClick(layout_time).subscribe {
            getTime()
        }

        Click.viewClick(layout_type).subscribe {
            var dialog=BusinessTypeDialog(this,tv_type)
            dialog.setCallBack(this)
            dialog.show(supportFragmentManager,"")
        }

        Click.viewClick(layout_area).subscribe {
            var dialog= AreaDialog(tv_area)
            dialog.setCallBack(this)
            dialog.show(supportFragmentManager,"")
        }

        Click.viewClick(layout_location).subscribe {
            if (tv_area.text != null && tv_area.text.toString().isNotEmpty()) {
                isMap=true
                var sp = tv_area.text.toString().split(" ")
                province = sp[0]
                city = sp[1]
                area = sp[2]
                if (tv_address.text != null && tv_address.text.toString().isNotEmpty()) {
                    intentUtils.intentRegisterBusinessMap(province, city, area, tv_address.text.toString())
                } else {
                    intentUtils.intentRegisterBusinessMap(province, city, area)
                }
            }
        }

    }

    override fun getUserInfoError() {

    }


    override fun returnCameraImageList(list: java.util.ArrayList<String>) {
        if (list!=null&&list.size>0) {
            Log.e("测试", "开始上传")
            qiniuPresenter.setImage(list)
            file = File(list[0])
        }
    }

    override fun sendSucceedImage(fileUrlList: ArrayList<String>) {
        ImageLoad.setUserHead("http://pic.bixinyule.com/"+fileUrlList[0],iv_header)
        avatar="http://pic.bixinyule.com/"+fileUrlList[0]

        var body=EditBusinessBody()
        body.type="2"
        body.avatar=avatar
        editPresenter.setEditBusiness(body)

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

    private val infoPresenter by lazy { MineUserPresenter(this,this,this) }
    private val qiniuPresenter by lazy { QiniuPresenter(this,this,this) }
    private val editPresenter by lazy { EditBusinessPresenter(this,this,this) }

    private var sign=""

    private var avatar=""
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
    private var p_id=-1
    private var c_id=-1
    private var a_id=-1
    private var isFlag=true//是否第一次选取时间

    private var isMap=false//是否进入坐标选取

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_business_info

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        cameraSelect.initSinglePhotoSdk(this)
        picker = TimePicker(this)
    }

    override fun clickListener() {

        Click.viewClick(iv_back).subscribe { finish() }

        Click.viewClick(iv_header).subscribe {
            cameraSelect.openCamera()
        }


    }

    override fun onResume() {
        super.onResume()
        if (!isMap) {
            var user = GreenDaoHelper.getDaoSessions().businessDBDao
            if (user != null) {
                var info = user.loadAll()
                if (info != null && info.size > 0) {
                    infoPresenter.getUserInfo(UserInfoBody(info.get(0).token))
                } else {
                    intentUtils.intentLogin()
                }
            } else {
                intentUtils.intentLogin()
            }
        }else{
            if (user.getBusinessAddress()!=""){
                tv_address.setText(user.getBusinessAddress())
            }
            if (user.getlat()!=""&& user.getlng()!=""&& user.getBusinessAddress()!=""&&user.getLocationId()!=""){
                Log.e("测试地址", user.getBusinessAddress())
                Log.e("测试维度", user.getlat())
                Log.e("测试loca", user.getLocationId())
                tv_address.setText(user.getBusinessAddress())
                tv_location.text="已选取"
                var body=EditBusinessBody()
                body.type="9"
                body.address=tv_address.text.toString()
                editPresenter.setEditBusiness(body)

                var body1=EditBusinessBody()
                body1.type="10"
                body1.locationId=user.getLocationId()
                body1.latitude=user.getlat()
                body1.longitude=user.getlng()
                editPresenter.setEditBusiness(body1)
                if (p_id!=-1&&p_id!=-1&&a_id!=-1){
                    var body2=EditBusinessBody()
                    body2.type="8"
                    body2.countyId=a_id.toString()
                    editPresenter.setEditBusiness(body2)
                }
            }else{
                tv_location.text="待选取"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        user.setlat("")
        user.setlng("")
        user.setBusinessAddress("")
        user.setLocationId("")
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

    fun getTime() {
//        if (isFlag){
//            isFlag=false
        Log.e("测试","点击了")
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

            header= View.inflate(this,R.layout.layout_time_pick_top,null)
            picker.setHeaderView(header)
            footer= View.inflate(this,R.layout.layout_time_pick_bottom,null)
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
            picker.setOnWheelListener(object : TimePicker.OnWheelListener{
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
                    tv_time.text=footer.findViewById<TextView>(R.id.tv_start_time).text.toString()+" ~ "+footer.findViewById<TextView>(R.id.tv_end_time).text.toString()
                    var body=EditBusinessBody()
                    body.type="6"
                    body.openTime=openTime
                    body.closeTime=endTime
                    editPresenter.setEditBusiness(body)
                    picker.dismiss()
                }else{
                    Toast.Tips("请选择营业时间和歇业时间")
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
//        }

        picker.show()


    }
}