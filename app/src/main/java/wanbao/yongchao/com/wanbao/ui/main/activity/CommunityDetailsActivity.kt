package wanbao.yongchao.com.wanbao.ui.main.activity

import android.app.Activity
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.text.method.LinkMovementMethod
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_community_details.*
import kotlinx.android.synthetic.main.layout_community_details_item.*
import org.greenrobot.eventbus.EventBus
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.image.ImageInfo
import wanbao.yongchao.com.wanbao.ui.main.adapter.FragmentAdapter
import wanbao.yongchao.com.wanbao.ui.main.dialog.CommentInputDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.CommunityReportDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.LoginDialog
import wanbao.yongchao.com.wanbao.ui.main.fragment.CommunityCommentFragment
import wanbao.yongchao.com.wanbao.ui.main.fragment.CommunityLikeFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityCommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDetailsBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.CommunityDetailsPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunityDetailsView
import wanbao.yongchao.com.wanbao.ui.main.utils.dialog.MoreDialog
import wanbao.yongchao.com.wanbao.utils.dialog.ShareDialog
import wanbao.yongchao.com.wanbao.utils.http.BaseUrl
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.share.ShareUtil
import wanbao.yongchao.com.wanbao.utils.utils.AtColorUtil
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllSpan
import kotlin.Exception

class CommunityDetailsActivity :BaseActivity(),CommunityDetailsView,ShareDialog.Share,MoreDialog.More,CommentInputDialog.Send{
    override fun getCommunityDelRequest() {
        user.setIsDelMine(true)
        finish()
    }

    override fun getFocusRequest() {
        isFocus=false
        Toast.Tips("已关注")
        item_userFollow.setBackgroundResource(R.drawable.tv_followed_bg)
        item_userFollow.setText("已关注")
        item_userFollow.setTextColor(Color.parseColor("#FCC725"))
    }

    override fun getUnFocusRequest() {
        isFocus=true
        Toast.Tips("已取消")
        item_userFollow.setBackgroundResource(R.drawable.tv_follow_bg)
        item_userFollow.setText("关注")
        item_userFollow.setTextColor(Color.parseColor("#ff000000"))
    }

    override fun getLikeRequest() {
        if (isLike){
            isLike=false
            item_userLike.setImageResource(R.mipmap.homepage_button_list_like_nor)
            EventBus.getDefault().post("2")
            if (tv_like_num.text!=null&&!tv_like_num.text.toString().isNullOrEmpty()&&tv_like_num.text.toString().toInt()>1){
                tv_like_num.text=(tv_like_num.text.toString().toInt()-1).toString()
                tv_title_like_num.text=tv_like_num.text
            }else{
                tv_like_num.text=""
                tv_title_like_num.text=tv_like_num.text
                tv_like_num.visibility=View.GONE
                tv_title_like_num.visibility=View.GONE
            }
//            if (fragment2!=null){
//                (fragment2 as CommunityLikeFragment).setAdd()
//            }
        }else{
            isLike=true
            item_userLike.setImageResource(R.mipmap.homepage_button_list_like_pre)
            EventBus.getDefault().post("1")
            tv_like_num.visibility=View.VISIBLE
            tv_title_like_num.visibility=View.VISIBLE
            if (tv_like_num.text!=null&&!tv_like_num.text.toString().isNullOrEmpty()){
                tv_like_num.text=(tv_like_num.text.toString().toInt()+1).toString()
                tv_title_like_num.text=tv_like_num.text
            }else{
                tv_like_num.text="1"
                tv_title_like_num.text=tv_like_num.text
            }
//            if (fragment2!=null){
//                (fragment2 as CommunityLikeFragment).setAdd()
//            }
        }
    }

    override fun getCollectRequest() {
        if (isCollect){
            isCollect=false
            Toast.Tips("已取消收藏")
        }else{
            isCollect=true
            Toast.Tips("已收藏")
        }
//        moreDialog!!.setCollect(isCollect)
    }

    override fun getCommunityDetailsRequest(data: CommunityDetailsBean) {

        community_id=data.data.id
        ImageLoad.setUserHead(data.data.user.avatar,item_userHeader)
        item_userName.text=data.data.user.nickname
        item_userTime.text=data.data.releaseTime
        if (data.data.topicName!=null&&data.data.topicName!=""){
            item_userTopic.visibility=View.VISIBLE
            item_userTopic.text="#"+data.data.topicName+"#"
        }else{
            item_userTopic.visibility=View.GONE
        }

        if (data.data.user.authType!=null&&data.data.user.authType!=""){
            iv_item_community_auth.visibility=View.VISIBLE
            if (data.data.user.authType=="1"){//实名认证
                iv_item_community_auth.setImageResource(R.mipmap.homepage_icon_authentication_realname)
            }else if (data.data.user.authType=="2"){//行业大V
                iv_item_community_auth.setImageResource(R.mipmap.homepage_icon_authentication_blogger)
            }else if (data.data.user.authType=="3"){//商家
                iv_item_community_auth.setImageResource(R.mipmap.homepage_icon_authentication_shop)
            }
        }else{
            iv_item_community_auth.visibility=View.GONE
        }



        if (data.data.isLike==0){
            isLike=false
            item_userLike.setImageResource(R.mipmap.homepage_button_list_like_nor)
        }else{
            isLike=true
            item_userLike.setImageResource(R.mipmap.homepage_button_list_like_pre)
        }

        if (data.data.content!=null&&data.data.content!=""){
            item_userContent.visibility=View.VISIBLE
//            item_userContent.text=data.data.content
            item_userContent.setMovementMethod(LinkMovementMethod.getInstance())
            item_userContent.setText(AtColorUtil.getShowAllText(data.data.content,object : ShowAllSpan.OnAllSpanClickListener{
                override fun onClickAt(widget: View?, name: String) {
//                    Toast.Tips(name!!)
                    intentUtils.intentUserHomePageForName(name)
                }

                override fun onClick(widget: View?) {

                }
            }))
        }else{
            item_userContent.visibility=View.GONE
        }

        if (data.data.images!=null&&data.data.images.size>0){
            item_userVideo_layout.visibility=View.GONE
            if (data.data.images.size>1){
                item_userPhoto_nine.visibility=View.VISIBLE
                item_userPhoto_one.visibility=View.GONE
                item_userPhoto_nine.setUrlList(data.data.images)
            }else if (data.data.images.size==1){
                item_userPhoto_nine.visibility=View.GONE
                item_userPhoto_one.visibility=View.VISIBLE
                ImageLoad.setImage(data.data.images[0],item_userPhoto_one)
//                ImageLoad.setImageHeight(bean.photos[0],item_userPhoto_one)
                Click.viewClick(item_userPhoto_one).subscribe {
                    var images = ArrayList<ImageInfo>()
                    images.add(ImageInfo(data.data.images[0],false,0))
                    intentUtils.intentImage(false,images,0)
                }
            }else{
                item_userPhoto_nine.visibility=View.GONE
                item_userPhoto_one.visibility=View.GONE
            }
        }else if (data.data.video!=null&&data.data.video!=""){
            item_userVideo_layout.visibility=View.VISIBLE
            ImageLoad.setImage(data.data.video+"?vframe/jpg/offset/1",item_userVideo)
        }else{
            item_userPhoto_nine.visibility=View.GONE
            item_userPhoto_one.visibility=View.GONE
            item_userVideo_layout.visibility=View.GONE
        }

        if (data.data.location!=null&&data.data.location!=""){
            item_userAddress.visibility=View.VISIBLE
            item_userAddress.text=data.data.location
        }else{
            item_userAddress.visibility=View.GONE
        }

        if (data.data.commentNum!=null&&data.data.commentNum>0){
            tv_comment_num.visibility=View.VISIBLE
            tv_comment_num.text=data.data.commentNum.toString()
            tv_title_comment_num.visibility=View.VISIBLE
            tv_title_comment_num.text=data.data.commentNum.toString()

        }else{
            tv_comment_num.visibility=View.GONE
            tv_title_comment_num.visibility=View.GONE
        }

        if (data.data.likeNum!=null&&data.data.likeNum>0){
            tv_like_num.visibility=View.VISIBLE
            tv_like_num.text=data.data.likeNum.toString()
            tv_title_like_num.visibility=View.VISIBLE
            tv_title_like_num.text=data.data.likeNum.toString()

        }else{
            tv_like_num.visibility=View.GONE
            tv_title_like_num.visibility=View.GONE
        }

        initFragment(data.data.id.toString())


        try {
            var user= DBUtils.getUser()
            id=user.userId
        }catch (e: Exception){
            try {
                var user= DBUtils.getBusiness()
                id=user.businessId
            }catch (e: Exception){}
        }

//        if (id==data.data.user.id.toString()) {
        isCollect=data.data.isCollect==1
        user_id=data.data.user.id
        isFocus=data.data.isFocus==0
//        }else{
//            moreDialog = MoreDialog(this,false,data.data.isCollect==1)
//        }

        dialog= CommunityReportDialog(data.data.id.toString())

        if (id!=user_id.toString()) {
            item_userFollow.visibility=View.VISIBLE
            if (data.data.isFocus == 0) {
                item_userFollow.setBackgroundResource(R.drawable.tv_follow_bg)
                item_userFollow.setText("关注")
                item_userFollow.setTextColor(Color.parseColor("#ff000000"))
            } else {
                item_userFollow.setBackgroundResource(R.drawable.tv_followed_bg)
                item_userFollow.setText("已关注")
                item_userFollow.setTextColor(Color.parseColor("#FCC725"))
            }
        }else{
            item_userFollow.visibility=View.GONE
        }
        Click.viewClick(item_userFollow).subscribe {
            if (isLogin) {
                if (isFocus) {
                    presenter.getFocus(FocusBody(user_id.toString(), "1", community_id.toString(), "1"))
                } else {
                    presenter.getUnFocus(UnFocusBody(user_id.toString(), "1"))
                }
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }


        Click.viewClick(item_userLike).subscribe {
            if (isLogin) {
                presenter.getLike(LikeBody(community_id, 1, 2))
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }


        Click.viewClick(item_userHeader).subscribe {
            intentUtils.intentBusinessHomePage(user_id.toString())
        }

        Click.viewClick(item_userName).subscribe {
            intentUtils.intentBusinessHomePage(user_id.toString())
        }

        Click.viewClick(item_userTopic).subscribe {
            intentUtils.intentTopicDetails(data.data.topicId.toString(),data.data.topicName)
        }

        Click.viewClick(item_userVideo_play).subscribe {
            intentUtils.intentVideo(data.data.video)
        }

        Click.viewClick(item_userAddress).subscribe {
            if (data.data.locationType==3){//打卡类型：3 商家，2 定位，1 探索-城市坐标
                intentUtils.intentBusinessHomePage(data.data.objectId.toString())
            }else if (data.data.locationType==2){
                intentUtils.intentAddressHomePage(data.data.objectId.toString())
            }else if (data.data.locationType==1){
                intentUtils.intentLandMarkDetails(data.data.objectId.toString())
            }
        }
    }

    override fun getCommunityDetailsError() {

    }

    override fun send(str: String) {
        (fragment1!! as CommunityCommentFragment).setComment(str,community_id,1)
    }


    override fun setMoreShareWX() {
//        Toast.Tips("分享到微信")
        ShareUtil.shareWxCircle(this,"来自晚豹APP的一条热门动态","来自晚豹APP的一条热门动态", BaseUrl.HOST_URL+"share/page?id="+community_id+"&type=3")
    }

    override fun setMoreSharePYQ() {
//        Toast.Tips("分享到朋友圈")
        ShareUtil.shareWx(this,"来自晚豹APP的一条热门动态","来自晚豹APP的一条热门动态",BaseUrl.HOST_URL+"share/page?id="+community_id+"&type=3")
    }

    override fun setMoreShareQQ() {
//        Toast.Tips("分享到QQ")
        ShareUtil.QQShare(this,"晚豹App","来自晚豹APP的一条热门动态",BaseUrl.HOST_URL+"share/page?id="+community_id+"&type=3")
    }

    override fun setMoreDel() {
        if (isLogin) {
            Log.e("测试","删除")
            presenter.getCommunityDel(CommunityDelBody(community_id.toString()))
        }else{
            var dialog= LoginDialog()
            dialog.show(supportFragmentManager,"")
        }
    }

    override fun setMoreReport() {
        if (isLogin) {
            dialog.show(supportFragmentManager, "")
        }else{
            var dialog= LoginDialog()
            dialog.show(supportFragmentManager,"")
        }
    }

    override fun setMoreCollect() {
        if (isLogin) {
            presenter.getCollect(LikeBody(community_id, 1, 1))
        }else{
            var dialog= LoginDialog()
            dialog.show(supportFragmentManager,"")
        }
    }

    override fun setMoreHate() {
//        finish()
//
    }

    override fun setShareWX() {
        ShareUtil.shareWxCircle(this,"来自晚豹APP的一条热门动态","来自晚豹APP的一条热门动态",BaseUrl.HOST_URL+"share/page?id="+community_id+"&type=3")
    }

    override fun setSharePYQ() {
//        Toast.Tips("分享到朋友圈")
        ShareUtil.shareWx(this,"来自晚豹APP的一条热门动态","来自晚豹APP的一条热门动态",BaseUrl.HOST_URL+"share/page?id="+community_id+"&type=3")
    }

    override fun setShareQQ() {
//        Toast.Tips("分享到QQ")
        ShareUtil.QQShare(this,"晚豹App","来自晚豹APP的一条热门动态",BaseUrl.HOST_URL+"share/page?id="+community_id+"&type=3")
    }

    var mCurrentFragment: BaseFragment? = null
    var fragment1: BaseFragment? = null
    var fragment2: BaseFragment? = null
    private lateinit var shareDialog:ShareDialog
    private var moreDialog: MoreDialog?=null
    private lateinit var editDialog:CommentInputDialog
    private lateinit var dialog: CommunityReportDialog
    private val presenter by lazy { CommunityDetailsPresenter(this,this,this) }
    private var isLike=false
    private var community_id=0
    private var isCollect=false
    private var isFocus=false
    private var user_id=0
    var id=""

    private var isLogin=false

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_community_details

    override fun setActivityTitle() {
//        var h=SystemUtils.px2dip(this, SystemUtils.getScreenHeight(this).toFloat())-64.0f
//
//        var mheight= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,h,resources.displayMetrics)
////        var params = layout.layoutParams
////        params.height=mheight
//        Log.e("测试",mheight.toString())
//        val params = layout_community_comment.getLayoutParams() as LinearLayout.LayoutParams
//        params.height=mheight.toInt()
//        layout_community_comment.setLayoutParams(params)


    }

    override fun initActivityData() {
        shareDialog= ShareDialog(this)

        editDialog= CommentInputDialog(this)

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

        presenter.getCommunityDetails(CommunityDetailsBody(intent.getStringExtra("id")))


    }

    override fun clickListener() {

        scroll_community_details.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView, scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {

//                if (scrollY == 0) {
//                    //顶部
////                    manage.setmCanVerticalScroll(false)
//                    iv_back.visibility=View.VISIBLE
//                    iv_more.visibility=View.VISIBLE
//                }else{
//                    iv_back.visibility=View.GONE
//                    iv_more.visibility=View.GONE
//                }

                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
                var distanceScrollY = layout_top.height

                if (scrollY >= distanceScrollY) {
                    layout_title.visibility = View.VISIBLE
//                    ll_tab.visibility = View.INVISIBLE
//                    title_divider.visibility = View.VISIBLE
                } else {
                    layout_title.visibility = View.INVISIBLE
//                    ll_tab.visibility = View.VISIBLE
//                    title_divider.visibility = View.GONE
                }

                var  contentView = scroll_community_details.getChildAt(0)
                if (contentView != null && contentView.getMeasuredHeight() == (scroll_community_details.getScrollY() + scroll_community_details.getHeight())) {
                    //底部
//                    manage.setmCanVerticalScroll(true)
                    Log.e("测试","滑动到底部了")
                    if (mCurrentFragment==fragment1){
                        (fragment1 as CommunityCommentFragment).setMore()
                    }else{
                        (fragment2 as CommunityLikeFragment).setMore()
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
//
            }

        })

        Click.viewClick(back).subscribe {
            finish()
        }

        Click.viewClick(more).subscribe {
//            if (moreDialog!=null){
            Log.e("测试","点击了")
            moreDialog = MoreDialog(this,this,id==user_id.toString(),isCollect)
                moreDialog!!.showDialog(this,"")
//                moreDialog!!.setDel(id==user_id.toString())
//                moreDialog!!.setCollect(isCollect)
//            }else{
//
//            }

        }


        Click.viewClick(layout_comment).subscribe {
//            vp_comment.currentItem=0
            switchContent(mCurrentFragment!!,fragment1!!)

//            layout_bottom.visibility=View.VISIBLE
            tv_sort.visibility=View.VISIBLE
            tv_title_sort.visibility=View.VISIBLE


            tv_comment.setTextColor(Color.parseColor("#FCC725"))
            tv_comment.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
            tv_comment_num.setTextColor(Color.parseColor("#FCC725"))
            tv_comment_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_title_comment.setTextColor(Color.parseColor("#FCC725"))
            tv_title_comment.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
            tv_title_comment_num.setTextColor(Color.parseColor("#FCC725"))
            tv_title_comment_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_like.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_like_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

            tv_title_like.setTextColor(Color.parseColor("#a6ffffff"))
            tv_title_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_title_like_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_title_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

        }

        Click.viewClick(layout_like).subscribe {
//            vp_comment.currentItem=1
            switchContent(mCurrentFragment!!,fragment2!!)

//            layout_bottom.visibility=View.INVISIBLE
            tv_sort.visibility=View.INVISIBLE
            tv_title_sort.visibility=View.INVISIBLE

            tv_like.setTextColor(Color.parseColor("#FCC725"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
            tv_like_num.setTextColor(Color.parseColor("#FCC725"))
            tv_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_title_like.setTextColor(Color.parseColor("#FCC725"))
            tv_title_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
            tv_title_like_num.setTextColor(Color.parseColor("#FCC725"))
            tv_title_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_comment.setTextColor(Color.parseColor("#a6ffffff"))
            tv_comment.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_comment_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_comment_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

            tv_title_comment.setTextColor(Color.parseColor("#a6ffffff"))
            tv_title_comment.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_title_comment_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_title_comment_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

        }

        Click.viewClick(layout_title_comment).subscribe {
            //            vp_comment.currentItem=0
            switchContent(mCurrentFragment!!,fragment1!!)

//            layout_bottom.visibility=View.VISIBLE
            tv_title_sort.visibility=View.VISIBLE
            tv_sort.visibility=View.VISIBLE

            tv_comment.setTextColor(Color.parseColor("#FCC725"))
            tv_comment.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
            tv_comment_num.setTextColor(Color.parseColor("#FCC725"))
            tv_comment_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_title_comment.setTextColor(Color.parseColor("#FCC725"))
            tv_title_comment.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
            tv_title_comment_num.setTextColor(Color.parseColor("#FCC725"))
            tv_title_comment_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_like.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_like_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

            tv_title_like.setTextColor(Color.parseColor("#a6ffffff"))
            tv_title_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_title_like_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_title_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

        }

        Click.viewClick(layout_title_like).subscribe {
            //            vp_comment.currentItem=1
            switchContent(mCurrentFragment!!,fragment2!!)

//            layout_bottom.visibility=View.INVISIBLE
            tv_title_sort.visibility=View.INVISIBLE
            tv_sort.visibility=View.INVISIBLE

            tv_like.setTextColor(Color.parseColor("#FCC725"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
            tv_like_num.setTextColor(Color.parseColor("#FCC725"))
            tv_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_title_like.setTextColor(Color.parseColor("#FCC725"))
            tv_title_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)
            tv_title_like_num.setTextColor(Color.parseColor("#FCC725"))
            tv_title_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_comment.setTextColor(Color.parseColor("#a6ffffff"))
            tv_comment.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_comment_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_comment_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

            tv_title_comment.setTextColor(Color.parseColor("#a6ffffff"))
            tv_title_comment.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_title_comment_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_title_comment_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

        }



        Click.viewClick(item_userShare).subscribe {
            shareDialog.show(supportFragmentManager,"")
        }




        Click.viewClick(tv_sort).subscribe {
            if (tv_sort.text.toString()=="热度"){
                tv_sort.text="时间"
                tv_title_sort.text="时间"
                (fragment1 as CommunityCommentFragment).setSort(2)
            }else{
                tv_sort.text="热度"
                tv_title_sort.text="热度"
                (fragment1 as CommunityCommentFragment).setSort(1)
            }
        }

        Click.viewClick(tv_title_sort).subscribe {
            if (tv_title_sort.text.toString()=="热度"){
                tv_title_sort.text="时间"
                tv_sort.text="时间"
                (fragment1 as CommunityCommentFragment).setSort(2)
            }else{
                tv_title_sort.text="热度"
                tv_sort.text="热度"
                (fragment1 as CommunityCommentFragment).setSort(1)
            }
        }





        Click.viewClick(layout_bottom).subscribe {
            if (isLogin) {
                editDialog.show(supportFragmentManager, "")
                editDialog.setHint("评论：")
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }

        Click.viewClick(item_userComment).subscribe {
            if (isLogin) {
                editDialog.show(supportFragmentManager, "")
                editDialog.setHint("评论：")
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }



    }


    //设置初始显示的Fragment
    private fun initFragment(id:String) {
        fragment1 = CommunityCommentFragment(id,1)
        fragment2 = CommunityLikeFragment(id)
        mCurrentFragment = fragment1
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container, fragment1!!)
        transaction.commitAllowingStateLoss()

        if (intent.getSerializableExtra("bean")!=null){
            try {
                var bean:CommunityCommentBean.DataBean.CommentsBean= intent.getSerializableExtra("bean") as CommunityCommentBean.DataBean.CommentsBean
                (fragment1!! as CommunityCommentFragment).setTop(bean)
            }catch (e:Exception){}

        }
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