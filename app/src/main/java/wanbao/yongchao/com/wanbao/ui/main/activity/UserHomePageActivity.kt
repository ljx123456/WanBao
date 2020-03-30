package wanbao.yongchao.com.wanbao.ui.main.activity

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_user_homepage.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo
import wanbao.yongchao.com.wanbao.ui.main.adapter.FragmentAdapter
import wanbao.yongchao.com.wanbao.ui.main.fragment.TopicFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.UserHomePageBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UserHomePageBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UserHomePageForNameBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.UserHomePagePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.UserHomePageView
import wanbao.yongchao.com.wanbao.ui.main.utils.banner.BannerUtil
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllSpan
import java.math.BigDecimal

//import wanbao.yongchao.com.wanbao.ui.main.fragment.CommunityHotFragment

class UserHomePageActivity:BaseActivity(),UserHomePageView{
    override fun getFocusRequest() {

    }

    override fun getUnFocusRequest() {

    }

    override fun getUserHomePageRequest(data: UserHomePageBean) {
        ImageLoad.setImage(data.data.images[0],banner_user)
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
            var draw=resources.getDrawable(R.mipmap.suerhomepage_icon_man)
            draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight())
            tv_age_user.setCompoundDrawables(draw,null,null,null)
        }else{
            var draw=resources.getDrawable(R.mipmap.suerhomepage_icon_lady)
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

        initFragment(data.data.id.toString())
    }

    override fun getUserHomePageError() {

    }

    private val presenter by lazy { UserHomePagePresenter(this,this,this) }
    var fragment: TopicFragment? = null

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_user_homepage

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

        if (intent.getStringExtra("id")!=null&&intent.getStringExtra("id")!=""){
            presenter.getUserHomePage(UserHomePageBody(intent.getStringExtra("id")))
        }else if (intent.getStringExtra("name")!=null&&intent.getStringExtra("name")!=""){
            presenter.getUserHomePageForName(UserHomePageForNameBody(intent.getStringExtra("name")))
        }


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
                var distanceScrollY = layout_top_user.height - layout_title_user.height+SystemUtils.dip2px(this@UserHomePageActivity,20f)
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
                var h=SystemUtils.dip2px(this@UserHomePageActivity,100f)
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

        Click.viewClick(iv_back_user).subscribe {
            finish()
        }

        Click.viewClick(iv_title_back_user).subscribe {
            finish()
        }

//        Click.viewClick(layout_fans_user).subscribe {
//            intentUtils.intentFans("粉丝")
//        }
//
//        Click.viewClick(layout_like_user).subscribe {
//            intentUtils.intentFans("关注")
//        }



    }


    //设置初始显示的Fragment
    private fun initFragment(id:String) {
        fragment = TopicFragment("动态",id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container_user, fragment!!)
        transaction.commitAllowingStateLoss()
//        transaction.show(fragment!!)
    }

}