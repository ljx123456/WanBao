package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.LinearLayout
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
import kotlinx.android.synthetic.main.fragment_community_comment.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.main.adapter.CommunityCommentAdapter
import wanbao.yongchao.com.wanbao.ui.main.dialog.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddCommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddReplyBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentDetailsBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityCommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.CommunityCommentPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunityCommentView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.lang.Exception

@SuppressLint("ValidFragment")
class CommunityCommentFragment(val id:String,val type:Int) : BaseFragment(),CommentInputDialog.Send,CommunityCommentView,CommunityCommentDialog.Comment{
    override fun getAddReplyRequest(data: AddReplyBean) {
        var bean=CommunityCommentBean.DataBean.CommentsBean.User()
        try {
            var user=DBUtils.getUser()
            bean.avatar=user.avatar
            bean.name=user.nickName
            bean.content=comment
        }catch (e:Exception){
            try {
                var user=DBUtils.getBusiness()
                bean.avatar=user.avatar
                bean.name=user.nickName
                bean.content=comment
            }catch (e:Exception){}
        }
        adapter!!.data[posi].bean=bean
        adapter!!.notifyDataSetChanged()
    }

    override fun getCommentDelRequest() {
        Toast.Tips("删除成功")
        adapter!!.remove(posi)
    }

    override fun setReply() {
        if (isLogin) {
            dialog.show(childFragmentManager, "")
            dialog.setHint("回复@${adapter!!.data[posi].nickname}：")
        }else{
            var dialog= LoginDialog()
            dialog.show(childFragmentManager,"")
        }
    }

    override fun setCopy() {
        val cm=activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var mClipData = ClipData.newPlainText("Label",adapter!!.data[posi].content)
        cm.setPrimaryClip(mClipData)
    }

    override fun setChat() {
        if (isLogin) {
            if (isMine) {
                presenter.getDelComment(CommentDelBody(adapter!!.data[posi].id.toString(), "1"))
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
                                var intent = Intent(activity!!, ChatActivity::class.java)
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
            dialog.show(childFragmentManager,"")
        }
    }

    override fun setReport() {
        if (isLogin) {
            reportDialog = HomeReportDialog(adapter!!.data[posi].userId.toString())
            reportDialog.show(childFragmentManager, "")
        }else{
            var dialog= LoginDialog()
            dialog.show(childFragmentManager,"")
        }
    }

    override fun getAddCommentRequest(data: AddCommentBean) {
        var bean=CommunityCommentBean.DataBean.CommentsBean()
            try {
                var user=DBUtils.getUser()
                bean.avatar=user.avatar
                bean.nickname=user.nickName
                bean.likeNum=0
                bean.isLike=0
                bean.hasReply=0
                bean.content=content
                bean.id=data.data
                bean.userId=user.userId
                bean.createTime="刚刚"

            }catch (e:Exception){
                var user=DBUtils.getBusiness()
                bean.avatar=user.avatar
                bean.nickname=user.nickName
                bean.likeNum=0
                bean.isLike=0
                bean.hasReply=0
                bean.content=content
                bean.id=data.data
                bean.userId=user.businessId
                bean.createTime="刚刚"
            }
            if (adapter!=null) {
                adapter!!.addData(0, bean)
            }else{
                recy_community_comment.visibility = View.VISIBLE
                layout_null.visibility = View.GONE
                var list=ArrayList<CommunityCommentBean.DataBean.CommentsBean>()
                list.add(bean)
                adapter = CommunityCommentAdapter(list)
                var manager = LinearLayoutManager(activity!!)
                manager.orientation = LinearLayout.VERTICAL
                recy_community_comment.layoutManager = manager
                recy_community_comment.adapter = adapter
                recy_community_comment.isNestedScrollingEnabled = false

                adapter!!.removeAllFooterView()
                adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null))

//                adapter!!.setOnItemClickListener { mAdapter, view, position ->
//                    intentUtils.intentCommentDetails(adapter!!.data[position].id.toString())
//                }

                adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
                    when (view.id) {
                        R.id.item_commentContent -> {
                            if (isLogin) {
                                dialog.show(childFragmentManager, "")
                                dialog.setHint("回复@${adapter!!.data[position].nickname}：")
                                posi = position
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(childFragmentManager,"")
                            }
                        }
                        R.id.item_commentMore ->{
                            intentUtils.intentCommentDetails(adapter!!.data[position].id.toString())
                        }
                        R.id.item_commentHeader->{
                            intentUtils.intentBusinessHomePage(adapter!!.data[position].userId.toString())
                        }
                        R.id.item_commentName->{
                            intentUtils.intentBusinessHomePage(adapter!!.data[position].userId.toString())
                        }
                        R.id.item_commentLike ->{
                            if (isLogin) {
                                pos = position
                                isLike = adapter!!.data[position].isLike == 1
                                presenter.getLike(LikeBody(adapter!!.data[position].id, 3, 3))
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(childFragmentManager,"")
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
                            commentDialog= CommunityCommentDialog(this,adapter!!.data[position].avatar,adapter!!.data[position].nickname,isMine)
                            commentDialog!!.show(childFragmentManager,"")
                        }else if (inf != null && inf.size > 0) {
                            isMine=(inf[0].businessId==adapter!!.data[position].userId.toString())
//                        name=adapter!!.data[position].nickname
                            posi=position
                            commentDialog= CommunityCommentDialog(this,adapter!!.data[position].avatar,adapter!!.data[position].nickname,isMine)
                            commentDialog!!.show(childFragmentManager,"")
                        }
                    } else{

                    }
                    return@setOnItemLongClickListener true
                }
            }


    }

    override fun getLikeRequest() {
        if (!isLike){
            adapter!!.data[pos].isLike=1
            adapter!!.data[pos].likeNum=(adapter!!.data[pos].likeNum+1)
            adapter!!.notifyDataSetChanged()
        }else{
            adapter!!.data[pos].isLike=0
            adapter!!.data[pos].likeNum=(adapter!!.data[pos].likeNum-1)
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun CommunityCommentRequest(data: CommunityCommentBean) {
        if ((parentFragment!=null&&parentFragment!!.isVisible)||(activity!=null&&!activity!!.isFinishing)) {
            if (data.data != null && data.data.comments != null && data.data.comments.size > 0) {
                recy_community_comment.visibility = View.VISIBLE
                layout_null.visibility = View.GONE

                if (adapter != null) {
                    if (pageIndex == 1) {
                        adapter!!.setNewData(data.data.comments)
                    } else {
                        adapter!!.addData(data.data.comments)
                    }
                } else {
                    adapter = CommunityCommentAdapter(data.data.comments)
                    var manager = LinearLayoutManager(activity!!)
                    manager.orientation = LinearLayout.VERTICAL
                    recy_community_comment.layoutManager = manager
                    recy_community_comment.adapter = adapter
                    recy_community_comment.isNestedScrollingEnabled = false
                }

//            adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
//                override fun onLoadMoreRequested() {
//                    pageIndex = pageIndex + 1
//                    presenter.getCommunityComment(CommunityCommentBody(id, 1, sort, pageIndex, pageSize))
//                }
//            }, recy_community_comment)
                Log.e("测试","获取评论")
                if (data.data.comments.size < pageSize) {
                    Log.e("测试","添加view")
                    adapter!!.removeAllFooterView()
                    adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null))
                    isFlag = false
                }

//            adapter!!.setOnItemClickListener { mAdapter, view, position ->
//                intentUtils.intentCommentDetails(adapter!!.data[position].id.toString())
//            }

                adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
                    when (view.id) {
                        R.id.item_commentContent -> {
                            if (isLogin) {
                                dialog.show(childFragmentManager, "")
                                dialog.setHint("回复@${adapter!!.data[position].nickname}：")
                                posi = position
                            } else {
                                var dialog = LoginDialog()
                                dialog.show(childFragmentManager, "")
                            }
                        }
                        R.id.item_commentMore -> {
                            intentUtils.intentCommentDetails(adapter!!.data[position].id.toString())
                        }
                        R.id.item_commentHeader -> {
                            intentUtils.intentBusinessHomePage(adapter!!.data[position].userId.toString())
                        }
                        R.id.item_commentName -> {
                            intentUtils.intentBusinessHomePage(adapter!!.data[position].userId.toString())
                        }
                        R.id.item_commentLike -> {
                            if (isLogin) {
                                pos = position
                                isLike = adapter!!.data[position].isLike == 1
                                presenter.getLike(LikeBody(adapter!!.data[position].id, 3, 3))
                            } else {
                                var dialog = LoginDialog()
                                dialog.show(childFragmentManager, "")
                            }
                        }
                    }
                }

                adapter!!.setOnItemLongClickListener { mAdapter, view, position ->
                    var user = GreenDaoHelper.getDaoSessions().userDBDao
                    var business = GreenDaoHelper.getDaoSessions().businessDBDao
                    if (user != null || business != null) {
                        var info = user.loadAll()
                        var inf = business.loadAll()
                        if (info != null && info.size > 0) {
                            isMine = (info[0].userId == adapter!!.data[position].userId.toString())
//                        name=adapter!!.data[position].nickname
                            posi = position
                            commentDialog = CommunityCommentDialog(this, adapter!!.data[position].avatar, adapter!!.data[position].nickname, isMine)
                            commentDialog!!.show(childFragmentManager, "")
                        } else if (inf != null && inf.size > 0) {
                            isMine = (inf[0].businessId == adapter!!.data[position].userId.toString())
//                        name=adapter!!.data[position].nickname
                            posi = position
                            commentDialog = CommunityCommentDialog(this, adapter!!.data[position].avatar, adapter!!.data[position].nickname, isMine)
                            commentDialog!!.show(childFragmentManager, "")
                        }
                    } else {

                    }
                    return@setOnItemLongClickListener true
                }

            } else {
                isFlag = false
                if (pageIndex == 1) {
                    recy_community_comment.visibility = View.VISIBLE
                    layout_null.visibility = View.VISIBLE
                }
                if (adapter != null) {
                    adapter!!.removeAllFooterView()
                    adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null))
                }
            }
        }
    }

    override fun CommmunityCommentError() {

    }

    override fun send(str: String) {
        comment=str
        presenter.getAddReply(AddReplyBody(adapter!!.data[posi].id,str))
    }


    private var adapter:CommunityCommentAdapter?=null
    private var dialog=CommentInputDialog(this)
    private var posi=0
    private var sort=1
    private var pageIndex=1
    private var pageSize=10
    private val presenter by lazy { CommunityCommentPresenter(this,this,activity!!) }
    private var pos=0
    private var isLike=false
    private var content=""
    private var isFlag=true

    private var comment=""

    private var commentDialog:CommunityCommentDialog?=null
    private var isMine=false
    private var isChat=true

    private lateinit var reportDialog:  HomeReportDialog

    private var isLogin=false

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
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
        presenter.getCommunityComment(CommunityCommentBody(id,type,sort,pageIndex,pageSize))
    }

    override fun setFragmentListener() {

    }

    override fun layoutID(): Int = R.layout.fragment_community_comment

    fun setComment(str:String,id:Int,type:Int){
        content=str
        presenter.getAddComment(AddCommentBody(id,type,str))

//        adapter.addData(0, CommunityCommentBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572671392134&di=fc96f906eb237fc53a9203c33b07acbc&imgtype=0&src=http%3A%2F%2Fphoto.orsoon.com%2F180405%2F180405_186%2FOwSjWTf7U2_small.jpg"
//                ,"点点","刚刚",0,str,false))
    }

    fun setMore(){
        if (isFlag) {
            pageIndex = pageIndex + 1
            presenter.getCommunityComment(CommunityCommentBody(id, type, sort, pageIndex, pageSize))
        }
    }


    fun setSort(s:Int){
        sort=s
        pageIndex=1
        presenter.getCommunityComment(CommunityCommentBody(id,type,sort,pageIndex,pageSize))
    }


    fun setTop(bean:CommunityCommentBean.DataBean.CommentsBean){
        if (adapter!=null){
            adapter!!.data.forEachIndexed { position, commentsBean ->
                if (adapter!!.data[position].id==bean.id){
                    adapter!!.remove(position)
                }
            }
            adapter!!.addData(0,bean)
        }
    }

}