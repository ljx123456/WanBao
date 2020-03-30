package wanbao.yongchao.com.wanbao.ui.release.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.widget.NestedScrollView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_release_ye.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.image.ImageInfo
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.QiniuPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.QiniuView
import wanbao.yongchao.com.wanbao.ui.release.adapter.PictureAdapter
import wanbao.yongchao.com.wanbao.ui.release.dialog.ReleaseAreaDialog
import wanbao.yongchao.com.wanbao.ui.release.dialog.ReleasePhoneDialog
import wanbao.yongchao.com.wanbao.ui.release.dialog.ReleaseYeAddressDialog
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseYeBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseYeHistoryBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.ReleaseYeBody
import wanbao.yongchao.com.wanbao.ui.release.mvp.presenter.ReleaseYePresenter
import wanbao.yongchao.com.wanbao.ui.release.mvp.view.ReleaseYeView
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.pickers.TimeCustomPicker
import wanbao.yongchao.com.wanbao.utils.pickers.pickerUtils
import wanbao.yongchao.com.wanbao.utils.utils.CameraSelect
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class ReleaseYeActivity:BaseActivity(), CameraSelect.CameraSelectFace, QiniuView,ReleaseAreaDialog.CallBack ,ReleaseYeAddressDialog.Address,ReleasePhoneDialog.Phone,ReleaseYeView{
    override fun getReleaseYeRequest(data: ReleaseYeBean) {
        user.setChange(true)
        intentUtils.intentActivitiesDetails(data.data.id.toInt())
        finish()
    }

    override fun getReleaseYeError() {

    }

    override fun setPhone(list: ArrayList<String>) {
        layout_bottom.visibility=View.VISIBLE
        layout_phone.visibility=View.VISIBLE
        if (list.size==1){
            tv_phone.text=list[0]
        }else if (list.size==2){
            tv_phone.text=list[0]+" / "+list[1]
        }
        phoneList=list
        scroll.post(object :Runnable{
            override fun run() {
                scroll.scrollTo(0,layout_bottom.bottom)
            }
        })
    }

    override fun setAddress(address: String) {
        tv_address_content.text=address
//        this.address=tv_address_area.text.toString().replace(" ","")+address
        this.address=address
        Log.e("测试",this.address)
        scroll.post(object :Runnable{
            override fun run() {
                scroll.scrollTo(0,layout_bottom.bottom)
            }
        })
    }

    override fun choose(p_id: Int, c_id: Int, a_id: Int) {
        layout_bottom.visibility=View.VISIBLE
        layout_address.visibility=View.VISIBLE
        countyId=a_id.toString()
        scroll.post(object :Runnable{
            override fun run() {
                scroll.scrollTo(0,layout_bottom.bottom)
            }
        })

    }

    override fun sendSucceedImage(fileUrlList: ArrayList<String>) {
        if (isCovee) {
            ImageLoad.setImage("http://pic.bixinyule.com/" + fileUrlList[0], iv_cover)
            cover="http://pic.bixinyule.com/" + fileUrlList[0]
            if (!file!!.exists()) {
                return
            } else {
//                file!!.delete()
                deletePic(file!!.absolutePath)
            }
        }else{
            iv_add.visibility=View.GONE
            gv_picture.visibility=View.VISIBLE
            var list=ArrayList<String>()
            fileUrlList.forEach {
                list.add("http://pic.bixinyule.com/"+it)
            }
            if (adapter!=null){
                adapter!!.addList(list)
            }else{
                adapter= PictureAdapter(this,12)
                adapter!!.addList(list)
                gv_picture.adapter=adapter
            }

            picList=adapter!!.list
        }
    }

    override fun sendFileErrorImage() {

    }

    override fun returnCameraImageList(list: ArrayList<String>) {
        Log.e("测试返回结果",list.size.toString())
        if (list!=null&&list.size>0&& File(list[0]).length()>0) {
            isFirst=false
            list.forEach {
                Log.e("测试返回结果图片",it)
            }
            photoPresenter.setImage(list)
            if (list.size==1) {
                file = File(list[0])
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraSelect.onActivityCameraResult(requestCode, resultCode, data)
        photoSelect.onActivityCameraResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_CANCELED&&isFirst){
            finish()
        }
    }

    private var cameraSelect = CameraSelect(this)
    private var photoSelect = CameraSelect(this)
    private val photoPresenter by lazy { QiniuPresenter(this,this,this) }
    private var adapter: PictureAdapter?=null
    private var picker:TimeCustomPicker?=null
    private val presenter by lazy { ReleaseYePresenter(this,this,this) }

    private var file:File?=null
    private var countyId=""
    private var cover=""
    private var picList=ArrayList<String>()
    private var address=""
    private var phoneList=ArrayList<String>()
    private var start=""
    private var end=""
    private var isCovee=true
    private var isFirst=false

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_release_ye

    override fun setActivityTitle() {

    }

    override fun initActivityData() {


        Handler().postDelayed(object :Runnable{
            override fun run() {
                var p=iv_cover.layoutParams
                p.width=iv_cover.width
                p.height=iv_cover.width
                iv_cover.layoutParams=p
            }
        },500)

        var mCache = ACache.get(this)
        mCache.getAsString("HistoryReleaseYe")
        if (mCache.getAsString("HistoryReleaseYe") != null && !"".equals(mCache.getAsString("HistoryReleaseYe"))) {
            var bean = Gson().fromJson(mCache.getAsString("HistoryReleaseYe"), ReleaseYeHistoryBean::class.java)
            if (bean.previewImg!=null&&bean.previewImg!=""){
                cover=bean.previewImg
                ImageLoad.setImage(bean.previewImg, iv_cover)
            }

            if (bean.images!=null&&bean.images.size>0){
                iv_add.visibility=View.GONE
                gv_picture.visibility=View.VISIBLE

                adapter= PictureAdapter(this,12)
                adapter!!.addList(bean.images)
                gv_picture.adapter=adapter

                picList=adapter!!.list
            }

            if (bean.title!=null&&bean.title!=""){
                edit_title.setText(bean.title)
            }

            if(bean.content!=null&&bean.content!=""){
                edit_content.setText(bean.content)
            }

            if (bean.countyId!=null&&bean.countyId!=""){
                layout_bottom.visibility=View.VISIBLE
                layout_address.visibility=View.VISIBLE
                countyId=bean.countyId
                tv_address_area.text=bean.area
                if (bean.address!=null&&bean.address!=""){
                    tv_address_content.text=bean.address
                    address=bean.address
                }
            }

            if (bean.startTime!=null&&bean.startTime!=""&&bean.endTime!=null&&bean.endTime!=""){
                layout_bottom.visibility=View.VISIBLE
                layout_time.visibility=View.VISIBLE
                start=bean.startTime
                end=bean.endTime
                tv_time.text=bean.time
            }

            if (bean.phones!=null&&bean.phones.size>0){
                layout_bottom.visibility=View.VISIBLE
                layout_phone.visibility=View.VISIBLE
                if (bean.phones.size==1){
                    tv_phone.text=bean.phones[0]
                }else if (bean.phones.size==2){
                    tv_phone.text=bean.phones[0]+" / "+bean.phones[1]
                }
                phoneList=bean.phones
            }
        }else{
            isCovee=true
            isFirst=true
            cameraSelect.initCameraSdkFlag(this)
            cameraSelect.openCamera()
        }
    }

    override fun clickListener() {
        gv_picture.setOnItemClickListener { parent, view, position, id ->
            if (adapter!=null&&adapter!!.list.size<12&&position==parent.childCount-1){
//                cameraSelect.openCamera()
                isCovee=false
                photoSelect.initMultiCameraSdk(this,12-adapter!!.list.size)
                photoSelect.openCamera()
            }else{
                var imagelist = ArrayList<ImageInfo>()
                adapter!!.list.forEach {
                    imagelist.add(ImageInfo(it, false, 2))
                }
                intentUtils.intentImage(false, imagelist, position)
            }
        }

        edit_title.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s.toString().isNotEmpty()&&s.toString().length==32){
                    Toast.Tips("标题最多不得超过32个字")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        Click.viewClick(tv_cover).subscribe {
            isCovee=true
            cameraSelect.openCamera()
        }

        Click.viewClick(iv_add).subscribe {
            isCovee=false
            photoSelect.initMultiCameraSdk(this,12)
            photoSelect.openCamera()
        }

        Click.viewClick(iv_address).subscribe {
            var dialog= ReleaseAreaDialog(tv_address_area)
            dialog.setCallBack(this)
            dialog.show(supportFragmentManager,"")
        }

        Click.viewClick(tv_address_content).subscribe {
            var dialog=ReleaseYeAddressDialog(this)
            dialog.show(supportFragmentManager,"")
        }

        Click.viewClick(iv_phone).subscribe {
            var dialog=ReleasePhoneDialog(this)
            dialog.show(supportFragmentManager,"")
        }

        Click.viewClick(iv_time).subscribe {
            getTime()
        }

        Click.viewClick(tv_release).subscribe {
            if (cover!=""&&picList.size>0&&edit_title.text!=null&&edit_title.text.toString().isNotEmpty()&&edit_content.text!=null&&edit_content.text.toString().isNotEmpty()
                &&countyId!=""&&address!=""&&start!=""&&end!=""&&phoneList.size>0){

                var body=ReleaseYeBody()
                body.previewImg=cover
                body.images=picList
                body.title=edit_title.text.toString()
                body.content=edit_content.text.toString()
                body.countyId=countyId
                body.address=address
                body.startTime=start
                body.endTime=end
                body.phones=phoneList
                presenter.getReleaseYe(body)
            }else{
                Toast.Tips("您的活动文章必须包含封面、标题、内容，活动地点以及联系方式和活动时间")
            }
        }

        Click.viewClick(tv_preview).subscribe {
            if (cover!=""&&picList.size>0&&edit_title.text!=null&&edit_title.text.toString().isNotEmpty()&&edit_content.text!=null&&edit_content.text.toString().isNotEmpty()
                    &&countyId!=""&&address!=""&&start!=""&&end!=""&&phoneList.size>0){
                var list=ArrayList<String>()
                list.addAll(picList)
                list.add(0,cover)
                intentUtils.intentPreview(list,edit_title.text.toString(),edit_content.text.toString(),tv_address_area.text.toString(),address,tv_time.text.toString(),phoneList)
            }else{
                Toast.Tips("您的活动文章必须包含封面、标题、内容，活动地点以及联系方式和活动时间")
            }
        }

        Click.viewClick(iv_back).subscribe {
            if (cover!=""||picList.size>0||(edit_title.text!=null&&edit_title.text.toString().isNotEmpty())||(edit_content.text!=null&&edit_content.text.toString().isNotEmpty())
                    ||countyId!=""||address!=""||(start!=""&&end!="")||phoneList.size>0){
                ShowDialog.showCustomDialogNoTitle(this,"需要帮您保留这次编辑内容吗?","保留","不保留",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                dialog.dismiss()
                                var bean=ReleaseYeHistoryBean()
                                if (cover!=""){
                                    bean.previewImg=cover
                                }
                                if (picList.size>0){
                                    bean.images=picList
                                }

                                if (edit_title.text!=null&&edit_title.text.toString().isNotEmpty()){
                                    bean.title=edit_title.text.toString()
                                }

                                if (edit_content.text!=null&&edit_content.text.toString().isNotEmpty()){
                                    bean.content=edit_content.text.toString()
                                }

                                if (countyId!=""){
                                    bean.countyId=countyId
                                    bean.area=tv_address_area.text.toString()
                                }

                                if (address!=""){
                                    bean.address=address
                                }

                                if (start!=""&&end!=""){
                                    bean.startTime=start
                                    bean.endTime=end
                                    bean.time=tv_time.text.toString()
                                }

                                if (phoneList.size>0){
                                    bean.phones=phoneList
                                }

                                var mCache = ACache.get(this@ReleaseYeActivity)
                                mCache.put("HistoryReleaseYe", Gson().toJson(bean))
                                finish()
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {
                                var mCache = ACache.get(this@ReleaseYeActivity)
                                mCache.put("HistoryReleaseYe", "")
                                dialog.dismiss()
                                finish()
                            }
                        }
                    }
                })

            }else{
                finish()
            }
        }


    }

    private lateinit var header:View
    private lateinit var footer:View
    private lateinit var tv_start:TextView
    private lateinit var tv_end:TextView
    private lateinit var tv_sumit:TextView
    private lateinit var tv_cancel:TextView
    private var isStartTime=true
    private var starTime=""
    private var endTime=""
    val c = Calendar.getInstance()//可以对每个时间域单独修改
    var months = c.get(Calendar.MONTH) + 1
    var dates = c.get(Calendar.DATE)
    var years = c.get(Calendar.YEAR)
    fun getTime(){
        Log.e("测试","时间选取")
        picker= TimeCustomPicker(this)
        pickerUtils.showCustomTimePicker(picker!!)
        picker!!.setBackgroundColor(Color.parseColor("#2C2C2F"))
        picker!!.setTopBackgroundColor(Color.parseColor("#212123"))
        picker!!.setLabelTextColor(Color.parseColor("#d6ffffff"))
        picker!!.setTitleText("")
        picker!!.setTitleTextColor(Color.parseColor("#333333"))
        picker!!.setTitleTextSize(16)
        picker!!.setTextColor(Color.parseColor("#FCC725"))
        picker!!.setSelectedHourItem(months,dates,0)
//        picker!!.setSelectedItem(6,0)

        header=View.inflate(this,R.layout.layout_time_pick_top,null)
        picker!!.setHeaderView(header)
        footer=View.inflate(this,R.layout.layout_time_pick_bottom,null)
        picker!!.setFooterView(footer)
        tv_start=footer.findViewById<TextView>(R.id.tv_start_time)
        tv_end=footer.findViewById<TextView>(R.id.tv_end_time)
        tv_sumit=header.findViewById<TextView>(R.id.tv_summit)
        tv_cancel=header.findViewById<TextView>(R.id.tv_cancel)
        header.findViewById<TextView>(R.id.tv_title).text="活动开始时间"
        tv_start.text=picker!!.selectedMonth+"月"+picker!!.selectedDay+"日"+picker!!.selectedHour+"时"
        starTime=picker!!.selectedMonth+"/"+picker!!.selectedDay+"/"+picker!!.selectedHour+":00"
        endTime=picker!!.selectedMonth+"/"+picker!!.selectedDay+"/"+picker!!.selectedHour+":00"
        tv_end.text="选择结束时间"

        tv_start.setTextColor(Color.parseColor("#FCC725"))
        tv_end.setTextColor(Color.parseColor("#d9ffffff"))

        picker!!.setOnWheelListener(object :TimeCustomPicker.OnWheelListener{
            override fun onMonthWheeled(var1: Int, p1: String?) {
                if (isStartTime){
                    tv_start.text=p1+"月"+picker!!.selectedDay+"日"+picker!!.selectedHour+"时"
                    starTime=p1+"/"+picker!!.selectedDay+"/"+picker!!.selectedHour+":00"
                }else{
                    tv_end.text=p1+"月"+picker!!.selectedDay+"日"+picker!!.selectedHour+"时"
                    endTime=p1+"/"+picker!!.selectedDay+"/"+picker!!.selectedHour+":00"
                }

                if (tv_start.text.toString().contains("日")&&tv_end.text.toString().contains("日")){
                    tv_sumit.setTextColor(Color.parseColor("#d9ffffff"))
                }else{
                    tv_sumit.setTextColor(Color.parseColor("#d6ffffff"))
                }
            }

            override fun onDayWheeled(var1: Int, p2: String?) {
                if (isStartTime){
                    tv_start.text=picker!!.selectedMonth+"月"+p2+"日"+picker!!.selectedHour+"时"
                    starTime=picker!!.selectedMonth+"/"+p2+"/"+picker!!.selectedHour+":00"
                }else{
                    tv_end.text=picker!!.selectedMonth+"月"+p2+"日"+picker!!.selectedHour+"时"
                    endTime=picker!!.selectedMonth+"/"+p2+"/"+picker!!.selectedHour+":00"
                }
                if (tv_start.text.toString().contains("日")&&tv_end.text.toString().contains("日")){
                    tv_sumit.setTextColor(Color.parseColor("#d9ffffff"))
                }else{
                    tv_sumit.setTextColor(Color.parseColor("#d6ffffff"))
                }
            }

            override fun onHourWheeled(var1: Int, p3: String?) {
                if (isStartTime){
                    tv_start.text=picker!!.selectedMonth+"月"+picker!!.selectedDay+"日"+p3+"时"
                    starTime=picker!!.selectedMonth+"/"+picker!!.selectedDay+"/"+p3+":00"
                }else{
                    tv_end.text=picker!!.selectedMonth+"月"+picker!!.selectedDay+"日"+p3+"时"
                    endTime=picker!!.selectedMonth+"/"+picker!!.selectedDay+"/"+p3+":00"
                }
                if (tv_start.text.toString().contains("日")&&tv_end.text.toString().contains("日")){
                    tv_sumit.setTextColor(Color.parseColor("#d9ffffff"))
                }else{
                    tv_sumit.setTextColor(Color.parseColor("#d6ffffff"))
                }
            }
        })

        Click.viewClick( tv_cancel).subscribe {
            isStartTime=true
            header.findViewById<TextView>(R.id.tv_title).text="活动开始时间"
            picker!!.dismiss()

        }

        Click.viewClick(tv_start).subscribe {
            isStartTime=true
            tv_start.setTextColor(Color.parseColor("#FCC725"))
            tv_end.setTextColor(Color.parseColor("#d9ffffff"))
            header.findViewById<TextView>(R.id.tv_title).text="活动开始时间"
        }

        Click.viewClick(tv_end).subscribe {
            isStartTime=false
            tv_end.setTextColor(Color.parseColor("#FCC725"))
            tv_start.setTextColor(Color.parseColor("#d9ffffff"))
            header.findViewById<TextView>(R.id.tv_title).text="活动结束时间"
        }

        Click.viewClick(tv_sumit).subscribe {
            if (tv_start.text.toString().contains("日")&&tv_end.text.toString().contains("日")){
                layout_bottom.visibility=View.VISIBLE
                layout_time.visibility=View.VISIBLE
                tv_time.text=years.toString()+"/"+starTime+" - "+years.toString()+"/"+endTime
                var str=years.toString()+"-"+starTime.replace("/","-")+":00"
                start=str.substring(0,str.lastIndexOf("-"))+" "+str.substring(str.lastIndexOf("-")+1,str.length)

                var str1=years.toString()+"-"+endTime.replace("/","-")+":00"
                end=str1.substring(0,str1.lastIndexOf("-"))+" "+str1.substring(str1.lastIndexOf("-")+1,str1.length)
                Log.e("测试时间",start+"   "+end)
                picker!!.dismiss()
                scroll.post(object :Runnable{
                    override fun run() {
                        scroll.scrollTo(0,layout_bottom.bottom)
                    }
                })
            }else{
                Toast.Tips("请选择开始时间和结束时间")
            }
        }

        picker!!.show()
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