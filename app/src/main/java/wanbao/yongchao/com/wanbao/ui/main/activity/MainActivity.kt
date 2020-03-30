package wanbao.yongchao.com.wanbao.ui.main.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.event.NotificationClickEvent
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.UserInfo
import cn.jpush.im.android.eventbus.EventBus
import cn.jpush.im.api.BasicCallback
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import jiguang.chat.activity.ChatActivity
import jiguang.chat.application.JGApplication
import jiguang.chat.entity.Event
import jiguang.chat.entity.EventType
import jiguang.chat.entity.NotificationClickEventReceiver
import kotlinx.android.synthetic.main.activity_main.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.adapter.ViewPageAdapter
import wanbao.yongchao.com.wanbao.ui.main.dialog.LoginDialog
import wanbao.yongchao.com.wanbao.ui.main.fragment.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CityBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.CityPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CityView
import wanbao.yongchao.com.wanbao.ui.set.dialog.VersionUpdatingDialog
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateLocationBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UpdateBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UpdateLocationBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.presenter.SplashPresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.presenter.UpdateLocationPresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.SplashView
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.UpdateLocationView
import wanbao.yongchao.com.wanbao.utils.Badge.BadgeHelper
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.pop.PopupWindowHelper
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import kotlin.math.sign

class MainActivity : BaseActivity(), UserPermissions.MemoryReadPermissionsFace, LocationUtils.Location ,SplashView, VersionUpdatingDialog.VersionUpdatingCallBack,UpdateLocationView,CityView {
    override fun getCityRequest(data: CityBean) {
        data.data.forEach {
            if (user.getCity()!=city&&city==it.name){
                ShowDialog.showCustomDialogNoTitle(this, "当前定位为${city}，是否切换到该城市", "是", "否", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                dialog.dismiss()
                                user.setCity(it.name)
                                user.setCityID(it.cityId.toString())
                                (fragments[0] as ExploreFragment).setCity()
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {
                                dialog.dismiss()
                            }
                        }
                    }
                })
            }
        }
    }

    override fun getUpdateLocationRequest(data: UpdateLocationBean) {

    }

    override fun getUpdateRequest(data: UpdateBean) {
        if (AppUtils.getAppVersionCode()!=data.data.version) {
            updatingdialog.setDialogContent(data, this)
            updatingdialog.show(supportFragmentManager, "")
        }
    }

    override fun getUpdateError(code: Int, message: String) {

    }

    override fun enterInto() {

    }

    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        LocationUtils(this).getLocation()
    }

    override fun requestPermissionsFaceError() {

    }

    override fun getLocationSuccess(city: String) {
        this.city=city
        if (user.getLocationLat()!=""&&user.getLocationLng()!=""){
            location.getUpdataLocation(UpdateLocationBody(user.getLocationLng(),user.getLocationLat()))
        }
        cityPresenter.getCity(CityBody("1","99999"))
    }

    var mFragmentAdapter: ViewPageAdapter? = null
    var fragments = ArrayList<Fragment>()
    private lateinit var pop: PopupWindowHelper
    private lateinit var popView: View
    private var isFlag=false

    private val updataPresenter by lazy { SplashPresenter(this, this, this) }
    private val updatingdialog = VersionUpdatingDialog()

    private val location by lazy { UpdateLocationPresenter(this,this,this) }

    private val cityPresenter by lazy { CityPresenter(this,this,this) }

    private var city=""

//    private lateinit var login: PopupWindowHelper
//    private lateinit var loginView: View

    override fun openEventBus(): Boolean =false

    override fun getActivityLayout(): Int =R.layout.activity_main

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        user.setChange(true)
        //注册Notification点击的接收器
        NotificationClickEventReceiver(applicationContext)
        UserPermissions.userLocation(mContext, this)
        fragments.add(ExploreFragment())
        fragments.add(CommunityFragment())
        fragments.add(NewsFragment())
        fragments.add(MineFragment())
        mFragmentAdapter= ViewPageAdapter(supportFragmentManager,fragments)
        vp_main.adapter=mFragmentAdapter
        vp_main.offscreenPageLimit=3
        updataPresenter.getUpdata(UpdateBody("1"))
        Log.e("测试","初始化")
//        if (getIntent().getStringExtra("city")!=null){
//            (fragments[0] as ExploreFragment).setCityName(getIntent().getStringExtra("city"))
//        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (getIntent().getStringExtra("city")!=null&&fragments.size>0) {
            Log.e("测试", "城市")
            (fragments[0] as ExploreFragment).setCity()
        }else if (getIntent().getStringExtra("mine") != null ) {
            Log.e("测试","选择")
            chooseBottom(4)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("测试","resume")
            if (user.getFlag()) {
                showLoading(this)
                user.setFlag(false)
                chooseBottom(2)
                (fragments[vp_main.currentItem] as CommunityFragment).setRelease()
            } else if (user.getOut()) {
                user.setOut(false)
                chooseBottom(1)
            }else if (user.getIsWant()){
                if (vp_main.currentItem==0){
                    (fragments[vp_main.currentItem] as ExploreFragment).setWant()
                }
            }else if(user.getChange()&&vp_main.currentItem==3){
                (fragments[vp_main.currentItem] as MineFragment).refresh()
            }

//        }

        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                Log.e("测试","用户0")
                isFlag=true
                ImageLoad.setUserHead(info[0].avatar,iv_mine_header)
                JMessageClient.login(info[0].userId, info[0].userId, object : BasicCallback() {
                    override fun gotResult(p0: Int, p1: String?) {
                        if (p0 == 0) {
                            var userInfo=JMessageClient.getMyInfo()
                            if (userInfo!=null) {
                                userInfo.nickname = info[0].nickName
                                JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, object : BasicCallback() {
                                    override fun gotResult(p0: Int, p1: String?) {
                                        LogUtils.a("极光昵称", p1)
                                    }
                                })
                            }
                        }
                    }
                })
                JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND or JMessageClient.FLAG_NOTIFY_WITH_VIBRATE)

            } else if (inf != null && inf.size > 0) {
                Log.e("测试","商家0")
                isFlag=true
                ImageLoad.setUserHead(inf[0].avatar,iv_mine_header)
                JMessageClient.login(inf[0].businessId, inf[0].businessId, object : BasicCallback() {
                    override fun gotResult(p0: Int, p1: String?) {
                        if (p0 == 0) {
                            var userInfo=JMessageClient.getMyInfo()
                            if (userInfo!=null) {
                                userInfo.nickname = inf[0].nickName
                                JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, object : BasicCallback() {
                                    override fun gotResult(p0: Int, p1: String?) {
                                        LogUtils.a("极光昵称", p1)
                                    }
                                })
                            }
                        }
                    }
                })
                JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND or JMessageClient.FLAG_NOTIFY_WITH_VIBRATE)
            }else{
                isFlag=false
            }
        } else{
            isFlag=false
        }
    }

    override fun clickListener() {
        Click.viewClick(layout_community).subscribe {
            chooseBottom(1)
        }

        Click.viewClick(layout_explore).subscribe {
            if (vp_main.currentItem==1){
                (fragments[1] as CommunityFragment).scrollTop()
            }else{
                chooseBottom(2)
            }

        }

        Click.viewClick(layout_news).subscribe {
            if (isFlag) {
                chooseBottom(3)
            }else{
                var dialog=LoginDialog()
                dialog.show(supportFragmentManager,"")
//                loginView = LayoutInflater.from(mContext!!).inflate(R.layout.dialog_login_tips, null)
//                login = PopupWindowHelper(loginView, mContext)
//                login.showAtLocation(layout_mine,Gravity.CENTER,0,0)
//
//                Click.viewClick(loginView.findViewById(R.id.tv_login)).subscribe {
//                    intentUtils.intentLogin()
//                    login.dismiss()
//                }
//
//                Click.viewClick(loginView.findViewById(R.id.tv_over)).subscribe {
//                    login.dismiss()
//                }
            }
        }

        Click.viewClick(layout_mine).subscribe {
            if (isFlag) {
                chooseBottom(4)
            }else{
                var dialog=LoginDialog()
                dialog.show(supportFragmentManager,"")
//                loginView = LayoutInflater.from(mContext!!).inflate(R.layout.dialog_login_tips, null)
//                login = PopupWindowHelper(loginView, mContext)
//                login.showAtLocation(layout_mine,Gravity.CENTER,0,0)
//
//                Click.viewClick(loginView.findViewById(R.id.tv_login)).subscribe {
//                    intentUtils.intentLogin()
//                    login.dismiss()
//                }
//
//                Click.viewClick(loginView.findViewById(R.id.tv_over)).subscribe {
//                    login.dismiss()
//                }
            }
        }

        Click.viewClick(layout_release).subscribe {
//            var dialog=ReleaseDialog()
//            dialog.show(supportFragmentManager,"")
            if (isFlag) {
                var user = GreenDaoHelper.getDaoSessions().userDBDao
                var business = GreenDaoHelper.getDaoSessions().businessDBDao
                if (user != null || business != null) {
                    Log.e("测试", "用户")
                    var info = user.loadAll()
                    var inf = business.loadAll()
                    if (info != null && info.size > 0) {
                        Log.e("测试", "用户0")
                        popView = LayoutInflater.from(mContext!!).inflate(R.layout.dialog_release, null)
                        pop = PopupWindowHelper(popView, mContext)
                        pop.showFromBottomMatch(layout_release)

//                        Click.viewClick(popView.findViewById(R.id.tv_release_ye)).subscribe {
//                            var mCache = ACache.get(this)
//                            mCache.getAsString("HistoryReleaseYe")
//                            if (mCache.getAsString("HistoryReleaseYe") != null && !"".equals(mCache.getAsString("HistoryReleaseYe"))) {
//                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的夜计划草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
//                                    override fun onClick(dialog: DialogInterface, which: Int) {
//                                        when (which) {
//                                            DialogInterface.BUTTON_POSITIVE -> {
//                                                dialog.dismiss()
//                                                intentUtils.intentReleaseYe()
//                                                pop.dismiss()
//                                            }
//                                            DialogInterface.BUTTON_NEGATIVE -> {
//                                                dialog.dismiss()
//                                                mCache.put("HistoryReleaseYe", "")
//                                                intentUtils.intentReleaseYe()
//                                                pop.dismiss()
//                                            }
//                                        }
//                                    }
//                                })
//                            } else {
//                                intentUtils.intentReleaseYe()
//                                pop.dismiss()
//                            }
//
//                        }

                        Click.viewClick(popView.findViewById(R.id.close)).subscribe {
                            pop.dismiss()
                        }

                        Click.viewClick(popView.findViewById(R.id.tv_release_text)).subscribe {
                            var mCache = ACache.get(this)
                            mCache.getAsString("HistoryRelease")
                            if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的动态草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, which: Int) {
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> {
                                                dialog.dismiss()
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                dialog.dismiss()
//                                var mCache = ACache.
                                                mCache.put("HistoryRelease", "")
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                        }
                                    }
                                })
                            } else {
                                intentUtils.intentRelease()
                                pop.dismiss()
                            }


                        }

                        Click.viewClick(popView.findViewById(R.id.tv_release_pic)).subscribe {
                            var mCache = ACache.get(this)
                            mCache.getAsString("HistoryRelease")
                            if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的动态草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, which: Int) {
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> {
                                                dialog.dismiss()
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                dialog.dismiss()
//                                var mCache = ACache.
                                                mCache.put("HistoryRelease", "")
                                                intentUtils.intentRelease("照片")
                                                pop.dismiss()
                                            }
                                        }
                                    }
                                })
                            } else {
                                intentUtils.intentRelease("照片")
                                pop.dismiss()
                            }


                        }
                        Click.viewClick(popView.findViewById(R.id.tv_release_video)).subscribe {
                            var mCache = ACache.get(this)
                            mCache.getAsString("HistoryRelease")
                            if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的动态草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, which: Int) {
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> {
                                                dialog.dismiss()
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                dialog.dismiss()
//                                var mCache = ACache.
                                                mCache.put("HistoryRelease", "")
                                                intentUtils.intentRelease("视频")
                                                pop.dismiss()
                                            }
                                        }
                                    }
                                })
                            } else {
                                intentUtils.intentRelease("视频")
                                pop.dismiss()
                            }


                        }
                        Click.viewClick(popView.findViewById(R.id.tv_release_location)).subscribe {
                            var mCache = ACache.get(this)
                            mCache.getAsString("HistoryRelease")
                            if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的动态草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, which: Int) {
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> {
                                                dialog.dismiss()
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                dialog.dismiss()
//                                var mCache = ACache.
                                                mCache.put("HistoryRelease", "")
                                                intentUtils.intentRelease("打卡")
                                                pop.dismiss()
                                            }
                                        }
                                    }
                                })
                            } else {
                                intentUtils.intentRelease("打卡")
                                pop.dismiss()
                            }


                        }
                    } else if (inf != null && inf.size > 0) {
                        popView = LayoutInflater.from(mContext!!).inflate(R.layout.dialog_release_business, null)
                        pop = PopupWindowHelper(popView, mContext)
                        pop.showFromBottomMatch(layout_release)
                        Click.viewClick(popView.findViewById(R.id.tv_release_ye)).subscribe {
                            var mCache = ACache.get(this)
                            mCache.getAsString("HistoryReleaseBusiness")
                            if (mCache.getAsString("HistoryReleaseBusiness") != null && !"".equals(mCache.getAsString("HistoryReleaseBusiness"))) {
                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的夜计划草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, which: Int) {
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> {
                                                dialog.dismiss()
                                                intentUtils.intentReleaseYe()
                                                pop.dismiss()
                                            }
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                dialog.dismiss()
                                                mCache.put("HistoryReleaseBusiness", "")
                                                intentUtils.intentReleaseYe()
                                                pop.dismiss()
                                            }
                                        }
                                    }
                                })
                            } else {
                                intentUtils.intentReleaseYe()
                                pop.dismiss()
                            }

                        }

                        Click.viewClick(popView.findViewById(R.id.close)).subscribe {
                            pop.dismiss()
                        }

                        Click.viewClick(popView.findViewById(R.id.tv_release_text)).subscribe {
                            var mCache = ACache.get(this)
                            mCache.getAsString("HistoryRelease")
                            if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的动态草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, which: Int) {
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> {
                                                dialog.dismiss()
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                dialog.dismiss()
//                                var mCache = ACache.
                                                mCache.put("HistoryRelease", "")
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                        }
                                    }
                                })
                            } else {
                                intentUtils.intentRelease()
                                pop.dismiss()
                            }


                        }

                        Click.viewClick(popView.findViewById(R.id.tv_release_pic)).subscribe {
                            var mCache = ACache.get(this)
                            mCache.getAsString("HistoryRelease")
                            if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的动态草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, which: Int) {
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> {
                                                dialog.dismiss()
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                dialog.dismiss()
//                                var mCache = ACache.
                                                mCache.put("HistoryRelease", "")
                                                intentUtils.intentRelease("照片")
                                                pop.dismiss()
                                            }
                                        }
                                    }
                                })
                            } else {
                                intentUtils.intentRelease("照片")
                                pop.dismiss()
                            }


                        }
                        Click.viewClick(popView.findViewById(R.id.tv_release_video)).subscribe {
                            var mCache = ACache.get(this)
                            mCache.getAsString("HistoryRelease")
                            if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的动态草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, which: Int) {
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> {
                                                dialog.dismiss()
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                dialog.dismiss()
//                                var mCache = ACache.
                                                mCache.put("HistoryRelease", "")
                                                intentUtils.intentRelease("视频")
                                                pop.dismiss()
                                            }
                                        }
                                    }
                                })
                            } else {
                                intentUtils.intentRelease("视频")
                                pop.dismiss()
                            }


                        }
                        Click.viewClick(popView.findViewById(R.id.tv_release_location)).subscribe {
                            var mCache = ACache.get(this)
                            mCache.getAsString("HistoryRelease")
                            if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
                                ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的动态草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, which: Int) {
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> {
                                                dialog.dismiss()
                                                intentUtils.intentRelease()
                                                pop.dismiss()
                                            }
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                dialog.dismiss()
//                                var mCache = ACache.
                                                mCache.put("HistoryRelease", "")
                                                intentUtils.intentRelease("打卡")
                                                pop.dismiss()
                                            }
                                        }
                                    }
                                })
                            } else {
                                intentUtils.intentRelease("打卡")
                                pop.dismiss()
                            }


                        }
                    }
                }


            }else{
                var dialog=LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }

    }

    fun chooseBottom(i:Int){
        when(i){
            1 ->{
                vp_main.currentItem=0
                tv_community.visibility=View.GONE
                iv_community.visibility=View.VISIBLE
                tv_explore.visibility=View.VISIBLE
                iv_explore.visibility=View.GONE
                tv_news.visibility=View.VISIBLE
                iv_news.visibility=View.GONE
                tv_mine.visibility=View.VISIBLE
                iv_mine_header.visibility=View.GONE
                if (badge2!=null){
                    badge2!!.setBadgeEnable(false)
                }
                if (badge1!=null){
                    badge1!!.setBadgeEnable(true)
                }
            }

            2 ->{
                vp_main.currentItem=1
                tv_explore.visibility=View.GONE
                iv_explore.visibility=View.VISIBLE
                tv_community.visibility=View.VISIBLE
                iv_community.visibility=View.GONE
                tv_news.visibility=View.VISIBLE
                iv_news.visibility=View.GONE
                tv_mine.visibility=View.VISIBLE
                iv_mine_header.visibility=View.GONE
                if (badge2!=null){
                    badge2!!.setBadgeEnable(false)
                }
                if (badge1!=null){
                    badge1!!.setBadgeEnable(true)
                }
            }

            3 ->{
                vp_main.currentItem=2
                tv_news.visibility=View.GONE
                iv_news.visibility=View.VISIBLE
                tv_explore.visibility=View.VISIBLE
                iv_explore.visibility=View.GONE
                tv_community.visibility=View.VISIBLE
                iv_community.visibility=View.GONE
                tv_mine.visibility=View.VISIBLE
                iv_mine_header.visibility=View.GONE
                if (badge2!=null){
                    badge2!!.setBadgeEnable(true)
                }
                if (badge1!=null){
                    badge1!!.setBadgeEnable(false)
                }
            }

            4 ->{
                vp_main.currentItem=3
                tv_mine.visibility=View.GONE
                iv_mine_header.visibility=View.VISIBLE
                tv_explore.visibility=View.VISIBLE
                iv_explore.visibility=View.GONE
                tv_news.visibility=View.VISIBLE
                iv_news.visibility=View.GONE
                tv_community.visibility=View.VISIBLE
                iv_community.visibility=View.GONE
                if (badge2!=null){
                    badge2!!.setBadgeEnable(false)
                }
                if (badge1!=null){
                    badge1!!.setBadgeEnable(true)
                }
            }
        }
    }

    public fun onEvent(event: NotificationClickEvent){
        var mUserInfo=event.message.fromUser
        var intent= Intent()
        intent.setClass(this, ChatActivity::class.java)
        //创建会话
        intent.putExtra(JGApplication.TARGET_ID, mUserInfo.getUserName())
        intent.putExtra(JGApplication.TARGET_APP_KEY, mUserInfo.getAppKey())
        var notename = mUserInfo.getNotename()
        if (TextUtils.isEmpty(notename)) {
            notename = mUserInfo.getNickname()
            if (TextUtils.isEmpty(notename)) {
                notename = mUserInfo.getUserName()
            }
        }
        intent.putExtra(JGApplication.CONV_TITLE, notename)
        var conv: Conversation? = JMessageClient.getSingleConversation(mUserInfo.getUserName(), mUserInfo.getAppKey())
        //如果会话为空，使用EventBus通知会话列表添加新会话
        if (conv == null) {
            conv = Conversation.createSingleConversation(mUserInfo.getUserName(), mUserInfo.getAppKey())
            EventBus.getDefault().post(Event.Builder()
                    .setType(EventType.createConversation)
                    .setConversation(conv)
                    .build())
        }
        startActivity(intent)
    }

    private var badge1:BadgeHelper?=null
    private var badge2:BadgeHelper?=null
    fun setNews(flag:Boolean){
        if (tv_news.visibility==View.VISIBLE) {
            if (flag) {
                if (badge1 == null) {
                    badge1 = BadgeHelper(this).setBadgeType(BadgeHelper.Type.TYPE_POINT).setBadgeOverlap(true)
                }
                badge1!!.bindToTargetView(tv_news)
                badge1!!.setBadgeEnable(true)
            } else {
                if (badge1 != null) {
                    badge1!!.setBadgeEnable(false)
                }
                if (badge2 != null) {
                    badge2!!.setBadgeEnable(false)
                }
                badge1=null
                badge2=null
            }
        }else{
            if (flag) {
                if (badge2 == null) {
                    badge2 = BadgeHelper(this).setBadgeType(BadgeHelper.Type.TYPE_POINT).setBadgeOverlap(true)
                }
                badge2!!.bindToTargetView(iv_news)
                badge2!!.setBadgeEnable(true)
            } else {
                if (badge1 != null) {
                    badge1!!.setBadgeEnable(false)
                }
                if (badge2 != null) {
                    badge2!!.setBadgeEnable(false)
                }
                badge1=null
                badge2=null
            }
        }
    }

    private var isClose=false
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if(KeyEvent.KEYCODE_BACK==keyCode){
            if (!isClose){
                isClose=true
                Toast.Tips("再按一次返回退出")
                Handler().postDelayed(object :Runnable{
                    override fun run() {
                        isClose=false
                    }
                },3000)
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    fun setHead(avatar:String){
        ImageLoad.setUserHead(avatar,iv_mine_header)
    }

}
