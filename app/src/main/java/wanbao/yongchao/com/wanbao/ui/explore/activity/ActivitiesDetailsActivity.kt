package wanbao.yongchao.com.wanbao.ui.explore.activity

import android.graphics.Color
import android.support.v4.widget.NestedScrollView
import android.view.View
import kotlinx.android.synthetic.main.activity_activities_details.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ActivitiesDetailsBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ActivitiesDetailsBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.ActivitiesDetailsPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ActivitiesDetailsView
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo
import wanbao.yongchao.com.wanbao.ui.main.dialog.CommentInputDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.LoginDialog
import wanbao.yongchao.com.wanbao.ui.main.fragment.CommunityCommentFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityCommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.LikeBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.main.utils.explore_banner.BannerDetailsUtil
import wanbao.yongchao.com.wanbao.utils.dialog.ShareDialog
import wanbao.yongchao.com.wanbao.utils.http.BaseUrl
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.share.ShareUtil
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ActivitiesDetailsActivity:BaseActivity(),ShareDialog.Share, CommentInputDialog.Send,ActivitiesDetailsView{
    override fun getFocusRequest() {

//                Toast.Tips("已标记想去")
        isFollow=true
        tv_follow.setBackgroundResource(R.drawable.tv_followed_bg)
        tv_follow.setText("已关注")
        tv_follow.setTextColor(Color.parseColor("#FCC725"))
    }

    override fun getUnFocusRequest() {
        isFollow=false
        tv_follow.setBackgroundResource(R.drawable.tv_follow_bg)
        tv_follow.setText("关注")
        tv_follow.setTextColor(Color.parseColor("#ff000000"))
    }

    override fun getActivitiesDetailsRequest(data: ActivitiesDetailsBean) {
        var imagelist = ArrayList<ImageBannerInfo>()
        data.data.data.videoSet.forEach {
            imagelist.add(ImageBannerInfo(it,false,0,"","",""))
        }
        BannerDetailsUtil.setBanner(banner, imagelist)
        if (data.data.businessAvatar!=null&&data.data.businessAvatar!=""){
            ImageLoad.setUserHead(data.data.businessAvatar,iv_header)
        }else{
            ImageLoad.setResourceImage(R.mipmap.ic_app,iv_header)
        }
        if (data.data.businessName!=null&&data.data.businessName!=""){
            tv_name.text=data.data.businessName
        }else{
            tv_name.text="晚豹官方"
        }
        tv_time.text=data.data.releaseTime
        if (data.data.isFollow!=null&&data.data.isFollow!=-1){
            tv_follow.visibility=View.VISIBLE
            if (data.data.isFollow==0){
                isFollow=false
                tv_follow.setBackgroundResource(R.drawable.tv_follow_bg)
                tv_follow.setTextColor(Color.parseColor("#000000"))
                tv_follow.text="关注"
            }else{
                isFollow=true
                tv_follow.setBackgroundResource(R.drawable.tv_followed_bg)
                tv_follow.setTextColor(Color.parseColor("#FCC725"))
                tv_follow.text="已关注"
            }
        }else{
            tv_follow.visibility=View.GONE
        }

        tv_type.text=data.data.businessType
        tv_title.text=data.data.data.title
        tv_content.text=data.data.data.content

        tv_city.text=data.data.data.cityInfo
        tv_address.text=data.data.data.address
        tv_activities_time.text="活动时间："+data.data.data.startTime.substring(0,data.data.data.startTime.length-3).replace("-","/").replace(" ","/")+data.data.data.endTime.substring(0,data.data.data.endTime.length-3).replace("-","/").replace(" ","/")
        if (data.data.data.phones!=null&&data.data.data.phones.size>0){
            if (data.data.data.phones.size==1){
                tv_phone.text="联系电话："+data.data.data.phones[0]
            }else{
                tv_phone.text="联系电话："+data.data.data.phones[0]+" / "+data.data.data.phones[1]
            }
        }

        if (data.data.data.wantAvatars!=null&&data.data.data.wantAvatars.size>0){
            layout_user.visibility=View.VISIBLE
            when(data.data.data.wantAvatars.size){
                1->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.GONE
                    iv_user3.visibility=View.GONE
                    iv_user4.visibility=View.GONE
                    iv_user5.visibility=View.GONE
                    ImageLoad.setUserHead(data.data.data.wantAvatars[0],iv_user1)
                }
                2->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.VISIBLE
                    iv_user3.visibility=View.GONE
                    iv_user4.visibility=View.GONE
                    iv_user5.visibility=View.GONE
                    ImageLoad.setUserHead(data.data.data.wantAvatars[0],iv_user1)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[1],iv_user2)
                }
                3->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.VISIBLE
                    iv_user3.visibility=View.VISIBLE
                    iv_user4.visibility=View.GONE
                    iv_user5.visibility=View.GONE
                    ImageLoad.setUserHead(data.data.data.wantAvatars[0],iv_user1)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[1],iv_user2)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[2],iv_user3)
                }
                4->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.VISIBLE
                    iv_user3.visibility=View.VISIBLE
                    iv_user4.visibility=View.VISIBLE
                    iv_user5.visibility=View.GONE
                    ImageLoad.setUserHead(data.data.data.wantAvatars[0],iv_user1)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[1],iv_user2)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[2],iv_user3)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[3],iv_user4)
                }
                5->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.VISIBLE
                    iv_user3.visibility=View.VISIBLE
                    iv_user4.visibility=View.VISIBLE
                    iv_user5.visibility=View.VISIBLE
                    ImageLoad.setUserHead(data.data.data.wantAvatars[0],iv_user1)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[1],iv_user2)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[2],iv_user3)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[3],iv_user4)
                    ImageLoad.setUserHead(data.data.data.wantAvatars[4],iv_user5)

                }
            }
        }else{
            layout_user.visibility=View.GONE
            iv_user1.visibility=View.GONE
            iv_user2.visibility=View.GONE
            iv_user3.visibility=View.GONE
            iv_user4.visibility=View.GONE
            iv_user5.visibility=View.GONE
        }

        if (data.data.data.wantNum!=null&&data.data.data.wantNum>0){
            tv_num.text=data.data.data.wantNum.toString()+"人想去"
        }else{
            tv_num.text="暂时无人"
        }

        if (data.data.data.isWant==1){
            isWant=true
            tv_want.setBackgroundResource(R.drawable.tv_followed_bg)
            tv_want.setTextColor(Color.parseColor("#FCC725"))
            tv_want.text="已想去"
        }else{
            isWant=false
            tv_want.setBackgroundResource(R.drawable.tv_follow_bg)
            tv_want.setTextColor(Color.parseColor("#000000"))
            tv_want.text="想去"
        }
        id=data.data.data.id

        if (y==0) {
            initFragment(data.data.data.id.toString())
        }

        Click.viewClick(tv_follow).subscribe {
            if (isLogin) {
                if (isFollow) {
                    presenter.getUnFocus(UnFocusBody(data.data.businessId.toString(), "1"))
                } else {
                    presenter.getFocus(FocusBody(data.data.businessId.toString(), "1", data.data.data.id.toString(), "2"))
                }
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }

        }

        Click.viewClick(tv_want).subscribe {
            if (isLogin) {
                presenter.getWant(LikeBody(data.data.data.id, 2, 5))
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }

        Click.viewClick(layout_bottom).subscribe {
            if (isLogin) {
                if (id != -1) {
                    editDialog.show(supportFragmentManager, "")
                    editDialog.setHint("评论：")
                }
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }

        Click.viewClick(layout_want).subscribe {
            if (data.data.data.wantNum!=null&&data.data.data.wantNum>0) {
                intentUtils.intentWantActivity(data.data.data.id.toString(),data.data.data.wantNum.toString())
            }
        }

        scroll.scrollTo(0,y)
    }

    override fun getActivitiesDetailsError() {

    }

    override fun getWantGoRequest() {
        user.setIsWant(true)
        user.setWantId(intent.getIntExtra("id",0).toString())
        presenter.getActivitiesDetails(ActivitiesDetailsBody(intent.getIntExtra("id",0)))
        if (isWant){
            Toast.Tips("已取消标记")
            isWant=false
            user.setIsAddWant(1)
//            tv_want.setBackgroundResource(R.drawable.tv_follow_bg)
//            tv_want.setTextColor(Color.parseColor("#000000"))
//            tv_want.text="想去"
        }else{
            Toast.Tips("已标记想去")
            isWant=true
            user.setIsAddWant(0)
//            tv_want.setBackgroundResource(R.drawable.tv_followed_bg)
//            tv_want.setTextColor(Color.parseColor("#FCC725"))
//            tv_want.text="已想去"
        }
    }

    override fun send(str: String) {
        if (fragment!=null)
            fragment!!.setComment(str,id,2)
    }

    private lateinit var shareDialog: ShareDialog
    private var fragment: CommunityCommentFragment?=null
    private lateinit var editDialog:CommentInputDialog
    private val presenter by lazy { ActivitiesDetailsPresenter(this,this,this) }
    private var isFollow=false
    private var isWant=false
    private var id=-1

    override fun setShareWX() {
        ShareUtil.shareWxCircle(this,"这有一条来自晚豹的热门活动","这有一条来自晚豹的热门活动",BaseUrl.HOST_URL+"share/page?id="+intent.getIntExtra("id",0).toString()+"&type=4")
    }

    override fun setSharePYQ() {
//        Toast.Tips("分享到朋友圈")
        ShareUtil.shareWx(this,"这有一条来自晚豹的热门活动","这有一条来自晚豹的热门活动",BaseUrl.HOST_URL+"share/page?id="+intent.getIntExtra("id",0).toString()+"&type=4")
    }

    override fun setShareQQ() {
//        Toast.Tips("分享到QQ")
        ShareUtil.QQShare(this,"晚豹App","这有一条来自晚豹的热门活动",BaseUrl.HOST_URL+"share/page?id="+intent.getIntExtra("id",0).toString()+"&type=4")
    }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_activities_details

    override fun setActivityTitle() {

    }

    private var isLogin=false
    private var y=0

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

        presenter.getActivitiesDetails(ActivitiesDetailsBody(intent.getIntExtra("id",0)))

        shareDialog= ShareDialog(this)
        editDialog= CommentInputDialog(this)


        scroll.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView, scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {

                y=scrollY
                if (scrollY == 0) {
                    //顶部
//                    manage.setmCanVerticalScroll(false)
                    iv_back.visibility= View.VISIBLE
                    iv_share.visibility= View.VISIBLE
                }else{
                    iv_back.visibility= View.GONE
                    iv_share.visibility= View.GONE
                }
                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
                var distanceScrollY = layout_top.height
                if (scrollY >= distanceScrollY) {
                    layout_choose.visibility = View.VISIBLE
//                    ll_tab.visibility = View.INVISIBLE
//                    title_divider.visibility = View.VISIBLE
                } else {
                    layout_choose.visibility = View.INVISIBLE
//                    ll_tab.visibility = View.VISIBLE
//                    title_divider.visibility = View.GONE
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
                var  contentView = scroll.getChildAt(0)
                if (contentView != null && contentView.getMeasuredHeight() == (scroll.getScrollY() + scroll.getHeight())) {
                    if (fragment!=null)
                        fragment!!.setMore()
                }

                var alpha=0f
                var h= SystemUtils.dip2px(this@ActivitiesDetailsActivity,230f)
                if (scrollY>=h){
                    alpha=1f
//                    manage.setmCanVerticalScroll(true)
                }else{
                    alpha=scrollY/h
//                    manage.setmCanVerticalScroll(false)
                }

                layout_title.alpha=alpha
                layout_title.visibility= View.VISIBLE
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

        Click.viewClick(iv_share).subscribe {
            shareDialog.show(supportFragmentManager,"")
        }

        Click.viewClick(iv_title_share).subscribe {
            shareDialog.show(supportFragmentManager,"")
        }



    }

    //设置初始显示的Fragment
    private fun initFragment(id:String) {
        fragment = CommunityCommentFragment(id,2)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container, fragment!!)
        transaction.commitAllowingStateLoss()

        if (intent.getSerializableExtra("bean")!=null){
            try {
                var bean: CommunityCommentBean.DataBean.CommentsBean= intent.getSerializableExtra("bean") as CommunityCommentBean.DataBean.CommentsBean
                (fragment!! as CommunityCommentFragment).setTop(bean)
            }catch (e:Exception){}

        }
    }
}