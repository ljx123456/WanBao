package wanbao.yongchao.com.wanbao.ui.release.activity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.blankj.utilcode.util.ActivityUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_release.*
import picker.prim.com.primpicker_core.PrimPicker
import picker.prim.com.primpicker_core.entity.MediaItem
import picker.prim.com.primpicker_core.entity.MimeType
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.jpush.MiuiUtils
import wanbao.yongchao.com.wanbao.ui.image.ImageInfo
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.QiniuPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.QiniuView
import wanbao.yongchao.com.wanbao.ui.main.activity.MainActivity
import wanbao.yongchao.com.wanbao.ui.main.dialog.AtDialog
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.release.adapter.PictureAdapter
import wanbao.yongchao.com.wanbao.ui.release.dialog.ReleaseAddressDialog
import wanbao.yongchao.com.wanbao.ui.release.dialog.ReleaseTopicDialog
import wanbao.yongchao.com.wanbao.ui.release.dialog.SelectPhotoDialog
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseHistoryBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.ReleaseCommunityBody
import wanbao.yongchao.com.wanbao.ui.release.mvp.presenter.QiniuVideoPresenter
import wanbao.yongchao.com.wanbao.ui.release.mvp.presenter.ReleasePresenter
import wanbao.yongchao.com.wanbao.ui.release.mvp.view.ReleaseView
import wanbao.yongchao.com.wanbao.ui.release.util.ImageLoader
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils.intentImage
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.utils.CameraSelect
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class ReleaseActivity:BaseActivity(),SelectPhotoDialog.SelectPhotoDialogFace, CameraSelect.CameraSelectFace,QiniuView,ReleaseTopicDialog.Topic,ReleaseAddressDialog.Address , UserPermissions.MemoryReadPermissionsFace, LocationUtils.Location, AtDialog.AtList,ReleaseView,PictureAdapter.NonePiceture{
    override fun setNone() {
        gv_picture.visibility=View.GONE
        release_add.visibility= View.VISIBLE
        adapter=null
        tv_release.isEnabled=false
        tv_release.setTextColor(Color.parseColor("#80fcc725"))
    }

    override fun getReleaseCommunityRequest(data: ReleaseBean) {
//        dismissLoading(this)
        user.setFlag(true)
        user.setChange(true)
//        finish()
//        intentUtils.intentMain()
        ActivityUtils.finishOtherActivities(MainActivity::class.java)


    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading(this)
    }

    override fun getReleaseCommunityError() {
        dismissLoading(this)
    }

    override fun atList(list: ArrayList<FansBean.DataBean>) {
        user_at=""
        list.forEach {
            //            user=user+"@${it.nickname} &h;"
            user_at=user_at+"@${it.nickname} "
        }
        Log.e("测试edit",edit.text.toString())
//        text=text+user
        edit.setText(edit.text.toString()+user_at)
    }

    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        LocationUtils(this).getLocationOnce()
    }

    override fun requestPermissionsFaceError() {
        dismissLoading(this)
        Toast.Tips("请打开定位权限")
    }

    override fun getLocationSuccess(city: String) {
        dismissLoading(this)
        if (isRelease){
            var body=ReleaseCommunityBody()
            if (edit.text!=null&&edit.text.toString().isNotEmpty()){
                var mentionList = edit.getMentionList(true)
                var str = edit.text.toString()
                if (mentionList.size > 0) {
                    mentionList.forEach {
                        str = str.replace(it, "${it}&h;")
                    }
                }
                body.content=str
            }

            if (adapter!=null&&gv_picture.visibility==View.VISIBLE){
                body.images=adapter!!.list
            }

            if (video!=""&&layout_video.visibility==View.VISIBLE){
                body.video=video
            }

            if (topic_id!=""){
                body.topicId=topic_id
            }

            if (address_id!=""){
                body.locationId=address_id
            }

            body.latitude=user.getLocationLat()
            body.longitude=user.getLocationLng()

            presenter.getReleaseCommunity(body)
        }else {

//            if (addressDialog != null) {
//
//                addressDialog!!.show(supportFragmentManager, "")
//            } else {
                addressDialog = ReleaseAddressDialog(user.getLocationLat(), user.getLocationLng(), this)
                addressDialog!!.show(supportFragmentManager, "")
//            }
        }
    }

    override fun setAddress(name: String, id: String) {
//        Log.e("测试打卡",name)
        layout_address.visibility=View.VISIBLE
        tv_address.text=name
        address_id=id
        address=name
    }

    override fun setTopic(topic: String, id: String) {
//        Log.e("测试话题",topic)
        layout_topic.visibility=View.VISIBLE
        tv_topic.text=topic
        topic_id=id
        this.topic=topic
    }

    override fun sendSucceedImage(fileUrlList: ArrayList<String>) {
        tv_release.isEnabled=true
        tv_release.setTextColor(Color.parseColor("#FCC725"))
        if (isPhoto){
            release_add.visibility= View.GONE
            gv_picture.visibility=View.VISIBLE
            layout_video.visibility=View.GONE
            var list=ArrayList<String>()
            fileUrlList.forEach {
                list.add("http://pic.bixinyule.com/"+it)
            }
            if (adapter!=null){
                adapter!!.addList(list)
            }else{
                adapter= PictureAdapter(this,9,this)
                adapter!!.addList(list)
                gv_picture.adapter=adapter
            }
        }else{
            release_add.visibility= View.GONE
            gv_picture.visibility=View.GONE
            layout_video.visibility=View.VISIBLE
            video="http://pic.bixinyule.com/"+fileUrlList[0]
            ImageLoad.setImage("http://pic.bixinyule.com/"+fileUrlList[0],iv_video)
            if (duration>0){
                var m=(duration/1000)/60
                var s=(duration/1000)%60
                var min=""
                if (m>0){
                    min="0${m.toInt()}:"
                }else{
                    min="00:"
                }
                if (s<10){
                    tv_time.text=min+"0${s}"
                }else{
                    tv_time.text=min+"${s}"
                }
            }
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
//            if (list.size==1) {
//                file = File(list[0])
//            }
        }
    }

    override fun photoBtn() {
        isPhoto=true
        if (adapter!=null) {
            cameraSelect.initMultiCameraSdk(this, (9 - adapter!!.list.size))
        }else{
            cameraSelect.initMultiCameraSdk(this, 9)
        }
        cameraSelect.openCamera()

    }

    override fun videoBtn() {
        isPhoto=false
        var type=EnumSet.of<MimeType>(MimeType.MP4)
//        PrimPicker
//                .with(this)
//                .choose(MimeType.ofVideo())
//                .setSpanCount(3)
////                .setMaxSelected(9)
//                .setImageLoader(ImageLoader())
//                .setShowSingleMediaType(true)
//                .setCapture(true)
//                .setPerAllowSelect(true)
//                .setPerClickShowSingle(true)
//                .setSelectVideoMaxDurationS(150*1000)
//                .setPerViewEnable(true)
//                .setClickItemPerOrSelect(true)
////                .setDefaultItems(list)
//                .lastGo(1001)
        PrimPicker
                .with(this)
                .choose(MimeType.of(MimeType.MP4,MimeType.MP4))
                .setSpanCount(3)
//                .setMaxSelected(9)
                .setImageLoader(ImageLoader())
                .setShowSingleMediaType(true)
                .setCapture(true)
                .setSelectVideoMaxDurationS(150*1000)
                .setPerViewEnable(false)
//                .setDefaultItems(list)
                .lastGo(1001)
//        if (MiuiUtils.isMIUI()) {
//            var intent =  Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"video/*")
//            startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 2)
//        } else {
//            var intent = Intent()
//            if (Build.VERSION.SDK_INT < 19) {
//                intent.setAction(Intent.ACTION_GET_CONTENT)
//                intent.setType("video/*")
//            } else {
//                intent.setAction(Intent.ACTION_OPEN_DOCUMENT)
//                intent.addCategory(Intent.CATEGORY_OPENABLE)
//                intent.setType("video/*")
//            }
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
//            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, false)
//            startActivityForResult(intent, 2)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraSelect.onActivityCameraResult(requestCode, resultCode, data)
        Log.e("测试",requestCode.toString()+"  :"+resultCode.toString())
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            isFirst=false
            var list = PrimPicker.obtainItemsResult(data)
            duration=list[0].duration
            var pathlist = PrimPicker.obtainPathResult(data)
            Log.e("测试",pathlist[0])
            Log.e("测试","选择")
            videoPath=pathlist[0]
            videoPresenter.setImage(pathlist)
        }else if (resultCode==Activity.RESULT_CANCELED&&isFirst){
            finish()
        }
    }

    private var cameraSelect = CameraSelect(this)
    private lateinit var dialog : SelectPhotoDialog
    private val photoPresenter by lazy { QiniuPresenter(this,this,this) }
    private val videoPresenter by lazy { QiniuVideoPresenter(this,this,this) }
    private val presenter by lazy { ReleasePresenter(this,this,this) }
    private var adapter: PictureAdapter?=null
    private var videoPath=""
    private var video=""
    private var isPhoto=true
    private var duration=0L
    private var topic_id=""
    private var topic=""
    private var address_id=""
    private var address=""
    private var user_at=""
    private var isRelease=false
    private var flag=true
    private var isFirst=false

    private lateinit var topicDialog:ReleaseTopicDialog
    private var addressDialog:ReleaseAddressDialog?=null


    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_release
    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        var mentionList=edit.getMentionList(true)
        edit.setMentionTextColor(Color.parseColor("#FCC725"))
        edit.setPattern("@[\\u4e00-\\u9fa5_a-zA-Z0-9]{2,16}+ ")
        edit.setOnMentionInputListener {
            mentionList.forEach {
                Log.e("测试edit",it)
            }
        }
        dialog= SelectPhotoDialog(this)
        dialog.setDialogFace(this)

        cameraSelect.initMultiCameraSdk(this,9)

        topicDialog= ReleaseTopicDialog(this)

        var mCache = ACache.get(this)
        mCache.getAsString("HistoryRelease")
        if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
            var bean = Gson().fromJson(mCache.getAsString("HistoryRelease"), ReleaseHistoryBean::class.java)
            if (bean.content!=null&&bean.content!=""){
                edit.setText(bean.content)
                tv_release.isEnabled=true
                tv_release.setTextColor(Color.parseColor("#FCC725"))
            }

            if (bean.images!=null&&bean.images.size>0){
                release_add.visibility= View.GONE
                gv_picture.visibility=View.VISIBLE
                layout_video.visibility=View.GONE
                adapter= PictureAdapter(this,9,this)
                adapter!!.addList(bean.images)
                gv_picture.adapter=adapter
                tv_release.isEnabled=true
                tv_release.setTextColor(Color.parseColor("#FCC725"))
            }

            if (bean.video!=null&&bean.video!=""&&bean.duration!=null&&bean.duration>0){
                release_add.visibility= View.GONE
                gv_picture.visibility=View.GONE
                layout_video.visibility=View.VISIBLE
                video=bean.video
                ImageLoad.setImage(bean.video,iv_video)
                duration=bean.duration
                if (duration>0){
                    var m=(duration/1000)/60
                    var s=(duration/1000)%60
                    var min=""
                    if (m>0){
                        min="0${m.toInt()}:"
                    }else{
                        min="00:"
                    }
                    if (s<10){
                        tv_time.text=min+"0${s}"
                    }else{
                        tv_time.text=min+"${s}"
                    }
                }
                tv_release.isEnabled=true
                tv_release.setTextColor(Color.parseColor("#FCC725"))
            }

            if (bean.topic!=null&&bean.topic!=""&&bean.topicId!=""&&bean.topicId!=null){
                Log.e("测试话题",bean.topic)
                layout_topic.visibility=View.VISIBLE
                tv_topic.text=bean.topic
                topic_id=bean.topicId
                topic=bean.topic
            }

//            if (bean.address!=null&&bean.address!=""&&bean.locationId!=""&&bean.locationId!=null){
//                Log.e("测试打卡",bean.address)
//                layout_address.visibility=View.VISIBLE
//                tv_address.text=bean.address
//                address_id=bean.locationId
//                address=bean.address
//            }
        }

        if (intent.getStringExtra("type")!=null&&intent.getStringExtra("type")!=""){
            if (intent.getStringExtra("type")=="照片"){
                isFirst=true
                isPhoto=true
                cameraSelect.openCamera()
            }else if (intent.getStringExtra("type")=="视频"){
                isFirst=true
                isPhoto=false
                PrimPicker
                        .with(this)
                        .choose(MimeType.of(MimeType.MP4,MimeType.MP4))
                        .setSpanCount(3)
//                .setMaxSelected(9)
                        .setImageLoader(ImageLoader())
                        .setShowSingleMediaType(true)
                        .setCapture(true)
                        .setSelectVideoMaxDurationS(150*1000)
                        .setPerViewEnable(false)
//                .setDefaultItems(list)
                        .lastGo(1001)
            }else if (intent.getStringExtra("type")=="打卡"){
                isRelease=false
                showLoading(this)
                UserPermissions.userLocation(mContext, this)
            }
        }

        if (intent.getStringExtra("topicId")!=null&&intent.getStringExtra("topicId")!=""&&intent.getStringExtra("topic")!=null&&intent.getStringExtra("topic")!=""){
            Log.e("测试话题",intent.getStringExtra("topic"))
            layout_topic.visibility=View.VISIBLE
            tv_topic.text=intent.getStringExtra("topic")
            topic_id=intent.getStringExtra("topicId")
            topic=intent.getStringExtra("topic")
        }

    }

    override fun clickListener() {

        edit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s.toString().isNotEmpty()){
                    tv_release.isEnabled=true
                    tv_release.setTextColor(Color.parseColor("#FCC725"))
                    tv_num.text="${s.length}/160"
                    if (s.length==160){
                        tv_num.setTextColor(Color.parseColor("#FC4625"))
                    }else{
                        tv_num.setTextColor(Color.parseColor("#73FFFFFF"))
                    }
                }else{
                    tv_release.isEnabled=false
                    tv_release.setTextColor(Color.parseColor("#80fcc725"))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        Click.viewClick(release_add).subscribe {
            dialog.showDialog()
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
                intentImage(false, imagelist, position)
            }
        }

        Click.viewClick(layout_video).subscribe {
            intentUtils.intentVideo(videoPath)
        }

        Click.viewClick(videoClose).subscribe {
            videoPath=""
            gv_picture.visibility=View.GONE
            layout_video.visibility=View.GONE
            release_add.visibility=View.VISIBLE
        }

        Click.viewClick(close_address).subscribe {
            address_id=""
            address=""
            layout_topic.visibility=View.GONE
        }

        Click.viewClick(close_topic).subscribe {
            topic_id=""
            topic=""
            layout_topic.visibility=View.GONE
        }

        Click.viewClick(iv_topic).subscribe {
            if (layout_topic.visibility==View.VISIBLE&&topic_id!=""&&flag){
                flag=false
                ShowDialog.showCustomDialogNoTitle(this,"继续操作将更换话题\n" +
                        "是否继续？","更换话题","取消",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                dialog.dismiss()
                                topicDialog.show(supportFragmentManager,"")
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {
                                dialog.dismiss()
                            }
                        }
                    }
                })
            }else {
                topicDialog.show(supportFragmentManager, "")
            }
        }

        Click.viewClick(iv_clock).subscribe {
            isRelease=false
            showLoading(this)
            UserPermissions.userLocation(mContext, this)
        }


        Click.viewClick(iv_at).subscribe {
            var dialog=AtDialog()
            dialog.setAtDialog(this)
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
            dialog.show(supportFragmentManager,"")
        }

        Click.viewClick(tv_release).subscribe {
            isRelease = true
            showLoading(this)
            UserPermissions.userLocation(mContext, this)

        }

        Click.viewClick(iv_back).subscribe {
            if (edit.text.toString().isNotEmpty()||adapter!=null||video!=""||topic!=""||address!=""){
                ShowDialog.showCustomDialogNoTitle(this,"需要帮您保留这次编辑内容吗?","保留","不保留",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                dialog.dismiss()
                                var bean=ReleaseHistoryBean()
                                if (edit.text!=null&&edit.text.toString().isNotEmpty()){
                                    bean.content=edit.text.toString()
                                }

                                if (adapter!=null&&adapter!!.list.size>0){
                                    bean.images=adapter!!.list
                                }

                                if (video!=""&&duration>0){
                                    bean.video=video
                                    bean.duration=duration
                                }

                                if (topic!=""&&topic_id!=""){
                                    bean.topic=topic
                                    bean.topicId=topic_id
                                }

                                if (address!=""&&address_id!=""){
                                    bean.address=address
                                    bean.locationId=address_id
                                }
                                var mCache = ACache.get(this@ReleaseActivity)
                                mCache.put("HistoryRelease", Gson().toJson(bean))
                                finish()
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {
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


}