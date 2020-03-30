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
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.MineFansPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineFansView
import wanbao.yongchao.com.wanbao.ui.news.adapter.NoticeFansAdapter
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeFansBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelNoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.presenter.NoticeFansPresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NoticeFansView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class NoticeFansActivity:BaseActivity(),NoticeFansView, MineFansView {
    override fun getDelNoticeRequset() {
        adapter!!.remove(del)
    }

    override fun getFocusRequest() {
        if (adapter!!.data[posi].user.focusType!=null){
            if (adapter!!.data[posi].user.focusType==1){//关注类型：1 已关注，2 被关注，3 互关
//                adapter!!.data[posi].user.focusType=0
            }else if (adapter!!.data[posi].user.focusType==2){//关注类型：1 已关注，2 被关注，3 互关
                adapter!!.data[posi].user.focusType=3
            }else if (adapter!!.data[posi].user.focusType==3){//关注类型：1 已关注，2 被关注，3 互关
//                adapter!!.data[posi].user.focusType=2
            }else{
                adapter!!.data[posi].user.focusType=1
            }
        }else{
            adapter!!.data[posi].user.focusType=1
        }
        adapter!!.notifyDataSetChanged()
    }

    override fun getUnFocusRequest() {
        if (adapter!!.data[posi].user.focusType!=null){
            if (adapter!!.data[posi].user.focusType==1){//关注类型：1 已关注，2 被关注，3 互关
                adapter!!.data[posi].user.focusType=0
            }else if (adapter!!.data[posi].user.focusType==2){//关注类型：1 已关注，2 被关注，3 互关
//                adapter!!.data[posi].user.focusType=3
            }else if (adapter!!.data[posi].user.focusType==3){//关注类型：1 已关注，2 被关注，3 互关
                adapter!!.data[posi].user.focusType=2
            }else{
//                adapter!!.data[posi].user.focusType=1
            }
        }else{
//            adapter!!.data[posi].user.focusType=1
        }
        adapter!!.notifyDataSetChanged()
    }

    override fun getNoticeFansRequest(data: NoticeFansBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else{
            adapter= NoticeFansAdapter(data.data)
            var manager=LinearLayoutManager(this)
            manager.orientation=LinearLayout.VERTICAL
            recy_notice.layoutManager=manager
            recy_notice.adapter=adapter
            adapter!!.setEmptyView(layoutUtils.getNoneNoticeFans())
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
                presenter.getNoticeFans(NoticeBody("1",pageIndex.toString(),pageSize.toString()))
            }
        },recy_notice)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
        }

        adapter!!.setOnItemLongClickListener { mAdapter, view, position ->
            val listener = View.OnClickListener { v ->
                if (v.id == R.id.jmui_delete_conv_ll) {
                    del=position
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

        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            if (view.id==R.id.tv_focus){
                posi=position
                if (adapter!!.data[position].user.focusType!=null){
                    if (adapter!!.data[position].user.focusType==1){//关注类型：1 已关注，2 被关注，3 互关
                        fansPresenter.getUnFocus(UnFocusBody(adapter!!.data[position].user.id.toString(),"1"))
                    }else if(adapter!!.data[position].user.focusType==2){//关注类型：1 已关注，2 被关注，3 互关
                        fansPresenter.getFocus(FocusBody(adapter!!.data[position].user.id.toString(),"1"))
                    }else if(adapter!!.data[position].user.focusType==3){//关注类型：1 已关注，2 被关注，3 互关
                        fansPresenter.getUnFocus(UnFocusBody(adapter!!.data[position].user.id.toString(),"1"))
                    }else {//关注类型：1 已关注，2 被关注，3 互关
                        fansPresenter.getFocus(FocusBody(adapter!!.data[position].user.id.toString(),"1"))
                    }
                }else {//关注类型：1 已关注，2 被关注，3 互关
                    fansPresenter.getFocus(FocusBody(adapter!!.data[position].user.id.toString(),"1"))
                }
            }
        }

    }

    private val presenter by lazy { NoticeFansPresenter(this,this,this) }
    private var pageIndex=1
    private val pageSize=10
    private var adapter:NoticeFansAdapter?=null
    private val fansPresenter by lazy { MineFansPresenter(this,this,this) }
    private var posi=0
    private var del=0
    private lateinit var mDialog:Dialog

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_news_notice

    override fun setActivityTitle() {
        tv_title.text="粉丝"
    }

    override fun initActivityData() {
        pageIndex=1
        presenter.getNoticeFans(NoticeBody("1",pageIndex.toString(),pageSize.toString()))
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }

        swip.setOnRefreshListener {
            pageIndex=1
            presenter.getNoticeFans(NoticeBody("1",pageIndex.toString(),pageSize.toString()))
        }
    }

    override fun onResume() {
        super.onResume()
        pageIndex=1
        presenter.getNoticeFans(NoticeBody("1",pageIndex.toString(),pageSize.toString()))
    }
}