package wanbao.yongchao.com.wanbao.ui.explore.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_activities.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.adapter.ExploreActivityAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreActivityBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.ExploreActivityFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ExploreActivityFragmentView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

@SuppressLint("ValidFragment")
class ActivityFragment(var type:String): BaseFragment() , ExploreActivityFragmentView{

    override fun getExploreActivityRequest(data: ExploreActivityBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = ExploreActivityAdapter(data.data)
            var manager = LinearLayoutManager(activity!!)
            manager.orientation = LinearLayout.VERTICAL
            recy_activities.layoutManager = manager
            recy_activities.adapter = adapter
            adapter!!.setEmptyView(layoutUtils.getNoneActivities())
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                var body= ExploreActivityBody()
                body.cityId= user.getCityID().toInt()
                body.pageIndex=pageIndex
                body.pageSize=pageSize
                body.type=sort
                presenter.getExploreActivity(body)
            }
        },recy_activities)

        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<pageSize){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }


        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentActivitiesDetails(adapter!!.data[position].id)
        }
    }


    private var adapter: ExploreActivityAdapter?=null
    private val presenter by lazy { ExploreActivityFragmentPresenter (this,this,activity!!) }
    private var pageIndex=1
    private var pageSize=10
    private var sort=""

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        when(type){
            "精选"->{
                sort="1"
            }
            "文化"->{
                sort="15"
            }
            "美食"->{
                sort="11"
            }
            "旅游"->{
                sort="12"
            }
            "娱乐"->{
                sort="13"
            }
            "生活"->{
                sort="14"
            }
        }
        pageIndex=1
        var body= ExploreActivityBody()
        body.cityId=user.getCityID().toInt()
        body.pageIndex=pageIndex
        body.pageSize=pageSize
        body.type=sort
        presenter.getExploreActivity(body)
    }

    override fun setFragmentListener() {
        swip.setOnRefreshListener {
            pageIndex=1
            var body= ExploreActivityBody()
            body.cityId=user.getCityID().toInt()
            body.pageIndex=pageIndex
            body.pageSize=pageSize
            body.type=sort
            presenter.getExploreActivity(body)
        }
    }

    override fun layoutID(): Int = R.layout.fragment_activities


}