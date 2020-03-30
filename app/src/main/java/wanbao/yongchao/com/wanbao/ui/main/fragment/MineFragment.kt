package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.Manifest
import android.graphics.Color
import android.provider.MediaStore
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.model.UserInfo
import cn.jpush.im.api.BasicCallback
import com.blankj.utilcode.util.LogUtils
import com.makeramen.roundedimageview.RoundedDrawable
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_mine_business.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.fragment.ExploreActivityFragment
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.UserBean
import wanbao.yongchao.com.wanbao.ui.login.utils.TagsUtils
import wanbao.yongchao.com.wanbao.ui.main.activity.MainActivity
import wanbao.yongchao.com.wanbao.ui.mine.dialog.MineSetDialog
import wanbao.yongchao.com.wanbao.ui.mine.fragment.BusinessActivityFragment
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.UserInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.UserInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.MineUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineUserView
import wanbao.yongchao.com.wanbao.ui.mine.utils.BannerUtil
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.FileUtils
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils
import java.lang.Exception
import java.math.BigDecimal
import java.sql.Time

class MineFragment: BaseFragment(),MineUserView,MineSetDialog.SetDialog {
    override fun setMoreSet() {
        intentUtils.intentSet()
    }

    override fun setData() {
        if (role=="1") {
            intentUtils.intentUserInfo()
        }else if (role=="2"){
            intentUtils.intentBusinessInfo()
        }
    }

    override fun setCollect() {
        intentUtils.intentMineCollect()
    }

    override fun setWant() {
        intentUtils.intentMineWant()
    }

    override fun getUserInfoError() {
        layout_user.visibility=View.GONE
        layout_business.visibility=View.GONE
    }

    override fun getUserInfoRequest(data: UserInfoBean) {
        role=data.data.role

        if (data.data.role=="1") {
            layout_user.visibility=View.VISIBLE
            layout_business.visibility=View.GONE
            scroll_user.scrollTo(0,0)
            if (data.data.images != null && data.data.images.size > 0) {
                ImageLoad.setImage(data.data.images[0], banner_user)
            } else {
                ImageLoad.setImage(data.data.avatar, banner_user)
            }
            if (data.data.avatar != null && data.data.avatar != "") {
                ImageLoad.setUserHead(data.data.avatar, iv_header_user)
            }
            if (data.data.likeNum < 1000) {
                tv_like_num_user.text = data.data.likeNum.toString()
            } else if (data.data.likeNum < 10000) {
                tv_like_num_user.text = BigDecimal(data.data.likeNum).divide(BigDecimal(1000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "K"
            } else {
                tv_like_num_user.text = BigDecimal(data.data.likeNum).divide(BigDecimal(10000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "W"
            }

            if (data.data.fansNum < 1000) {
                tv_fans_num_user.text = data.data.fansNum.toString()
            } else if (data.data.fansNum < 10000) {
                tv_fans_num_user.text = BigDecimal(data.data.fansNum).divide(BigDecimal(1000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "K"
            } else {
                tv_fans_num_user.text = BigDecimal(data.data.fansNum).divide(BigDecimal(10000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "W"
            }

            if (data.data.focusNum < 1000) {
                tv_follow_num_user.text = data.data.focusNum.toString()
            } else if (data.data.focusNum < 10000) {
                tv_follow_num_user.text = BigDecimal(data.data.focusNum).divide(BigDecimal(1000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "K"
            } else {
                tv_follow_num_user.text = BigDecimal(data.data.focusNum).divide(BigDecimal(10000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "W"
            }

            if (data.data.authState == "1") {
                layout_none_auth.visibility = View.GONE
                if (data.data.authType == "1") {
                    layout_realname_user.visibility = View.VISIBLE
                    layout_blogger_user.visibility = View.GONE
                } else if (data.data.authType == "2") {
                    layout_realname_user.visibility = View.GONE
                    layout_blogger_user.visibility = View.VISIBLE
                    tv_blogger_user.text = data.data.authTypeName
                }
            } else {
                layout_realname_user.visibility = View.GONE
                layout_blogger_user.visibility = View.GONE
                layout_none_auth.visibility = View.VISIBLE
                if (data.data.authState == "3") {
                    tv_auth_tips.setTextColor(Color.parseColor("#FC4625"))
                    tv_auth_tips.text = "认证已过期"
                    tv_none_auth.text = "点我更新认证"
                }else{
                    tv_auth_tips.setTextColor(Color.parseColor("#73ffffff"))
                    tv_auth_tips.text = "暂无认证信息"
                    tv_none_auth.text = "点我去认证"
                }
            }

            tv_name_user.text = data.data.nickname
            tv_title_user.text = data.data.nickname
            if (data.data.gender == "1") {//男
                var draw = activity!!.resources.getDrawable(R.mipmap.suerhomepage_icon_man)
                draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight())
                tv_age_user.setCompoundDrawables(draw, null, null, null)
            } else {
                var draw = activity!!.resources.getDrawable(R.mipmap.suerhomepage_icon_lady)
                draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight())
                tv_age_user.setCompoundDrawables(draw, null, null, null)
            }
            tv_age_user.text = data.data.age

            tv_Id_user.text = "id：" + data.data.id
            if (data.data.signature != null && data.data.signature != "") {
                tv_explain_all_user.text = data.data.signature
                tv_explain_all_user.isEnabled = false
            } else {
                tv_explain_all_user.isEnabled = true
                tv_explain_all_user.text = "点击添加个人说明，让大家更了解你~"
            }

            if (data.data.tags != null && data.data.tags.size > 0) {
                tv_add_tags.visibility = View.GONE
                tag_user.visibility = View.VISIBLE

                var list = ArrayList<String>()
                var tags = ArrayList<TagsBean.DataBean>()

                data.data.tags.forEach {
                    list.add(it.description)
                    var bean = TagsBean.DataBean()
                    bean.description = it.description
                    bean.id = it.id
                    tags.add(bean)
                }
                if (data.data.tags.size < 6) {
                    list.add("+ 添加更多")
                }
                tag_user.setList(list)
                TagsUtils.setTagList(tags)
            }else{
                tv_add_tags.visibility = View.VISIBLE
                tag_user.visibility = View.GONE

            }
//
//            if (fragment!=null){
//                fragment!!.setRefresh()
//            }else {
                initFragment(data.data.id)
//            }

            tag_user.setOnItemClickListener { position, text ->
                if (text == "+ 添加更多") {
                    intentUtils.intentRegisterTags("编辑")
                }
            }

            Click.viewClick(tv_add_tags).subscribe {
                intentUtils.intentRegisterTags("编辑")
            }

            Click.viewClick(banner_user).subscribe {
                if (data.data.images != null && data.data.images.size > 0) {
                    intentUtils.intentUserCover(data.data.images[0])
                } else {
                    intentUtils.intentUserCover(data.data.avatar)
                }
            }

            Click.viewClick(iv_header_user).subscribe {
                intentUtils.intentUserAvatar(data.data.avatar)
            }

            Click.viewClick(tv_explain_all_user).subscribe {
                intentUtils.intentEditUserSign("")
            }

            Click.viewClick(tv_none_auth).subscribe {
                intentUtils.intentAuthCenter()
            }

            try {//不知道为啥
                Click.viewClick(layout_like).subscribe {
                    intentUtils.intentLikeCommunity()
                }
            }catch (e:Exception){

            }


            Click.viewClick(layout_fans_user).subscribe {
                intentUtils.intentMineFans(data.data.id, "我的粉丝")
            }

            Click.viewClick(layout_like_user).subscribe {
                intentUtils.intentMineFans(data.data.id, "我关注的")
            }

            Click.viewClick(iv_set).subscribe {
                dialog.show(childFragmentManager, "")
            }




            var userInfo= JMessageClient.getMyInfo()
            if (userInfo!=null) {
                userInfo.nickname = data.data.nickname
                JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, object : BasicCallback() {
                    override fun gotResult(p0: Int, p1: String?) {
                        LogUtils.a("极光昵称", p1)
                    }
                })
            }
            var user= UserBean.DataBeanX()
            user.data= UserBean.DataBeanX.DataBean()
            user.token=DBUtils.getUser().token
            user.data.accountState=DBUtils.getUser().accountState
            user.data.authState=data.data.authState.toInt()
            user.data.avatar=data.data.avatar
            user.data.nickname=data.data.nickname
            user.data.role=data.data.role.toInt()
            user.data.id=data.data.id
            user.data.signature=data.data.signature
            DBUtils.setUserDB(user)

            val rxPermissions = RxPermissions(activity!!)
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe { aBoolean ->
                if (aBoolean!!) {
                    var file = FileUtils.saveImageFile((iv_header_user.drawable as RoundedDrawable).toBitmap())
                    JMessageClient.updateUserAvatar(file, object : BasicCallback() {
                        override fun gotResult(p0: Int, p1: String?) {
                            Log.e("极光头像", p1)
                            if (file.exists()) {
                                deletePic(file.absolutePath)
//                            if (file.delete()){
//                                LogUtils.a("删除头像","成功")
//                            }else{
//                                LogUtils.a("删除头像","失败")
//                            }
                            }
                        }
                    })
                }
            }
        }else if (data.data.role=="2"){
            scroll.scrollTo(0,0)
            layout_user.visibility=View.GONE
            layout_business.visibility=View.VISIBLE
            if (data.data.images!=null&&data.data.images.size>0){
                var imagelist = ArrayList<ImageBannerInfo>()
                data.data.images.forEach {
                    imagelist.add(ImageBannerInfo(it, false, 0, "", "", ""))
                }
                BannerUtil.setBanner(banner, imagelist)
            }else{
                var imagelist = ArrayList<ImageBannerInfo>()
                imagelist.add(ImageBannerInfo(data.data.avatar, false, 0, "", "", ""))
                BannerUtil.setBanner(banner, imagelist)
            }

            ImageLoad.setUserHead(data.data.avatar,iv_header)

            if (data.data.likeNum < 1000) {
                tv_like_num.text = data.data.likeNum.toString()
            } else if (data.data.likeNum < 10000) {
                tv_like_num.text = BigDecimal(data.data.likeNum).divide(BigDecimal(1000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "K"
            } else {
                tv_like_num.text = BigDecimal(data.data.likeNum).divide(BigDecimal(10000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "W"
            }

            if (data.data.fansNum < 1000) {
                tv_fans_num.text = data.data.fansNum.toString()
            } else if (data.data.fansNum < 10000) {
                tv_fans_num.text = BigDecimal(data.data.fansNum).divide(BigDecimal(1000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "K"
            } else {
                tv_fans_num.text = BigDecimal(data.data.fansNum).divide(BigDecimal(10000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "W"
            }

            if (data.data.focusNum < 1000) {
                tv_follow_num.text = data.data.focusNum.toString()
            } else if (data.data.focusNum < 10000) {
                tv_follow_num.text = BigDecimal(data.data.focusNum).divide(BigDecimal(1000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "K"
            } else {
                tv_follow_num.text = BigDecimal(data.data.focusNum).divide(BigDecimal(10000)).setScale(1, BigDecimal.ROUND_DOWN).toString() + "W"
            }

            if (data.data.authState == "1") {
                layout_none_auth_business.visibility = View.GONE
                if (data.data.authType == "3") {
                    layout_business_auth.visibility = View.VISIBLE
                }
            } else {
                layout_business_auth.visibility = View.GONE
                layout_none_auth_business.visibility = View.VISIBLE
                if (data.data.authState == "3") {
                    tv_auth_tips_business.setTextColor(Color.parseColor("#FC4625"))
                    tv_auth_tips_business.text = "认证已过期"
                    tv_none_auth_business.text = "点我更新认证"
                }else{
                    tv_auth_tips_business.setTextColor(Color.parseColor("#73ffffff"))
                    tv_auth_tips_business.text = "暂无认证信息"
                    tv_none_auth_business.text = "点我去认证"
                }
            }

            tv_name.text = data.data.nickname
            tv_title.text = data.data.nickname

            tv_Id.text = "id：" + data.data.id
            if (data.data.signature != null && data.data.signature != "") {
                tv_explain_all.text = data.data.signature
                tv_explain_all.isEnabled = false
            } else {
                tv_explain_all.isEnabled = true
                tv_explain_all.text = ""
            }

            if (data.data.address!=null&&data.data.address!=""){
                tv_address.text=data.data.address
            }

            if (data.data.phone!=null&&data.data.phone.size>0){
                if (data.data.phone.size==1){
                    tv_phone.text=data.data.phone[0]
                }else{
                    tv_phone.text=data.data.phone[0]+" / " +data.data.phone[1]
                }
            }

            if (data.data.openTime!=null&&data.data.openTime!=""&&data.data.closeTime!=null&&data.data.closeTime!=""){
                tv_time.text=data.data.openTime+"~"+data.data.closeTime
            }

            if (data.data.perPersonConsume!=null&&data.data.perPersonConsume!=""){
                tv_money.visibility=View.VISIBLE
                tv_money_set.visibility=View.GONE
                tv_money.text=BigDecimal(data.data.perPersonConsume).setScale(0,BigDecimal.ROUND_DOWN).toString()+"RMB/人"
            }else{
                tv_money.visibility=View.GONE
                tv_money_set.visibility=View.VISIBLE
            }

//            if (fragment1!=null&&fragment2!=null&&fragment3!=null){
//                if (mCurrentFragment==fragment1){
//                    (fragment1!! as TopicFragment).setRefresh()
//                }else if (mCurrentFragment == fragment2) {
//                    (fragment2 as BusinessActivityFragment).setRefresh()
//                } else {
//                    (fragment3 as TopicFragment).setRefresh()
//                }
//            }else {
                initFragmentBusiness(data.data.id, data.data.locationId)
//            }

            Click.viewClick(tv_money_set).subscribe {
                intentUtils.intentBusinessMoney()
            }

            Click.viewClick(iv_set_business).subscribe {
                dialog.show(childFragmentManager, "")
            }

            Click.viewClick(iv_header).subscribe {
                intentUtils.intentBusinessAvatar(data.data.avatar)
            }

            Click.viewClick(tv_none_auth_business).subscribe {
                intentUtils.intentBusinessAuthCenter()
            }

            Click.viewClick(tv_explain_all).subscribe {
                intentUtils.intentBusinessSign("")
            }

            Click.viewClick(layout_like_business).subscribe {
                intentUtils.intentLikeCommunity()
            }

            Click.viewClick(layout_fans_business).subscribe {
                intentUtils.intentMineFans(data.data.id, "我的粉丝")
            }

            Click.viewClick(layout_focus_business).subscribe {
                intentUtils.intentMineFans(data.data.id, "我关注的")
            }

            Click.viewClick(iv_set).subscribe {
                dialog.show(childFragmentManager, "")
            }

            Click.viewClick(tv_content_choose1).subscribe {
                checkCommunity()
            }

            Click.viewClick(tv_content_choose2).subscribe {
                checkYe()
            }

            Click.viewClick(tv_content_choose3).subscribe {
                checkClock()
            }

            Click.viewClick(tv_choose2).subscribe {
                checkYe()
            }

            Click.viewClick(tv_choose1).subscribe {
                checkCommunity()
            }

            Click.viewClick(tv_choose3).subscribe {
                checkClock()
            }



            var userInfo= JMessageClient.getMyInfo()
            if (userInfo!=null) {
                userInfo.nickname = data.data.nickname
                JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, object : BasicCallback() {
                    override fun gotResult(p0: Int, p1: String?) {
                        LogUtils.a("极光昵称", p1)
                    }
                })
            }
            var user= BusinessBean.DataBeanX()
            user.data= BusinessBean.DataBeanX.DataBean()
            user.token=DBUtils.getBusiness().token
            user.data.accountState=DBUtils.getBusiness().accountState
            user.data.authState=data.data.authState.toInt()
            user.data.avatar=data.data.avatar
            user.data.nickname=data.data.nickname
            user.data.role=data.data.role.toInt()
            user.data.id=data.data.id
            user.data.signature=data.data.signature
            DBUtils.setBusinessDB(user)
            val rxPermissions = RxPermissions(activity!!)
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe { aBoolean ->
                if (aBoolean!!) {
                    var file = FileUtils.saveImageFile((iv_header.drawable as RoundedDrawable).toBitmap())
                    JMessageClient.updateUserAvatar(file, object : BasicCallback() {
                        override fun gotResult(p0: Int, p1: String?) {
                            Log.e("极光头像", p1)
                            if (file.exists()) {
                                deletePic(file.absolutePath)
//                            if (file.delete()){
//                                LogUtils.a("删除头像","成功")
//                            }else{
//                                LogUtils.a("删除头像","失败")
//                            }
                            }
                        }
                    })
                }
            }
        }

        (activity!! as MainActivity).setHead(data.data.avatar)
        user.setChange(false)
    }

    private fun deletePic(path:String){
        if(!TextUtils.isEmpty(path)){
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val contentResolver = activity!!.getContentResolver()//cutPic.this是一个上下文
            val url =  MediaStore.Images.Media.DATA + "=?"
            //删除图片
            contentResolver.delete(uri, url, arrayOf(path))
        }
    }

    private val presenter by lazy { MineUserPresenter(this, this, activity!!) }
    var fragment: TopicFragment? = null
    var fragment1: BaseFragment? = null
    var fragment2: BaseFragment? = null
    var fragment3:BaseFragment?=null
    var mCurrentFragment: BaseFragment? = null
    private lateinit var dialog: MineSetDialog

    private var role=""

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {

        dialog = MineSetDialog(this)
    }

    override fun setFragmentListener() {
        scroll.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0:NestedScrollView,scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {

                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
                var distanceScrollY = layout_top.height - layout_title.height+SystemUtils.dip2px(activity!!,20f)
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
//                    Log.e("测试","滑动到底部了")
                    if (mCurrentFragment!=null) {
                        if (mCurrentFragment == fragment1) {
                            (fragment1 as TopicFragment).setLoadMore()
                        } else if (mCurrentFragment == fragment2) {
                            (fragment2 as BusinessActivityFragment).setLoadMore()
                        } else {
                            (fragment3 as TopicFragment).setLoadMore()
                        }
                    }
                }
                //设置标题栏渐变
                var alpha=0f
                var h=SystemUtils.dip2px(activity!!,180f)
                if (scrollY>=h){
                    alpha=1f
                }else{
                    alpha=scrollY/h
                }

                layout_title.alpha=alpha
                layout_title.visibility=View.VISIBLE

            }

        })

        scroll_user.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0:NestedScrollView,scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {

                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
                var distanceScrollY = layout_top_user.height - layout_title_user.height+SystemUtils.dip2px(activity!!,20f)
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
                    if(fragment!=null) {
                        fragment!!.setLoadMore()
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
                var h=SystemUtils.dip2px(activity!!,100f)
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

    override fun layoutID(): Int = R.layout.fragment_mine_business

    //设置初始显示的Fragment
    private fun initFragment(id: String) {
        if (fragment!=null&&!user.getChange()){
            (fragment!! as TopicFragment).setRefresh()
        }else {
            fragment = TopicFragment("动态", id)
            (fragment as TopicFragment).setMine()
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(R.id.fl_container_user, fragment!!)
//            transaction.commitAllowingStateLoss()
            transaction.commitNowAllowingStateLoss()
        }
//        transaction.show(fragment!!)
    }

    //设置初始显示的Fragment
    private fun initFragmentBusiness(id:String,location:String) {
        fragment1 = TopicFragment("动态",id)
        (fragment1 as TopicFragment).setMine()
        fragment3 = TopicFragment("打卡",location)
        (fragment3 as TopicFragment).setMine()
        fragment2 = BusinessActivityFragment(id)
        mCurrentFragment = fragment1
        val transaction = childFragmentManager.beginTransaction()
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
        tv_content_choose3.setTextColor(Color.parseColor("#a6ffffff"))
        tv_content_choose3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)

        //悬浮导航栏选中效果
        tv_choose1.setTextColor(Color.parseColor("#FCC725"))
        tv_choose1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
        tv_choose2.setTextColor(Color.parseColor("#a6ffffff"))
        tv_choose2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        tv_choose3.setTextColor(Color.parseColor("#a6ffffff"))
        tv_choose3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
    }

    /**
     * 选中打卡
     */
    private fun checkClock() {
        switchContent(mCurrentFragment!!, fragment3!!)

        tv_content_choose3.setTextColor(Color.parseColor("#FCC725"))
        tv_content_choose3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
        tv_content_choose1.setTextColor(Color.parseColor("#a6ffffff"))
        tv_content_choose1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        tv_content_choose2.setTextColor(Color.parseColor("#a6ffffff"))
        tv_content_choose2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        //悬浮导航栏选中效果
        tv_choose3.setTextColor(Color.parseColor("#FCC725"))
        tv_choose3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
        tv_choose1.setTextColor(Color.parseColor("#a6ffffff"))
        tv_choose1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        tv_choose2.setTextColor(Color.parseColor("#a6ffffff"))
        tv_choose2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
    }

    /**
     * 选中夜计划
     */
    private fun checkYe() {
        switchContent(mCurrentFragment!!, fragment2!!)

        tv_content_choose2.setTextColor(Color.parseColor("#FCC725"))
        tv_content_choose2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
        tv_content_choose1.setTextColor(Color.parseColor("#a6ffffff"))
        tv_content_choose1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        tv_content_choose3.setTextColor(Color.parseColor("#a6ffffff"))
        tv_content_choose3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        //悬浮导航栏选中效果
        tv_choose2.setTextColor(Color.parseColor("#FCC725"))
        tv_choose2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
        tv_choose1.setTextColor(Color.parseColor("#a6ffffff"))
        tv_choose1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        tv_choose3.setTextColor(Color.parseColor("#a6ffffff"))
        tv_choose3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
    }

    /**
     * Fragment的切换
     */
    fun switchContent(fromFragment: BaseFragment, toFragment: BaseFragment) {
        if (fromFragment != toFragment) {
            val transaction = childFragmentManager.beginTransaction()
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            Log.e("测试", "测试修改")
//            if (user.getIsDelMine()) {
                if (fragment != null) {

                    fragment!!.setRefresh()
                } else if (mCurrentFragment != null) {

                    if (mCurrentFragment == fragment1) {
//                    (fragment1!! as TopicFragment).setDel()
                        (fragment1!! as TopicFragment).setRefresh()

                    } else if (mCurrentFragment == fragment2) {
//                    (fragment2!! as TopicFragment).setDel()
                        (fragment2!! as TopicFragment).setRefresh()

                    }
                }
//            }
            if (user.getChange()) {
                Log.e("测试", "测试")
//                user.setChange(false)
                var user = GreenDaoHelper.getDaoSessions().userDBDao
                var business = GreenDaoHelper.getDaoSessions().businessDBDao
                if (user != null || business != null) {
                    var info = user.loadAll()
                    var inf = business.loadAll()
                    if (info != null && info.size > 0) {
                        presenter.getUserInfo(UserInfoBody(info.get(0).token))
                    } else if (inf != null && inf.size > 0) {
                        presenter.getUserInfo(UserInfoBody(inf.get(0).token))
                    }
                } else {
                    intentUtils.intentLogin()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        if (user.getIsDelMine()) {
            if (fragment != null) {

                fragment!!.setRefresh()
            } else if (mCurrentFragment != null) {

                if (mCurrentFragment== fragment1) {
//                    (fragment1!! as TopicFragment).setDel()

                    (fragment1!! as TopicFragment).setRefresh()

                } else if (mCurrentFragment == fragment2) {
//                    (fragment2!! as TopicFragment).setDel()
                    (fragment2!! as TopicFragment).setRefresh()

                }
            }
//        }
        if (user.getChange()) {
//            user.setChange(false)
            Log.e("测试", "测试")
            var user = GreenDaoHelper.getDaoSessions().userDBDao
            var business = GreenDaoHelper.getDaoSessions().businessDBDao
            if (user != null || business != null) {
                var info = user.loadAll()
                var inf = business.loadAll()
                if (info != null && info.size > 0) {
                    presenter.getUserInfo(UserInfoBody(info.get(0).token))
                } else if (inf != null && inf.size > 0) {
                    presenter.getUserInfo(UserInfoBody(inf.get(0).token))
                }
            } else {
                intentUtils.intentLogin()
            }
        }
    }

    fun setAddCommunity(flag:Boolean){
        if (flag&&role=="1"){
            tv_add_community.visibility=View.VISIBLE
        }else{
            tv_add_community.visibility=View.GONE
        }
    }

    fun refresh(){
        if (user.getChange()) {
            Log.e("测试", "测试")
//                user.setChange(false)
            var user = GreenDaoHelper.getDaoSessions().userDBDao
            var business = GreenDaoHelper.getDaoSessions().businessDBDao
            if (user != null || business != null) {
                var info = user.loadAll()
                var inf = business.loadAll()
                if (info != null && info.size > 0) {
                    presenter.getUserInfo(UserInfoBody(info.get(0).token))
                } else if (inf != null && inf.size > 0) {
                    presenter.getUserInfo(UserInfoBody(inf.get(0).token))
                }
            } else {
                intentUtils.intentLogin()
            }
        }
    }



}
