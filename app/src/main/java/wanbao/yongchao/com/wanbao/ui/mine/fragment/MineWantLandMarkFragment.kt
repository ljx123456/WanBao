package wanbao.yongchao.com.wanbao.ui.mine.fragment

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.adapter.SearchLandMarkAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchLandMarkBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.WantActivitiesBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.WantLandMarkPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.WantLandMarkView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class MineWantLandMarkFragment:BaseFragment(),WantLandMarkView {

    private var adapter: SearchLandMarkAdapter?=null
    private var pageIndex=1
    private var pageSize=10
    private val presenter by lazy { WantLandMarkPresenter(this,this,activity!!) }

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        pageIndex=1
        presenter.getWantLandMark(WantActivitiesBody(pageIndex,pageSize))
    }

    override fun setFragmentListener() {
        swip.setOnRefreshListener {
            pageIndex=1
            presenter.getWantLandMark(WantActivitiesBody(pageIndex,pageSize))
        }
    }

    override fun layoutID(): Int = R.layout.fragment_search

    override fun getLandMarkRequest(data: SearchLandMarkBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = SearchLandMarkAdapter(data.data)
            var manager = LinearLayoutManager(activity!!)
            manager.orientation = LinearLayout.VERTICAL
            recy_search_user.layoutManager = manager
            recy_search_user.adapter = adapter
            adapter!!.setEmptyView(layoutUtils.getNoneWant())
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
                presenter.getWantLandMark(WantActivitiesBody(pageIndex,pageSize))
            }
        },recy_search_user)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentLandMarkDetails(adapter!!.data[position].cityMapId.toString())
        }
    }
}