package wanbao.yongchao.com.wanbao.ui.main.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.callback.GetUserInfoCallback
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.UserInfo
import cn.jpush.im.android.eventbus.EventBus
import com.blankj.utilcode.util.LogUtils
import jiguang.chat.activity.ChatActivity
import jiguang.chat.entity.Event
import jiguang.chat.entity.EventType
import kotlinx.android.synthetic.main.activity_business_homepage.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo
import wanbao.yongchao.com.wanbao.ui.image.ImageInfo
import wanbao.yongchao.com.wanbao.ui.main.adapter.FragmentAdapter
import wanbao.yongchao.com.wanbao.ui.main.dialog.*
import wanbao.yongchao.com.wanbao.ui.main.fragment.TopicFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.UserHomePageBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UserHomePageBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UserHomePageForNameBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.UserHomePagePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.UserHomePageView
import wanbao.yongchao.com.wanbao.ui.main.utils.banner.BannerUtil
import wanbao.yongchao.com.wanbao.utils.dialog.showPhoneDialog
import wanbao.yongchao.com.wanbao.utils.http.BaseUrl
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.share.ShareUtil
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllSpan
import java.io.File
import java.math.BigDecimal
import java.net.URISyntaxException

//import wanbao.yongchao.com.wanbao.ui.main.fragment.CommunityHotFragment

class BusinessHomePageActivity:BaseActivity(),UserHomePageView,HomeMoreDialog.More,SelectMapDialog.SelectMapDialogFace{
    override fun gaodeBtn() {
        if (isPackageInstalled("com.autonavi.minimap")){
            var intent= Intent()
            intent.action= Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            var uri= Uri.parse("amapuri://route/plan/?did=BGVIS2&dlat=" + lat
                    + "&dlon=" + lng + "&dname=${name}&dev=0&t=0")
            intent.data=uri
            startActivity(intent)
        }else{
            Toast.Tips("请安装高德地图")
        }
    }

    override fun baiduBtn() {
        if (isPackageInstalled("com.baidu.BaiduMap")){
            try {
//                name:对外经贸大学|latlng:39.98871,116.43234
                var pareUrl = "baidumap://map/direction?region=" +
                        "&destination=latlng:" + lat + "," + lng +"|name:"+name+ "&coord_type=bd09ll&src=andr.bixinyule.ServeBixin"
                var intent = Intent.getIntent(pareUrl)
                startActivity(intent)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }else{
            Toast.Tips("请安装百度地图")
        }
    }

    override fun tencentBtn() {

        if (isPackageInstalled("com.tencent.map")){
            var intent= Intent()
            intent.action= Intent.ACTION_VIEW
            var uri= Uri.parse("qqmap://map/routeplan?type=drive&from=我的位置&fromcoord=0,0"
                    + "&to=" + name
                    + "&tocoord=" + lat + "," + lng
                    + "&policy=1&referer=myapp")
            intent.data=uri
            startActivity(intent)
        }else{
            Toast.Tips("请安装腾讯地图")
        }

    }

    //判断包名是否存在
    fun isPackageInstalled(packageName: String): Boolean {
        return File("/data/data/$packageName").exists()
    }

    private lateinit var mapdialog: SelectMapDialog
    private var lat=""
    private var lng=""
    private var name=""
    private var user_id=""
    private var bussiness_id=""
    private var Type="1"

    override fun setMoreShareWX() {
//        Toast.Tips("分享到微信")
        if (user_id!=""){
            ShareUtil.shareWxCircle(this,"这家店不错，改天一起去探探","这家店不错，改天一起去探探", BaseUrl.HOST_URL+"share/page?id="+user_id+"&type=1")
        }else if (bussiness_id!=""){
            ShareUtil.shareWxCircle(this,"这家店不错，改天一起去探探","这家店不错，改天一起去探探", BaseUrl.HOST_URL+"share/page?id="+bussiness_id+"&type=2")
        }

    }

    override fun setMoreSharePYQ() {
//        Toast.Tips("分享到朋友圈")
//        ShareUtil.shareWx(this,"这家店不错，改天一起去探探","这家店不错，改天一起去探探",BaseUrl.HOST_URL+"share/page?id="+user_id+"&type=1")
        if (user_id!=""){
            ShareUtil.shareWx(this,"这家店不错，改天一起去探探","这家店不错，改天一起去探探",BaseUrl.HOST_URL+"share/page?id="+user_id+"&type=1")
        }else if (bussiness_id!=""){
            ShareUtil.shareWx(this,"这家店不错，改天一起去探探","这家店不错，改天一起去探探",BaseUrl.HOST_URL+"share/page?id="+bussiness_id+"&type=2")
        }
    }

    override fun setMoreShareQQ() {
//        Toast.Tips("分享到QQ")
//        ShareUtil.QQShare(this,"晚豹App","这家店不错，改天一起去探探",BaseUrl.HOST_URL+"share/page?id="+user_id+"&type=1")
        if (user_id!=""){
            ShareUtil.QQShare(this,"晚豹App","这家店不错，改天一起去探探",BaseUrl.HOST_URL+"share/page?id="+user_id+"&type=1")
        }else if (bussiness_id!=""){
            ShareUtil.QQShare(this,"晚豹App","这家店不错，改天一起去探探",BaseUrl.HOST_URL+"share/page?id="+bussiness_id+"&type=2")
        }
    }

    override fun setMoreReport() {
        if (isLogin) {
            dialog = HomeReportDialog(id)
            dialog.show(supportFragmentManager, "")
        }else{
            var dialog= LoginDialog()
            dialog.show(supportFragmentManager,"")
        }
    }

    override fun setMoreCollect() {//拉黑
        if (isLogin) {
            if (type) {
                presenter.getUnFocus(UnFocusBody(id, "0"))
            } else {
                presenter.getFocus(FocusBody(id, "0"))
            }
        }else{
            var dialog= LoginDialog()
            dialog.show(supportFragmentManager,"")
        }
    }

    override fun getFocusRequest() {
        if (isBlank){
            Toast.Tips("已拉黑")
            type=true
            if (intent.getStringExtra("id")!=null&&intent.getStringExtra("id")!=""){
                presenter.getUserHomePage(UserHomePageBody(intent.getStringExtra("id")))
            }else if (intent.getStringExtra("name")!=null&&intent.getStringExtra("name")!=""){
                presenter.getUserHomePageForName(UserHomePageForNameBody(intent.getStringExtra("name")))
            }
        }else {
            Toast.Tips("已关注")
            type=false
            if (intent.getStringExtra("id")!=null&&intent.getStringExtra("id")!=""){
                presenter.getUserHomePage(UserHomePageBody(intent.getStringExtra("id")))
            }else if (intent.getStringExtra("name")!=null&&intent.getStringExtra("name")!=""){
                presenter.getUserHomePageForName(UserHomePageForNameBody(intent.getStringExtra("name")))
            }
        }
    }

    override fun getUnFocusRequest() {
        if (isBlank){
            Toast.Tips("已移出黑名单")
            type=false
            if (intent.getStringExtra("id")!=null&&intent.getStringExtra("id")!=""){
                presenter.getUserHomePage(UserHomePageBody(intent.getStringExtra("id")))
            }else if (intent.getStringExtra("name")!=null&&intent.getStringExtra("name")!=""){
                presenter.getUserHomePageForName(UserHomePageForNameBody(intent.getStringExtra("name")))
            }
        }else {
            Toast.Tips("已取消")
            type=false
            if (intent.getStringExtra("id")!=null&&intent.getStringExtra("id")!=""){
                presenter.getUserHomePage(UserHomePageBody(intent.getStringExtra("id")))
            }else if (intent.getStringExtra("name")!=null&&intent.getStringExtra("name")!=""){
                presenter.getUserHomePageForName(UserHomePageForNameBody(intent.getStringExtra("name")))
            }
        }
    }

    override fun getUserHomePageRequest(data: UserHomePageBean) {

        if (data.data.role==2) {//商家
            layout_business.visibility=View.VISIBLE
            layout_user.visibility=View.GONE
            bussiness_id=data.data.id
            var imagelist = ArrayList<ImageBannerInfo>()
            if (data.data.images!=null&&data.data.images.size>0) {
                data.data.images.forEach {
                    imagelist.add(ImageBannerInfo(it, false, 0, "", "", ""))
                }
            }else{
                imagelist.add(ImageBannerInfo(data.data.avatar, false, 0, "", "", ""))
            }
            BannerUtil.setBanner(banner, imagelist)
            ImageLoad.setUserHead(data.data.avatar, iv_header)
            var num = BigDecimal(data.data.dynamicNum)
            if (data.data.dynamicNum < 1000) {
                tv_community_num.text = data.data.dynamicNum.toString()
            } else if (data.data.dynamicNum < 10000) {
                tv_community_num.text = num.divide(BigDecimal("1000")).setScale(1, BigDecimal.ROUND_DOWN).toString() + "K"
            } else {
                tv_community_num.text = num.divide(BigDecimal("10000")).setScale(1, BigDecimal.ROUND_DOWN).toString() + "W"
            }

            if (data.data.focusNum < 1000) {
                tv_follow_num.text = data.data.focusNum.toString()
            } else if (data.data.focusNum < 10000) {
                tv_follow_num.text = BigDecimal(data.data.focusNum).divide(BigDecimal("1000")).setScale(1, BigDecimal.ROUND_DOWN).toString() + "K"
            } else {
                tv_follow_num.text = BigDecimal(data.data.focusNum).divide(BigDecimal("10000")).setScale(1, BigDecimal.ROUND_DOWN).toString() + "W"
            }

            if (data.data.fansNum < 1000) {
                tv_fans_num.text = data.data.fansNum.toString()
            } else if (data.data.fansNum < 10000) {
                tv_fans_num.text = BigDecimal(data.data.fansNum).divide(BigDecimal("1000")).setScale(1, BigDecimal.ROUND_DOWN).toString() + "K"
            } else {
                tv_fans_num.text = BigDecimal(data.data.fansNum).divide(BigDecimal("10000")).setScale(1, BigDecimal.ROUND_DOWN).toString() + "W"
            }

            tv_name.text = data.data.nickname
            tv_title.text = data.data.nickname
            tv_Id.text = "id：" + data.data.id
            tv_address.text = data.data.address
            tv_time.text = data.data.openTime + "~" + data.data.closeTime
            if (data.data.perPersonConsume!=null&&data.data.perPersonConsume.compareTo(BigDecimal.ZERO)!=0){
                layout_money.visibility=View.VISIBLE
                tv_money.text = data.data.perPersonConsume.setScale(0,BigDecimal.ROUND_DOWN).toString() + "RMB/人"
            }else{
                layout_money.visibility=View.GONE
            }

            if(data.data.authType!=null&&data.data.authType==3){
                layout_business_auth.visibility=View.VISIBLE
            }else{
                layout_business_auth.visibility=View.GONE
            }

            if (data.data.focusType!=null&&data.data.focusType!=""&&data.data.focusType=="0"){
                type=true
            }else{
                type=false
            }
            id=data.data.id.toString()

//        var titles = ArrayList<String>()
//        titles.add("动态")
//        titles.add("打卡")
//
//
//        titles.forEach {
//            fragments.add(TopicFragment(it))
//        }
            if (fragment1!=null&&fragment2!=null){
                if (mCurrentFragment!=null&&mCurrentFragment==fragment1){
                    (fragment1!! as TopicFragment).setRefresh()
                }else if (mCurrentFragment!=null&&mCurrentFragment==fragment2){
                    (fragment2!! as TopicFragment).setRefresh()
                }
            }else {
                initFragmentBusiness(data.data.id.toString(), data.data.locationId)
            }
            tv_explain_all.text = data.data.signature
            tv_explain_short.maxShowLines = 2
            tv_explain_short.setMyText(data.data.signature, "详细")
            tv_explain_short.setOnAllSpanClickListener(object : ShowAllSpan.OnAllSpanClickListener {
                override fun onClickAt(widget: View?, name: String?) {

                }

                override fun onClick(widget: View?) {
                    tv_explain_all.visibility = View.VISIBLE
                    tv_explain_short.visibility = View.GONE
                }
            })
            try {
                var user=DBUtils.getBusiness()
                if (user.businessId==data.data.id.toString()){
                    tv_focus.visibility=View.GONE
                }else{
                    tv_focus.visibility=View.VISIBLE
                }
            }catch (e:Exception){

            }

            if (data.data.focusType!=null&&data.data.focusType!=""){
                if (data.data.focusType=="1"){
                    tv_focus.text="已关注"
                    tv_focus.setTextColor(Color.parseColor("#FCC725"))
                    tv_focus.setBackgroundResource(R.drawable.tv_followed_bg)
                }else{
                    tv_focus.text="关注"
                    tv_focus.setTextColor(Color.parseColor("#000000"))
                    tv_focus.setBackgroundResource(R.drawable.tv_follow_bg)
                }
            }else{
                tv_focus.text="关注"
                tv_focus.setTextColor(Color.parseColor("#000000"))
                tv_focus.setBackgroundResource(R.drawable.tv_follow_bg)
            }

            Click.viewClick(tv_focus).subscribe {
                if (isLogin) {
                    isBlank = false
                    if (data.data.focusType == "1") {
                        presenter.getUnFocus(UnFocusBody(data.data.id.toString(), "1"))
                    } else {
                        presenter.getFocus(FocusBody(data.data.id.toString(), "1"))
                    }
                }else{
                    var dialog= LoginDialog()
                    dialog.show(supportFragmentManager,"")
                }
            }

            Click.viewClick(layout_fans).subscribe {
                intentUtils.intentFans(data.data.id.toString(),"粉丝")
            }

            Click.viewClick(layout_like).subscribe {
                intentUtils.intentFans(data.data.id.toString(),"关注")
            }

            var business= GreenDaoHelper.getDaoSessions().businessDBDao
            if (business!=null){
                var info=business.loadAll()
                if (info != null && info.size > 0) {
                    if (info[0].businessId==data.data.id){
                        isMine=true
                        btn_homepage.visibility=View.GONE
                    }else{
                        isMine=false
                        btn_homepage.visibility=View.VISIBLE
                    }
                }
            }

            Click.viewClick(btn_homepage).subscribe {
                if (isLogin){
                    if (flag) {
                        flag=false
                        JMessageClient.getUserInfo(data.data.id.toString(), "feec8ae8389113d4bf6915c5", object : GetUserInfoCallback() {
                            override fun gotResult(p0: Int, p1: String, p2: UserInfo) {
                                LogUtils.a("测试", "进来了${p1}")
                                if (p0 == 0) {
                                    LogUtils.a("测试", "进来了")
                                    flag=true
                                    var mUserInfo = p2
                                    var intent = Intent(this@BusinessHomePageActivity, ChatActivity::class.java)
                                    //创建会话
                                    intent.putExtra("targetId", mUserInfo.getUserName())
                                    intent.putExtra("targetAppKey", mUserInfo.getAppKey())
                                    var notename = mUserInfo.getNotename()
                                    if (TextUtils.isEmpty(notename)) {
                                        notename = mUserInfo.getNickname()
                                        if (TextUtils.isEmpty(notename)) {
                                            notename = mUserInfo.getUserName()
                                        }
                                    }
                                    intent.putExtra("conv_title", notename)
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
                            }
                        })
                    }else{
                        Toast.Tips("聊天正在创建中。。。")
                    }
                }else{
                    var dialog= LoginDialog()
                    dialog.show(supportFragmentManager,"")
                }
            }

            Click.viewClick(iv_phone).subscribe {
                var dialog=CallPhoneDialog(data.data.phone)
                dialog.show(supportFragmentManager,"")
            }

            Click.viewClick(layout_address).subscribe {
                lat=data.data.latitude.toString()
                lng=data.data.longitude.toString()
                name=data.data.nickname
                mapdialog.showDialog()
            }

            Click.viewClick(iv_more).subscribe {
                isBlank=true
                moreDialog= HomeMoreDialog(this,type,isMine)
                moreDialog!!.show(supportFragmentManager,"")
            }

            Click.viewClick(iv_title_more).subscribe {
                isBlank=true
                moreDialog= HomeMoreDialog(this,type,isMine)
                moreDialog!!.show(supportFragmentManager,"")
            }

            var list = java.util.ArrayList<ImageInfo>()
            list.add(ImageInfo(data.data.avatar,false,0))
            Click.viewClick(iv_header).subscribe {
                intentUtils.intentImage(false,list,0)
            }



        }else{
            layout_business.visibility=View.GONE
            layout_user.visibility=View.VISIBLE
            user_id=data.data.id
            if (data.data.images!=null&&data.data.images.size>0) {
                ImageLoad.setImage(data.data.images[0], banner_user)
                var images = java.util.ArrayList<ImageInfo>()
                images.add(ImageInfo(data.data.images[0],false,0))
                Click.viewClick(banner_user).subscribe {
                    intentUtils.intentImage(false,images,0)
                }
            }else{
                ImageLoad.setImage(data.data.avatar, banner_user)
                var images = java.util.ArrayList<ImageInfo>()
                images.add(ImageInfo(data.data.avatar,false,0))
                Click.viewClick(banner_user).subscribe {
                    intentUtils.intentImage(false,images,0)
                }
            }
            ImageLoad.setUserHead(data.data.avatar,iv_header_user)
            var num=BigDecimal(data.data.dynamicNum)
            if (data.data.dynamicNum<1000){
                tv_community_num_user.text=data.data.dynamicNum.toString()
            }else if (data.data.dynamicNum<10000){
                tv_community_num_user.text=num.divide(BigDecimal("1000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"K"
            }else{
                tv_community_num_user.text=num.divide(BigDecimal("10000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"W"
            }

            if (data.data.focusNum<1000){
                tv_follow_num_user.text=data.data.focusNum.toString()
            }else if (data.data.focusNum<10000){
                tv_follow_num_user.text=BigDecimal(data.data.focusNum).divide(BigDecimal("1000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"K"
            }else{
                tv_follow_num_user.text=BigDecimal(data.data.focusNum).divide(BigDecimal("10000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"W"
            }

            if (data.data.fansNum<1000){
                tv_fans_num_user.text=data.data.fansNum.toString()
            }else if (data.data.fansNum<10000){
                tv_fans_num_user.text=BigDecimal(data.data.fansNum).divide(BigDecimal("1000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"K"
            }else{
                tv_fans_num_user.text=BigDecimal(data.data.fansNum).divide(BigDecimal("10000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"W"
            }

            tv_name_user.text=data.data.nickname
            tv_title_user.text=data.data.nickname
            tv_Id_user.text="id："+data.data.id
            tv_age_user.text=data.data.age.toString()
            if (data.data.gender==1){//男
                var draw=mContext.resources.getDrawable(R.mipmap.suerhomepage_icon_man)
                draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight())
                tv_age_user.setCompoundDrawables(draw,null,null,null)
            }else{
                var draw=mContext.resources.getDrawable(R.mipmap.suerhomepage_icon_lady)
                draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight())
                tv_age_user.setCompoundDrawables(draw,null,null,null)
            }

            if (data.data.tags!=null&&data.data.tags.size>0){
                var tags=ArrayList<String>()
                data.data.tags.forEach {
                    tags.add(it.description)
                }
                tag_user.setList(tags)
                tag_user.visibility=View.VISIBLE
            }else{
                tag_user.visibility=View.GONE
            }

            if (data.data.authType!=null) {
                if (data.data.authType == 1) {
                    layout_auth_user.visibility = View.VISIBLE
                    layout_realname_user.visibility = View.VISIBLE
                    layout_blogger_user.visibility = View.GONE
                }else if (data.data.authType==2){
                    layout_auth_user.visibility = View.VISIBLE
                    layout_realname_user.visibility = View.GONE
                    layout_blogger_user.visibility = View.VISIBLE
                    tv_blogger_user.text=data.data.authTypeName
                }
            }else{
                layout_auth_user.visibility = View.GONE
                layout_realname_user.visibility = View.GONE
                layout_blogger_user.visibility = View.GONE
            }

            if (data.data.focusType!=null&&data.data.focusType!=""&&data.data.focusType=="0"){
                type=true
            }else{
                type=false
            }
            id=data.data.id.toString()

//        var titles = ArrayList<String>()
//        titles.add("动态")
//        titles.add("打卡")
//
//
//        titles.forEach {
//            fragments.add(TopicFragment(it))
//        }
            if (data.data.authType!=null&&data.data.authType!=0){
                layout_auth_user.visibility=View.VISIBLE
                if (data.data.authType==1){//实名
                    layout_realname_user.visibility=View.VISIBLE
                    layout_blogger_user.visibility=View.GONE
                }else if (data.data.authType==2){//大V
                    layout_realname_user.visibility=View.GONE
                    layout_blogger_user.visibility=View.VISIBLE
                    tv_blogger_user.text=data.data.authTypeName
                }
            }else{
                layout_auth_user.visibility=View.GONE
            }
            tv_explain_all_user.text=data.data.signature
            tv_explain_short_user.maxShowLines=2
            tv_explain_short_user.setMyText(data.data.signature,"详细")
            tv_explain_short_user.setOnAllSpanClickListener (object : ShowAllSpan.OnAllSpanClickListener{
                override fun onClickAt(widget: View?, name: String?) {

                }

                override fun onClick(widget: View?) {
                    tv_explain_all_user.visibility=View.VISIBLE
                    tv_explain_short_user.visibility=View.GONE
                }
            })

            if (fragment!=null){
                fragment!!.setRefresh()
            }else {
                initFragment(data.data.id.toString())
            }

            try {
                var user=DBUtils.getUser()
                if (user.userId==data.data.id.toString()){
                    tv_focus_user.visibility=View.GONE
                }else{
                    tv_focus_user.visibility=View.VISIBLE
                }
            }catch (e:Exception){

            }

            if (data.data.focusType!=null&&data.data.focusType!=""){
                if (data.data.focusType=="1"){
                    tv_focus_user.text="已关注"
                    tv_focus_user.setTextColor(Color.parseColor("#FCC725"))
                    tv_focus_user.setBackgroundResource(R.drawable.tv_followed_bg)
                }else{
                    tv_focus_user.text="关注"
                    tv_focus_user.setTextColor(Color.parseColor("#000000"))
                    tv_focus_user.setBackgroundResource(R.drawable.tv_follow_bg)
                }
            }else{
                tv_focus_user.text="关注"
                tv_focus_user.setTextColor(Color.parseColor("#000000"))
                tv_focus_user.setBackgroundResource(R.drawable.tv_follow_bg)
            }

            Click.viewClick(tv_focus_user).subscribe {
                if (isLogin) {
                    isBlank = false
                    if (data.data.focusType == "1") {
                        presenter.getUnFocus(UnFocusBody(data.data.id.toString(), "1"))
                    } else {
                        presenter.getFocus(FocusBody(data.data.id.toString(), "1"))
                    }
                }else{
                    var dialog= LoginDialog()
                    dialog.show(supportFragmentManager,"")
                }
            }

            Click.viewClick(layout_fans_user).subscribe {
                intentUtils.intentFans(data.data.id.toString(),"粉丝")
            }

            Click.viewClick(layout_like_user).subscribe {
                intentUtils.intentFans(data.data.id.toString(),"关注")
            }

            var user= GreenDaoHelper.getDaoSessions().userDBDao
            if (user!=null){
                var info=user.loadAll()
                if (info != null && info.size > 0) {
                    if (info[0].userId==data.data.id){
                        isMine=true
                        btn_homepage_user.visibility=View.GONE
                    }else{
                        isMine=false
                        btn_homepage_user.visibility=View.VISIBLE
                    }
                }
            }

            Click.viewClick(btn_homepage_user).subscribe {
                if (isLogin){
                    if (flag) {
                        flag=false
                        JMessageClient.getUserInfo(data.data.id.toString(), "feec8ae8389113d4bf6915c5", object : GetUserInfoCallback() {
                            override fun gotResult(p0: Int, p1: String, p2: UserInfo) {
                                LogUtils.a("测试", "进来了${p1}")
                                if (p0 == 0) {
                                    LogUtils.a("测试", "进来了")
                                    flag=true
                                    var mUserInfo = p2
                                    var intent = Intent(this@BusinessHomePageActivity, ChatActivity::class.java)
                                    //创建会话
                                    intent.putExtra("targetId", mUserInfo.getUserName())
                                    intent.putExtra("targetAppKey", mUserInfo.getAppKey())
                                    var notename = mUserInfo.getNotename()
                                    if (TextUtils.isEmpty(notename)) {
                                        notename = mUserInfo.getNickname()
                                        if (TextUtils.isEmpty(notename)) {
                                            notename = mUserInfo.getUserName()
                                        }
                                    }
                                    intent.putExtra("conv_title", notename)
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
                            }
                        })
                    }else{
                        Toast.Tips("聊天正在创建中。。。")
                    }
                }else{
                    var dialog= LoginDialog()
                    dialog.show(supportFragmentManager,"")
                }
            }


            Click.viewClick(iv_more_user).subscribe {
                isBlank=true
                moreDialog= HomeMoreDialog(this,type,isMine)
                moreDialog!!.show(supportFragmentManager,"")
            }

            Click.viewClick(iv_title_more_user).subscribe {
                isBlank=true
                moreDialog= HomeMoreDialog(this,type,isMine)
                moreDialog!!.show(supportFragmentManager,"")
            }

            var list = java.util.ArrayList<ImageInfo>()
            list.add(ImageInfo(data.data.avatar,false,0))
            Click.viewClick(iv_header_user).subscribe {
                intentUtils.intentImage(false,list,0)
            }
        }
    }

    override fun getUserHomePageError() {
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {

            }else if (inf != null && inf.size > 0) {

            }
        }
    }

//    var mFragmentAdapter: FragmentAdapter? = null
//    var fragments = ArrayList<Fragment>()

    var mCurrentFragment: BaseFragment? = null
    var fragment: TopicFragment? = null
    var fragment1: BaseFragment? = null
    var fragment2: BaseFragment? = null
    private val presenter by lazy { UserHomePagePresenter(this,this,this) }

    private var moreDialog:HomeMoreDialog?=null
    private var type=false
    private var id=""
    private var isBlank=false

    private lateinit var dialog:HomeReportDialog

    private var isMine=false
    private var isLogin=false


    private var flag=true

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_business_homepage

    override fun setActivityTitle() {

//        var h= SystemUtils.px2dip(this, SystemUtils.getScreenHeight(this).toFloat())-88.0f
//
//        var mheight= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,h,resources.displayMetrics)
//        var params = layout.layoutParams
//        params.height=mheight
//        Log.e("测试",mheight.toString())
//        val params = vp_homePage.getLayoutParams()
//        params.height=mheight.toInt()
//        vp_homePage.setLayoutParams(params)

    }

    override fun initActivityData() {

        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                isLogin=true
            }else if (inf != null && inf.size > 0) {
                isLogin=true
            }else{
                isLogin=false
            }
        } else{
            isLogin=false
        }

        if (intent.getStringExtra("id")!=null&&intent.getStringExtra("id")!=""){
            presenter.getUserHomePage(UserHomePageBody(intent.getStringExtra("id")))
        }else if (intent.getStringExtra("name")!=null&&intent.getStringExtra("name")!=""){
            presenter.getUserHomePageForName(UserHomePageForNameBody(intent.getStringExtra("name")))
        }

        mapdialog= SelectMapDialog(this)
        mapdialog.setDialogFace(this)

        scroll.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0:NestedScrollView,scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {

                if (scrollY == 0) {
                    //顶部
//                    manage.setmCanVerticalScroll(false)
                    iv_back.visibility=View.VISIBLE
                    iv_more.visibility=View.VISIBLE
                }else{
                    iv_back.visibility=View.GONE
                    iv_more.visibility=View.GONE
                }
                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
                var distanceScrollY = layout_top.height - layout_title.height+SystemUtils.dip2px(this@BusinessHomePageActivity,20f)
                if (scrollY >= distanceScrollY) {
                    layout_choose.visibility = View.VISIBLE
//                    ll_tab.visibility = View.INVISIBLE
//                    title_divider.visibility = View.VISIBLE
                } else {
                    layout_choose.visibility = View.INVISIBLE
//                    ll_tab.visibility = View.VISIBLE
//                    title_divider.visibility = View.GONE
                }

                var  contentView = scroll_user.getChildAt(0)
                if (contentView != null && contentView.getMeasuredHeight() == (scroll_user.getScrollY() + scroll_user.getHeight())) {
                    //底部
//                    manage.setmCanVerticalScroll(true)
                    Log.e("测试","滑动到底部了")
                    if (mCurrentFragment==fragment1){
                        (fragment1 as TopicFragment).setLoadMore()
                    }else{
                        (fragment2 as TopicFragment).setLoadMore()
                    }
                }
                //设置标题栏渐变
//                if (scrollY <= 0) {
//                    //初始位置：未滑动时，设置标题背景透明
//                    layout_title.setBackgroundColor(Color.TRANSPARENT)
////                    tv_title_text.setTextColor(Color.WHITE)
//                } else if (scrollY > 0 && scrollY <= distanceScrollY) {
//                    var scale: Float = (scrollY.toFloat()) / distanceScrollY
//                    var alpha: Float = 255 * scale
//                    layout_title.setBackgroundColor(Color.argb(alpha.toInt(), 255, 255, 255))
////                    tv_title_text.setTextColor(Color.argb(alpha.toInt(), 0, 0, 0))
//                } else {
//                    layout_title.setBackgroundColor(Color.argb(255, 255, 255, 255))
////                    tv_title_text.setTextColor(Color.argb(255, 0, 0, 0))
//                }
                var alpha=0f
                var h=SystemUtils.dip2px(this@BusinessHomePageActivity,180f)
                if (scrollY>=h){
                    alpha=1f
//                    manage.setmCanVerticalScroll(true)
                }else{
                    alpha=scrollY/h
//                    manage.setmCanVerticalScroll(false)
                }

                layout_title.alpha=alpha
                layout_title.visibility=View.VISIBLE
//
            }

        })

        scroll_user.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0:NestedScrollView,scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {

                if (scrollY == 0) {
                    //顶部
//                    manage.setmCanVerticalScroll(false)
                    iv_back_user.visibility=View.VISIBLE
                    iv_more_user.visibility=View.VISIBLE
                }else{
                    iv_back_user.visibility=View.GONE
                    iv_more_user.visibility=View.GONE
                }
                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
                var distanceScrollY = layout_top_user.height - layout_title_user.height+SystemUtils.dip2px(this@BusinessHomePageActivity,20f)
                if (scrollY >= distanceScrollY) {
                    layout_choose_user.visibility = View.VISIBLE
//                    ll_tab.visibility = View.INVISIBLE
//                    title_divider.visibility = View.VISIBLE
                } else {
                    layout_choose_user.visibility = View.INVISIBLE
//                    ll_tab.visibility = View.VISIBLE
//                    title_divider.visibility = View.GONE
                }

                var  contentView = scroll_user.getChildAt(0)
                if (contentView != null && contentView.getMeasuredHeight() == (scroll_user.getScrollY() + scroll_user.getHeight())) {
                    //底部
//                    manage.setmCanVerticalScroll(true)
                    Log.e("测试","滑动到底部了")
                    fragment!!.setLoadMore()
                }
                //设置标题栏渐变
//                if (scrollY <= 0) {
//                    //初始位置：未滑动时，设置标题背景透明
//                    layout_title.setBackgroundColor(Color.TRANSPARENT)
////                    tv_title_text.setTextColor(Color.WHITE)
//                } else if (scrollY > 0 && scrollY <= distanceScrollY) {
//                    var scale: Float = (scrollY.toFloat()) / distanceScrollY
//                    var alpha: Float = 255 * scale
//                    layout_title.setBackgroundColor(Color.argb(alpha.toInt(), 255, 255, 255))
////                    tv_title_text.setTextColor(Color.argb(alpha.toInt(), 0, 0, 0))
//                } else {
//                    layout_title.setBackgroundColor(Color.argb(255, 255, 255, 255))
////                    tv_title_text.setTextColor(Color.argb(255, 0, 0, 0))
//                }
                var alpha=0f
                var h=SystemUtils.dip2px(this@BusinessHomePageActivity,100f)
                if (scrollY>=h){
                    alpha=1f
//                    manage.setmCanVerticalScroll(true)
                }else{
                    alpha=scrollY/h
//                    manage.setmCanVerticalScroll(false)
                }

                layout_title_user.alpha=alpha
                layout_title_user.visibility=View.VISIBLE
//
            }

        })


    }

    override fun clickListener() {

        Click.viewClick(iv_back).subscribe {
            finish()
        }

        Click.viewClick(iv_title_back).subscribe {
            finish()
        }

        Click.viewClick(tv_content_choose1).subscribe {
            checkCommunity()
        }

        Click.viewClick(tv_content_choose2).subscribe {
            checkClock()
        }

        Click.viewClick(tv_choose1).subscribe {
            checkCommunity()
        }

        Click.viewClick(tv_choose2).subscribe {
            checkClock()
        }

        Click.viewClick(iv_back_user).subscribe {
            finish()
        }

        Click.viewClick(iv_title_back_user).subscribe {
            finish()
        }




    }

    //设置初始显示的Fragment
    private fun initFragment(id:String) {
        fragment = TopicFragment("动态",id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container_user, fragment!!)
        transaction.commitAllowingStateLoss()
//        transaction.show(fragment!!)
    }


    //设置初始显示的Fragment
    private fun initFragmentBusiness(id:String,location:String) {
        fragment1 = TopicFragment("动态",id)
        fragment2 = TopicFragment("打卡",location)
        mCurrentFragment = fragment1
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container, fragment1!!)
        transaction.commitAllowingStateLoss()
    }

    /**
     * 选中动态
     */
    private fun checkCommunity() {
        switchContent(mCurrentFragment!!, fragment1!!)

        tv_content_choose1.setTextColor(Color.parseColor("#FCC725"))
        tv_content_choose1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
        tv_content_choose2.setTextColor(Color.parseColor("#a6ffffff"))
        tv_content_choose2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)

        //悬浮导航栏选中效果
        tv_choose1.setTextColor(Color.parseColor("#FCC725"))
        tv_choose1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
        tv_choose2.setTextColor(Color.parseColor("#a6ffffff"))
        tv_choose2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
    }

    /**
     * 选中打卡
     */
    private fun checkClock() {
        switchContent(mCurrentFragment!!, fragment2!!)

        tv_content_choose2.setTextColor(Color.parseColor("#FCC725"))
        tv_content_choose2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
        tv_content_choose1.setTextColor(Color.parseColor("#a6ffffff"))
        tv_content_choose1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        //悬浮导航栏选中效果
        tv_choose2.setTextColor(Color.parseColor("#FCC725"))
        tv_choose2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
        tv_choose1.setTextColor(Color.parseColor("#a6ffffff"))
        tv_choose1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
    }

    /**
     * Fragment的切换
     */
    fun switchContent(fromFragment: BaseFragment, toFragment: BaseFragment) {
        if (fromFragment != toFragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.hide(fromFragment)
            if (!toFragment!!.isAdded) {
                transaction.add(R.id.fl_container, toFragment)
            } else {
                transaction.show(toFragment)
            }
            transaction.commitAllowingStateLoss()
            mCurrentFragment = toFragment
        }
    }
}