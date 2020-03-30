package wanbao.yongchao.com.wanbao.ui.set.activity

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_blacklist.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FansBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.AtFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AtFragmentView
import wanbao.yongchao.com.wanbao.ui.mine.adapter.MineFansAdapter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.UserInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.MineFansPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineFansView
import wanbao.yongchao.com.wanbao.ui.set.adapter.BlacklistAdapter
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class BlacklistActivity:BaseActivity(), AtFragmentView, MineFansView {
    override fun getFocusRequest() {

        adapter!!.data[posi].isFlag=false
        adapter!!.notifyDataSetChanged()
    }

    override fun getUnFocusRequest() {
        adapter!!.data[posi].isFlag=true
        adapter!!.notifyDataSetChanged()
    }

    override fun getFansRequest(data: FansBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = BlacklistAdapter(data.data)
            var manager = LinearLayoutManager(this)
            manager.orientation = LinearLayout.VERTICAL
            recy_blacklist.layoutManager = manager
            recy_blacklist.adapter = adapter


            adapter!!.setEmptyView(layoutUtils.getNoneBlacklist())

        }

        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<10){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                presenter.getFans(FansBody(id,"0",pageIndex.toString(),pageSize.toString()))
            }
        },recy_blacklist)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            if (adapter!!.data[position].role==1){//用户
                intentUtils.intentUserHomePage(adapter!!.data[position].id.toString())
            }else{
                intentUtils.intentBusinessHomePage(adapter!!.data[position].id.toString())
            }
        }


        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            posi=position
            if (adapter!!.data[position].isFlag){
                fansPresenter.getFocus(FocusBody(adapter!!.data[position].id.toString(),"0"))
            }else{
                fansPresenter.getUnFocus(UnFocusBody(adapter!!.data[position].id.toString(),"0"))
            }
        }
    }

    private val presenter by lazy { AtFragmentPresenter(this,this,this) }
    private val fansPresenter by lazy { MineFansPresenter(this,this,this) }
    private var pageIndex=1
    private var pageSize=10
    private var adapter: BlacklistAdapter?=null
    private var posi=0
    private var id=""


    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_blacklist

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

        pageIndex=1
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                id=info.get(0).userId
                presenter.getFans(FansBody(id,"0",pageIndex.toString(),pageSize.toString()))
            }else if (inf != null && inf.size > 0) {
                id=inf.get(0).businessId
                presenter.getFans(FansBody(id,"0",pageIndex.toString(),pageSize.toString()))
            }
        }

    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }
    }
}