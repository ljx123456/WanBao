package wanbao.yongchao.com.wanbao.ui.mine.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_explore_activities.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.ui.explore.adapter.ExploreActivityAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ExploreActivityFragmentView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.BusinessActivityBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.BusinessActivityPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.BusinessActivityView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils

@SuppressLint("ValidFragment")
class BusinessActivityFragment(val id:String): BaseFragment() , BusinessActivityView {
    override fun getBusinessActivityRequest(data: ExploreActivityBean) {
        if (data.data.size>0&&pageIndex!=1) {
            recy_community_comment.visibility=View.VISIBLE
            layout_none.visibility=View.GONE
            if (adapter != null) {
                if (pageIndex==1){
                    adapter!!.setNewData(data.data)
                }else {
                    adapter!!.addData(data.data)
                }
            } else {
                adapter = ExploreActivityAdapter(data.data)
                var manager = LinearLayoutManager(activity!!)
                manager.orientation = LinearLayout.VERTICAL
                recy_community_comment.layoutManager = manager
                recy_community_comment.adapter = adapter
                recy_community_comment.isNestedScrollingEnabled = false
            }
//        adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null),adapter!!.data.size-1)
            if (data.data.size < pageSize) {
                isFlag = false
                adapter!!.loadMoreEnd()
//                adapter!!.removeAllFooterView()
                if (isFirst) {
                    isFirst=false
                    adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null))
                }
            } else {
                isFlag = true
            }

            adapter!!.setOnItemClickListener { mAdapter, view, position ->
                intentUtils.intentActivitiesDetails(adapter!!.data[position].id)
            }
        }else{
//            view_a.visibility=View.VISIBLE
            if (pageIndex==1){
                recy_community_comment.visibility=View.GONE
                layout_none.visibility=View.VISIBLE
            }

            if (adapter!=null){
                adapter!!.loadMoreEnd()
//                adapter!!.removeAllFooterView()
                if (isFirst) {
                    isFirst=false
                    adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null))
                }
            }
            isFlag=false
        }
    }

    private var adapter: ExploreActivityAdapter?=null
    private val presenter by lazy { BusinessActivityPresenter (this,this,activity!!) }
    private var pageIndex=1
    private var pageSize=10
    private var isFlag=true

    private var isFirst=true

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        pageIndex=1
        var body= BusinessActivityBody()
        body.pageIndex=pageIndex
        body.pageSize=pageSize
        body.userId=id
        presenter.getBusinessActivity(body)


    }

    override fun setFragmentListener() {

    }

    override fun layoutID(): Int = R.layout.fragment_explore_activities



    fun setLoadMore(){
        if (isFlag) {
            pageIndex = pageIndex + 1
            var body= BusinessActivityBody()
            body.pageIndex=pageIndex
            body.pageSize=pageSize
            body.userId=id
            presenter.getBusinessActivity(body)
        }

    }

    fun setRefresh(){
        pageIndex=1
        var body= BusinessActivityBody()
        body.pageIndex=pageIndex
        body.pageSize=pageSize
        body.userId=id
        presenter.getBusinessActivity(body)
    }

}