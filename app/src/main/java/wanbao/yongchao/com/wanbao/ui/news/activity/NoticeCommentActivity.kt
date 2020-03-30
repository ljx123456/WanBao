package wanbao.yongchao.com.wanbao.ui.news.activity

import android.app.Dialog
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import jiguang.chat.utils.DialogCreator
import kotlinx.android.synthetic.main.activity_news_notice.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentDetailsBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityCommentBean
import wanbao.yongchao.com.wanbao.ui.news.adapter.NoticeCommentAdapter
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeCommentBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelNoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.presenter.NoticeCommentPresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NoticeCommentView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class NoticeCommentActivity:BaseActivity(),NoticeCommentView {
    override fun NoticeCommentRequest(data: NoticeCommentBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else{
            adapter= NoticeCommentAdapter(data.data)
            var manager=LinearLayoutManager(this)
            manager.orientation=LinearLayout.VERTICAL
            recy_notice.layoutManager=manager
            recy_notice.adapter=adapter
            adapter!!.setEmptyView(layoutUtils.getNoneNoticeComment())
        }

        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<pageSize){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                presenter.getNoticeComment(NoticeBody("4",pageIndex.toString(),pageSize.toString()))
            }
        },recy_notice)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            if (adapter!!.data[position].dynamic!=null){
                if(adapter!!.data[position].dynamic.state==2){
                    if (adapter!!.data[position].comment!=null&&adapter!!.data[position].comment.state==2){
                        var b=adapter!!.data[position].comment
                        var bean = CommunityCommentBean.DataBean.CommentsBean()
                        bean.id = b.commentId
                        bean.createTime = b.createTime
                        bean.userId = b.userId.toString()
                        bean.content = b.content
                        bean.hasReply = b.hasReply
                        bean.isLike = b.isLike
                        bean.likeNum = b.likeNum
                        bean.nickname = b.nickname
                        bean.avatar = b.avatar
                        intentUtils.intentNoticeCommunityDetails(adapter!!.data[position].dynamic.id.toString(), bean)
                    }else{
                        Toast.Tips("该评论已删除")
                    }
                }else{
                    Toast.Tips("该动态已删除")
                }
            }else if (adapter!!.data[position].information!=null){
                if (adapter!!.data[position].information.state==2){
                    if (adapter!!.data[position].comment!=null&&adapter!!.data[position].comment.state==2){
                        var b=adapter!!.data[position].comment
                        var bean = CommunityCommentBean.DataBean.CommentsBean()
                        bean.id = b.commentId
                        bean.createTime = b.createTime
                        bean.userId = b.userId.toString()
                        bean.content = b.content
                        bean.hasReply = b.hasReply
                        bean.isLike = b.isLike
                        bean.likeNum = b.likeNum
                        bean.nickname = b.nickname
                        bean.avatar = b.avatar
                        intentUtils.intentNoticeActivitiesDetails(adapter!!.data[position].dynamic.id, bean)
                    }else{
                        Toast.Tips("该评论已删除")
                    }
                }else{
                    Toast.Tips("该活动已删除")
                }
            }else{
                if (adapter!!.data[position].comment!=null&&adapter!!.data[position].comment.state==2) {
                    var b = adapter!!.data[position].comment
                    var bean = CommentDetailsBean.DataBean.CommentsBean()
                    bean.id = b.replyId
                    bean.createTime = b.createTime
                    bean.userId = b.userId
                    bean.content = b.content
                    bean.isLike = b.isLike
                    bean.likeNum = b.likeNum
                    bean.nickname = b.nickname
                    bean.avatar = b.avatar
                    if (b.toUser!=null&&b.toUser.avatar!=""){
                        bean.toUser= CommentDetailsBean.DataBean.CommentsBean.ToUserBean()
                        bean.toUser.avatar=b.toUser.avatar
                        bean.toUser.nickname=b.toUser.nickname
                        bean.toUser.userId=b.toUser.id
                    }
                    intentUtils.intentNoticeCommentDetails(adapter!!.data[position].comment.commentId.toString(),bean)
                }else{
                    Toast.Tips("该评论已删除")
                }
            }
        }

        adapter!!.setOnItemLongClickListener { mAdapter, view, position ->
            val listener = View.OnClickListener { v ->
                if (v.id == R.id.jmui_delete_conv_ll) {
                    posi=position
                    presenter.delNotice(DelNoticeBody(adapter!!.data[position].id.toString()))
                    mDialog.dismiss()
                }
            }
            mDialog = DialogCreator.createDelNoticeDialog(this, listener)
            mDialog.show()
            val dm = DisplayMetrics()
            getWindowManager().getDefaultDisplay().getMetrics(dm)
            var mWidth=dm.widthPixels
            mDialog.getWindow()!!.setLayout((0.8 * mWidth).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            return@setOnItemLongClickListener true
        }

    }

    override fun getDelNoticeRequset() {
        adapter!!.remove(posi)
    }

    private val presenter by lazy { NoticeCommentPresenter(this,this,this) }
    private var pageIndex=1
    private val pageSize=10

    private var adapter:NoticeCommentAdapter?=null
    private var posi=0
    private lateinit var mDialog: Dialog

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_news_notice

    override fun setActivityTitle() {
        tv_title.text="评论"
    }

    override fun initActivityData() {
        pageIndex=1
        presenter.getNoticeComment(NoticeBody("4",pageIndex.toString(),pageSize.toString()))
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }

        swip.setOnRefreshListener {
            pageIndex=1
            presenter.getNoticeComment(NoticeBody("4",pageIndex.toString(),pageSize.toString()))
        }
    }
}