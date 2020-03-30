package wanbao.yongchao.com.wanbao.ui.explore.activity

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_want.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.explore.adapter.WantAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantUserBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WantActivityGoBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.WantActivityGoPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.WantActivityGoView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class WantActivityGoActivity:BaseActivity(),WantActivityGoView {
    private var adapter: WantAdapter?=null
    private val presenter by lazy { WantActivityGoPresenter(this,this,this) }
    private var pageIndex=1
    private val pageSize=10

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_want

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        pageIndex=1
        presenter.getWantActivityGo(WantActivityGoBody(intent.getStringExtra("id"),pageIndex.toString(),pageSize.toString()))
        tv_title.text=intent.getStringExtra("num")+"人想去"
    }

    override fun clickListener() {

        Click.viewClick(iv_back).subscribe {
            finish()
        }

        swip.setOnRefreshListener {
            pageIndex=1
            presenter.getWantActivityGo(WantActivityGoBody(intent.getStringExtra("id"),pageIndex.toString(),pageSize.toString()))
        }

    }
    override fun getWantActivityGoRequest(data: WantUserBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = WantAdapter(data.data)
            var manager = LinearLayoutManager(this)
            manager.orientation = LinearLayout.VERTICAL
            recy_want.layoutManager = manager
            recy_want.adapter = adapter
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                presenter.getWantActivityGo(WantActivityGoBody(intent.getStringExtra("id"),pageIndex.toString(),pageSize.toString()))
            }
        },recy_want)

        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<pageSize){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentBusinessHomePage(adapter!!.data[position].id.toString())
        }
    }
}