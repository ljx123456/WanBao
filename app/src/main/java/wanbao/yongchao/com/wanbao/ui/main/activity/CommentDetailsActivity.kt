package wanbao.yongchao.com.wanbao.ui.main.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_comment_details.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.main.adapter.CommentDetailsAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentDetailsBean
import wanbao.yongchao.com.wanbao.utils.utils.Click
import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat.getSystemService
import android.text.TextUtils
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.callback.GetUserInfoCallback
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.UserInfo
import cn.jpush.im.android.eventbus.EventBus
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import jiguang.chat.activity.ChatActivity
import jiguang.chat.entity.Event
import jiguang.chat.entity.EventType
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.dialog.CommentDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.CommentInputDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.HomeReportDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.LoginDialog
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddReplyBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.CommentDetailsPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommentDetailsView
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.AtColorUtil
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllSpan
import java.math.BigDecimal
import kotlin.Exception


class CommentDetailsActivity:BaseActivity(),CommentInputDialog.Send,CommentDetailsView,CommentDialog.Comment{
    override fun getCommentDelRequest() {
        Toast.Tips("删除成功")
        adapter!!.remove(posi)
    }

    override fun setChat() {
        if (isLogin) {
            if (isMine) {
                presenter.getDelComment(CommentDelBody(adapter!!.data[posi].id.toString(), "2"))
            } else {
                if (isChat) {
                    isChat = false
                    JMessageClient.getUserInfo(adapter!!.data[posi].userId.toString(), "feec8ae8389113d4bf6915c5", object : GetUserInfoCallback() {
                        override fun gotResult(p0: Int, p1: String, p2: UserInfo) {
                            LogUtils.a("测试", "进来了${p1}")
                            if (p0 == 0) {
                                LogUtils.a("测试", "进来了")
                                isChat = true
                                var mUserInfo = p2
                                var intent = Intent(this@CommentDetailsActivity, ChatActivity::class.java)
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
                } else {
                    Toast.Tips("聊天正在创建中。。。")
                }
            }
        }else{
            var dialog= LoginDialog()
            dialog.show(supportFragmentManager,"")
        }
    }

    override fun setReport() {
        if (isLogin) {
            reportDialog = HomeReportDialog(adapter!!.data[posi].userId.toString())
            reportDialog.show(supportFragmentManager, "")
        }else{
            var dialog= LoginDialog()
            dialog.show(supportFragmentManager,"")
        }
    }

    override fun getCommentRequest(data: CommentBean) {
        tv_title.text="共${data.data.totalCommentNum}条回复"


        ImageLoad.setUserHead(data.data.avatar,iv_header)
        tv_name.text=data.data.nickname
        tv_time.text=data.data.createTime
        tv_content.text=AtColorUtil.getShowAllText(data.data.content,object :ShowAllSpan.OnAllSpanClickListener{
            override fun onClick(widget: View?) {

            }

            override fun onClickAt(widget: View?, name: String) {
//                Toast.Tips(name)
                intentUtils.intentUserHomePageForName(name)
            }
        })

        if(data.data.isLike==0){//没点赞
            isLike=false
            tv_like.setCompoundDrawablesWithIntrinsicBounds(null,null,resources.getDrawable(R.mipmap.homepage_button_list_like_nor),null)
        }else{
            isLike=true
            tv_like.setCompoundDrawablesWithIntrinsicBounds(null,null,resources.getDrawable(R.mipmap.homepage_button_list_like_pre),null)
        }
        likeNum=data.data.likeNum
        var num=BigDecimal(likeNum)
        if (likeNum<1000){
            tv_like.text=num.toString()
        }else if (likeNum<10000){
            tv_like.text=num.divide(BigDecimal(1000)).setScale(1,BigDecimal.ROUND_DOWN).toString()
        }else{
            tv_like.text=num.divide(BigDecimal(10000)).setScale(1,BigDecimal.ROUND_DOWN).toString()
        }

        pageIndex=1
        presenter.getCommentDetails(CommentDetailsBody(data.data.id.toString(),sort,pageIndex,pageSize))
    }

    override fun getCommentError() {

    }

    override fun getAddReplyRequest(data: AddReplyBean) {
        var bean=CommentDetailsBean.DataBean.CommentsBean()
        var s=""
        if (replay!=""){
            s=replay+content
        }else{
            s=content
        }

//        if (adapter!=null){
            try {
                var user= DBUtils.getUser()
                bean.avatar=user.avatar
                bean.nickname=user.nickName
                bean.likeNum=0
                bean.isLike=0
                bean.content=s
                bean.userId=user.userId.toInt()
                bean.id=data.data
                bean.createTime="刚刚"

            }catch (e: Exception){
                var user= DBUtils.getBusiness()
                bean.avatar=user.avatar
                bean.nickname=user.nickName
                bean.likeNum=0
                bean.isLike=0
                bean.content=s
                bean.userId=user.businessId.toInt()
                bean.id=data.data
                bean.createTime="刚刚"
            }
        if (adapter!=null) {
            adapter!!.addData(0, bean)
        }else{
            var list=ArrayList<CommentDetailsBean.DataBean.CommentsBean>()
            list.add(bean)
            adapter = CommentDetailsAdapter(list)
            var manager = LinearLayoutManager(this)
            manager.orientation = LinearLayout.VERTICAL
            recy_comment.layoutManager = manager
            recy_comment.adapter = adapter

            adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
                override fun onLoadMoreRequested() {
                    pageIndex=pageIndex+1
                    presenter.getCommentDetails(CommentDetailsBody(intent.getStringExtra("id"),sort,pageIndex,pageSize))
                }
            },recy_comment)
//        recy_comment.isNestedScrollingEnabled=false
            adapter!!.setOnItemClickListener { mAdapter, view, position ->
                if (isLogin) {
                    dialog.show(supportFragmentManager, "")
                    dialog.setHint("回复@${adapter!!.data[position].nickname}：")
                    replay = "回复@${adapter!!.data[position].nickname}："
                    reply_id = adapter!!.data[position].userId
                }else{
                    var dialog= LoginDialog()
                    dialog.show(supportFragmentManager,"")
                }
            }

            adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
                when (view.id) {
                    R.id.item_commentContent -> {
                        if (isLogin) {
                            dialog.show(supportFragmentManager, "")
                            dialog.setHint("回复@${adapter!!.data[position].nickname}：")
                            replay = "回复@${adapter!!.data[position].nickname} &h;："
                            reply_id = adapter!!.data[position].userId
                        }else{
                            var dialog= LoginDialog()
                            dialog.show(supportFragmentManager,"")
                        }
                    }
                    R.id.item_commentHeader->{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].userId.toString())
                    }
                    R.id.item_commentName->{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].userId.toString())
                    }
                    R.id.item_commentLike ->{
                        if (isLogin) {
                            isReplay = true
                            pos = position
                            isLike = adapter!!.data[position].isLike == 1
                            presenter.getLike(LikeBody(adapter!!.data[position].id, 3, 4))
                        }else{
                            var dialog= LoginDialog()
                            dialog.show(supportFragmentManager,"")
                        }
                    }
                }
            }

            adapter!!.setOnItemLongClickListener { mAdapter, view, position ->
                var user = GreenDaoHelper.getDaoSessions().userDBDao
                var business= GreenDaoHelper.getDaoSessions().businessDBDao
                if (user != null||business!=null) {
                    var info = user.loadAll()
                    var inf = business.loadAll()
                    if (info != null && info.size > 0) {
                        isMine=(info[0].userId==adapter!!.data[position].userId.toString())
//                        name=adapter!!.data[position].nickname
                        posi=position
                        commentDialog= CommentDialog(this,adapter!!.data[position].avatar,adapter!!.data[position].nickname,isMine)
                        commentDialog!!.show(supportFragmentManager,"")
                    }else if (inf != null && inf.size > 0) {
                        isMine=(inf[0].businessId==adapter!!.data[position].userId.toString())
//                        name=adapter!!.data[position].nickname
                        posi=position
                        commentDialog= CommentDialog(this,adapter!!.data[position].avatar,adapter!!.data[position].nickname,isMine)
                        commentDialog!!.show(supportFragmentManager,"")
                    }
                } else{

                }
                return@setOnItemLongClickListener true
            }
        }
            replay=""
            reply_id=-1
//        }
    }

    override fun getLikeRequest() {
        if (isReplay){
            if (!isLike){
                adapter!!.data[pos].isLike=1
                adapter!!.data[pos].likeNum=(adapter!!.data[pos].likeNum+1)
                adapter!!.notifyDataSetChanged()
            }else{
                adapter!!.data[pos].isLike=0
                adapter!!.data[pos].likeNum=(adapter!!.data[pos].likeNum-1)
                adapter!!.notifyDataSetChanged()
            }
        }else {
            if (!isLike) {
                isLike = true
                likeNum = likeNum + 1
                tv_like.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(R.mipmap.homepage_button_list_like_pre), null)
            } else {
                isLike = false
                likeNum = likeNum - 1
                tv_like.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(R.mipmap.homepage_button_list_like_nor), null)
            }
            var num = BigDecimal(likeNum)
            if (likeNum < 1000) {
                tv_like.text = num.toString()
            } else if (likeNum < 10000) {
                tv_like.text = num.divide(BigDecimal(1000)).setScale(1, BigDecimal.ROUND_DOWN).toString()
            } else {
                tv_like.text = num.divide(BigDecimal(10000)).setScale(1, BigDecimal.ROUND_DOWN).toString()
            }
        }
    }

    override fun getCommentDetailsRequest(data: CommentDetailsBean) {

        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data.comments)
            }else{
                adapter!!.addData(data.data.comments)
            }
        }else {
            adapter = CommentDetailsAdapter(data.data.comments)
            var manager = LinearLayoutManager(this)
            manager.orientation = LinearLayout.VERTICAL
            recy_comment.layoutManager = manager
            recy_comment.adapter = adapter

            if (intent.getSerializableExtra("bean")!=null&&intent.getSerializableExtra("bean")!=""){
                var bean=(intent.getSerializableExtra("bean") as CommentDetailsBean.DataBean.CommentsBean)
                try {
                    adapter!!.data.forEachIndexed { positon, commentsBean ->
                        if (adapter!!.data[positon].id==bean.id){
                            adapter!!.remove(positon)
                        }
                    }
                    adapter!!.addData(0,bean)
                }catch (e:Exception){}
            }
        }

        adapter!!.setPreLoadNumber(7)
        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.comments.size<10){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                presenter.getCommentDetails(CommentDetailsBody(intent.getStringExtra("id"),sort,pageIndex,pageSize))
            }
        },recy_comment)
//        recy_comment.isNestedScrollingEnabled=false
        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            if (isLogin) {
                dialog.show(supportFragmentManager, "")
                dialog.setHint("回复@${adapter!!.data[position].nickname}：")
                replay = "回复@${adapter!!.data[position].nickname}："
                reply_id = adapter!!.data[position].userId
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }

        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            when (view.id) {
                R.id.item_commentContent -> {
                    if (isLogin) {
                        dialog.show(supportFragmentManager, "")
                        dialog.setHint("回复@${adapter!!.data[position].nickname}：")
                        replay = "回复@${adapter!!.data[position].nickname} &h;："
                        reply_id = adapter!!.data[position].userId
                    }else{
                        var dialog= LoginDialog()
                        dialog.show(supportFragmentManager,"")
                    }
                }
                R.id.item_commentHeader->{
                    intentUtils.intentBusinessHomePage(adapter!!.data[position].userId.toString())
                }
                R.id.item_commentName->{
                    intentUtils.intentBusinessHomePage(adapter!!.data[position].userId.toString())
                }
                R.id.item_commentLike ->{
                    if (isLogin) {
                        isReplay = true
                        pos = position
                        isLike = adapter!!.data[position].isLike == 1
                        presenter.getLike(LikeBody(adapter!!.data[position].id, 3, 4))
                    }else{
                        var dialog= LoginDialog()
                        dialog.show(supportFragmentManager,"")
                    }
                }
            }
        }

        adapter!!.setOnItemLongClickListener { mAdapter, view, position ->
            var user = GreenDaoHelper.getDaoSessions().userDBDao
            var business= GreenDaoHelper.getDaoSessions().businessDBDao
            if (user != null||business!=null) {
                var info = user.loadAll()
                var inf = business.loadAll()
                if (info != null && info.size > 0) {
                    isMine=(info[0].userId==adapter!!.data[position].userId.toString())
//                        name=adapter!!.data[position].nickname
                    posi=position
                    commentDialog= CommentDialog(this,adapter!!.data[position].avatar,adapter!!.data[position].nickname,isMine)
                    commentDialog!!.show(supportFragmentManager,"")
                }else if (inf != null && inf.size > 0) {
                    isMine=(inf[0].businessId==adapter!!.data[position].userId.toString())
//                        name=adapter!!.data[position].nickname
                    posi=position
                    commentDialog= CommentDialog(this,adapter!!.data[position].avatar,adapter!!.data[position].nickname,isMine)
                    commentDialog!!.show(supportFragmentManager,"")
                }
            } else{

            }
            return@setOnItemLongClickListener true
        }

    }

    override fun getCommentDetailsError() {

    }

    override fun send(str: String) {
//        adapter.addData(0, CommentDetailsBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572671392134&di=fc96f906eb237fc53a9203c33b07acbc&imgtype=0&src=http%3A%2F%2Fphoto.orsoon.com%2F180405%2F180405_186%2FOwSjWTf7U2_small.jpg"
//                ,"点点","刚刚",str,0))
        content=str
        if (replay!=""&&reply_id!=-1){
            presenter.getAddReply(AddReplyBody(intent.getStringExtra("id").toInt(),str,reply_id))
        }else{
            presenter.getAddReply(AddReplyBody(intent.getStringExtra("id").toInt(),str))
        }
    }

    var list=ArrayList<CommentDetailsBean.DataBean.CommentsBean>()
    private var adapter:CommentDetailsAdapter?=null
    lateinit var dialog:CommentInputDialog
    private val presenter by lazy { CommentDetailsPresenter(this,this,this) }
    private var sort=1
    private var pageIndex=1
    private var pageSize=10
    private var isLike=false
    private var likeNum=0
    private var replay=""
    private var isReplay=false
    private var pos=0
    private var reply_id=-1
    private var content=""

    private var commentDialog:CommentDialog?=null
    private var isMine=false
    private var isChat=true
    private var posi=0

    private var isLogin=false

    private lateinit var reportDialog: HomeReportDialog

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_comment_details

    override fun setActivityTitle() {

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

        dialog= CommentInputDialog(this)
        presenter.getComment(CommentBody(intent.getStringExtra("id")))
    }

    override fun clickListener() {

        Click.viewClick(iv_back).subscribe {
            finish()
        }


        Click.viewClick(layout_bottom).subscribe {
            if (isLogin) {
                dialog.show(supportFragmentManager, "")
                dialog.setHint("评论：")
                replay = ""
                reply_id = -1
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }

        Click.viewClick(tv_like).subscribe {
            if (isLogin) {
                isReplay = false
                presenter.getLike(LikeBody(intent.getStringExtra("id").toInt(), 3, 3))
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }

        Click.viewClick(iv_header).subscribe {
            intentUtils.intentUserHomePage(intent.getStringExtra("id"))
        }
        Click.viewClick(tv_name).subscribe {
            intentUtils.intentUserHomePage(intent.getStringExtra("id"))
        }

        Click.viewClick(tv_sort).subscribe {
            if (tv_sort.text.toString()=="热度"){
                tv_sort.text="时间"
                sort=2
                pageIndex=1
                presenter.getCommentDetails(CommentDetailsBody(intent.getStringExtra("id"),sort,pageIndex,pageSize))
            }else{
                tv_sort.text="热度"
                sort=1
                pageIndex=1
                presenter.getCommentDetails(CommentDetailsBody(intent.getStringExtra("id"),sort,pageIndex,pageSize))
            }
        }

    }


}