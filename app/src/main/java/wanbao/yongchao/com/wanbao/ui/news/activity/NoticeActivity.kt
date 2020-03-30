package wanbao.yongchao.com.wanbao.ui.news.activity

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_news_notice.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.news.adapter.NoticeAdapter
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.presenter.NoticePresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NoticeView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class NoticeActivity:BaseActivity(),NoticeView {
    override fun getNoticeRequest(data: NoticeBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else{
            adapter= NoticeAdapter(data.data)
            var manager=LinearLayoutManager(this)
            manager.orientation=LinearLayout.VERTICAL
            recy_notice.layoutManager=manager
            recy_notice.adapter=adapter
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
                presenter.getNotice(NoticeBody("0",pageIndex.toString(),pageSize.toString()))
            }
        },recy_notice)


        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            if (adapter!!.data[position].jumpType==1){//跳转类型：0 不跳转，1 个人中心，2 活动，3 认证
                intentUtils.intentMain("mine")
                finish()
            }else if (adapter!!.data[position].jumpType==2){
                intentUtils.intentActivitiesDetails(adapter!!.data[position].jumpId)
            }else if (adapter!!.data[position].jumpType==3){
                var user = GreenDaoHelper.getDaoSessions().userDBDao
                var business= GreenDaoHelper.getDaoSessions().businessDBDao
                if (user != null||business!=null) {
                    var info = user.loadAll()
                    var inf = business.loadAll()
                    if (info != null && info.size > 0) {
                        intentUtils.intentAuthCenter()
                    }else if (inf != null && inf.size > 0) {
                        intentUtils.intentBusinessAuthCenter()
                    }
                }
            }
        }

    }

    private val presenter by lazy { NoticePresenter(this,this,this) }
    private var pageIndex=1
    private val pageSize=10
    private var adapter:NoticeAdapter?=null

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_news_notice

    override fun setActivityTitle() {
        tv_title.text="系统通知"
    }

    override fun initActivityData() {
        pageIndex=1
        presenter.getNotice(NoticeBody("0",pageIndex.toString(),pageSize.toString()))
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe { finish() }

        swip.setOnRefreshListener {
            pageIndex=1
            presenter.getNotice(NoticeBody("0",pageIndex.toString(),pageSize.toString()))
        }
    }
}