package wanbao.yongchao.com.wanbao.ui.mine.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_want_activities.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.ui.explore.adapter.ExploreActivityAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreActivityBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.ExploreActivityFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ExploreActivityFragmentView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.WantActivitiesBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.WantActivitiesPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.WantActivitiesView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class MineWantActivitesFragment: BaseFragment() , WantActivitiesView {
    override fun getWantActivitiesRequest(data: ExploreActivityBean) {
        swip.isRefreshing=false
        if (adapter != null) {
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        } else {
            adapter = ExploreActivityAdapter(data.data)
            var manager = LinearLayoutManager(activity!!)
            manager.orientation = LinearLayout.VERTICAL
            recy_community_comment.layoutManager = manager
            recy_community_comment.adapter = adapter
            adapter!!.setEmptyView(layoutUtils.getNoneWant())
        }

        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size < pageSize) {
            adapter!!.loadMoreEnd()
//                hasMore=false
////            adapter!!.removeAllFooterView()
//                adapter!!.addFooterView(View.inflate(this,R.layout.layout_community_footer,null))
        } else {
            adapter!!.loadMoreComplete()
//                hasMore=true
        }

        adapter!!.setOnLoadMoreListener(object :BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                presenter.getWantActivities(WantActivitiesBody(pageIndex,pageSize))
            }
        },recy_community_comment)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentActivitiesDetails(adapter!!.data[position].id)
        }

    }

    private var adapter: ExploreActivityAdapter?=null
    private val presenter by lazy { WantActivitiesPresenter (this,this,activity!!) }
    private var pageIndex=1
    private var pageSize=10



    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        pageIndex=1
        presenter.getWantActivities(WantActivitiesBody(pageIndex,pageSize))

    }

    override fun setFragmentListener() {
        swip.setOnRefreshListener {
            pageIndex=1
            presenter.getWantActivities(WantActivitiesBody(pageIndex,pageSize))
        }
    }

    override fun layoutID(): Int = R.layout.fragment_want_activities




}