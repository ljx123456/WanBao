package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.graphics.Color
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.fragment_mine_user.*
import retrofit2.http.Body
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean
import wanbao.yongchao.com.wanbao.ui.login.utils.TagsUtils
import wanbao.yongchao.com.wanbao.ui.mine.dialog.MineSetDialog
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.UserInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.UserInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.MineUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineUserView
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils
import java.math.BigDecimal

class MineUserFragment : BaseFragment(),MineUserView,MineSetDialog.SetDialog{
    override fun setMoreSet() {
        intentUtils.intentSet()
    }

    override fun setData() {
        intentUtils.intentUserInfo()
    }

    override fun setCollect() {
        intentUtils.intentMineCollect()
    }

    override fun setWant() {
        intentUtils.intentMineWant()
    }

    override fun getUserInfoError() {

    }

    override fun getUserInfoRequest(data: UserInfoBean) {

        if (data.data.images!=null&&data.data.images.size>0){
            ImageLoad.setImage(data.data.images[0],banner_user)
        }else{
            ImageLoad.setImage(data.data.avatar,banner_user)
        }
        if (data.data.avatar!=null&&data.data.avatar!=""){
            ImageLoad.setUserHead(data.data.avatar,iv_header_user)
        }
        if (data.data.likeNum<1000){
            tv_like_num_user.text=data.data.likeNum.toString()
        }else if (data.data.likeNum<10000){
            tv_like_num_user.text=BigDecimal(data.data.likeNum).divide(BigDecimal(1000)).setScale(1,BigDecimal.ROUND_DOWN).toString()+"K"
        }else{
            tv_like_num_user.text=BigDecimal(data.data.likeNum).divide(BigDecimal(10000)).setScale(1,BigDecimal.ROUND_DOWN).toString()+"W"
        }

        if (data.data.fansNum<1000){
            tv_fans_num_user.text=data.data.fansNum.toString()
        }else if (data.data.fansNum<10000){
            tv_fans_num_user.text=BigDecimal(data.data.fansNum).divide(BigDecimal(1000)).setScale(1,BigDecimal.ROUND_DOWN).toString()+"K"
        }else{
            tv_fans_num_user.text=BigDecimal(data.data.fansNum).divide(BigDecimal(10000)).setScale(1,BigDecimal.ROUND_DOWN).toString()+"W"
        }

        if (data.data.focusNum<1000){
            tv_follow_num_user.text=data.data.focusNum.toString()
        }else if (data.data.focusNum<10000){
            tv_follow_num_user.text=BigDecimal(data.data.focusNum).divide(BigDecimal(1000)).setScale(1,BigDecimal.ROUND_DOWN).toString()+"K"
        }else{
            tv_follow_num_user.text=BigDecimal(data.data.focusNum).divide(BigDecimal(10000)).setScale(1,BigDecimal.ROUND_DOWN).toString()+"W"
        }

        if (data.data.authState=="1"){
            layout_none_auth.visibility=View.GONE
            if (data.data.authType=="1"){
                layout_realname_user.visibility=View.VISIBLE
                layout_blogger_user.visibility=View.GONE
            }else if (data.data.authType=="2"){
                layout_realname_user.visibility=View.GONE
                layout_blogger_user.visibility=View.VISIBLE
                tv_blogger_user.text=data.data.authTypeName
            }
        }else{
            layout_realname_user.visibility=View.GONE
            layout_blogger_user.visibility=View.GONE
            layout_none_auth.visibility=View.VISIBLE
            if (data.data.authState=="3"){
                tv_auth_tips.setTextColor(Color.parseColor("#FC4625"))
                tv_auth_tips.text="认证已过期"
                tv_none_auth.text="点我更新认证"
            }
        }

        tv_name_user.text=data.data.nickname
        tv_title_user.text=data.data.nickname
        if (data.data.gender=="1"){//男
            var draw=activity!!.resources.getDrawable(R.mipmap.suerhomepage_icon_man)
            draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight())
            tv_age_user.setCompoundDrawables(draw,null,null,null)
        }else{
            var draw=activity!!.resources.getDrawable(R.mipmap.suerhomepage_icon_lady)
            draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight())
            tv_age_user.setCompoundDrawables(draw,null,null,null)
        }
        tv_age_user.text=data.data.age

        tv_Id_user.text="id："+data.data.id
        if (data.data.signature!=null&&data.data.signature!=""){
            tv_explain_all_user.text=data.data.signature
            tv_explain_all_user.isEnabled=false
        }else{
            tv_explain_all_user.isEnabled=true
            tv_explain_all_user.text="点击添加个人说明，让大家更了解你~"
        }

        if (data.data.tags!=null&&data.data.tags.size>0){
            tv_add_tags.visibility=View.GONE
            tag_user.visibility=View.VISIBLE

            var list=ArrayList<String>()
            var tags=ArrayList<TagsBean.DataBean>()

            data.data.tags.forEach {
                list.add(it.description)
                var bean=TagsBean.DataBean()
                bean.description=it.description
                bean.id=it.id
                tags.add(bean)
            }
            if (data.data.tags.size<6){
                list.add("+ 添加更多")
            }
            tag_user.setList(list)
            TagsUtils.setTagList(tags)
        }

        initFragment(data.data.id)

        tag_user.setOnItemClickListener { position, text ->
            if (text=="+ 添加更多"){
                intentUtils.intentRegisterTags("编辑")
            }
        }

        Click.viewClick(banner_user).subscribe {
            if (data.data.images!=null&&data.data.images.size>0) {
                intentUtils.intentUserCover(data.data.images[0])
            }else{
                intentUtils.intentUserCover(data.data.avatar)
            }
        }

        Click.viewClick(iv_header_user).subscribe {  }

        Click.viewClick(tv_explain_all_user).subscribe {
            intentUtils.intentEditUserSign(data.data.signature)
        }

        Click.viewClick(tv_none_auth).subscribe {
            intentUtils.intentAuthCenter()
        }

        Click.viewClick(layout_like).subscribe {
            intentUtils.intentLikeCommunity()
        }

        Click.viewClick(layout_fans_user).subscribe {
            intentUtils.intentMineFans(data.data.id,"我的粉丝")
        }

        Click.viewClick(layout_like_user).subscribe {
            intentUtils.intentMineFans(data.data.id,"我关注的")
        }

        Click.viewClick(iv_set).subscribe {
            dialog.show(childFragmentManager,"")
        }

    }

    private val presenter by lazy { MineUserPresenter(this,this,activity!!)}
    var fragment: TopicFragment? = null
    private lateinit var dialog:MineSetDialog

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {

        dialog= MineSetDialog(this)
    }

    override fun setFragmentListener() {
        scroll_user.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView, scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {


                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
                var distanceScrollY = layout_top_user.height - layout_title_user.height+ SystemUtils.dip2px(activity!!,20f)
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
                var h= SystemUtils.dip2px(activity!!,100f)
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

    override fun layoutID(): Int = R.layout.fragment_mine_user

    //设置初始显示的Fragment
    private fun initFragment(id:String) {
        fragment = TopicFragment("动态",id)
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container_user, fragment!!)
        transaction.commitAllowingStateLoss()
//        transaction.show(fragment!!)
    }

    override fun onResume() {
        super.onResume()
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        if (user != null) {
            var info = user.loadAll()
            if (info != null && info.size > 0) {
                presenter.getUserInfo(UserInfoBody(info.get(0).token))
            }else{
                intentUtils.intentLogin()
            }
        }else{
            intentUtils.intentLogin()
        }
    }

}