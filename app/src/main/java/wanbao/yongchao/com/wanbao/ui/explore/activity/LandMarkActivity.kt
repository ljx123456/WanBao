package wanbao.yongchao.com.wanbao.ui.explore.activity

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_landmark.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.adapter.LandMarkAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreLandMarkBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreLandMarkBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.LandMarkPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.LandMarkView

import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class LandMarkActivity:BaseActivity(),LandMarkView{
    override fun getLandMarkRequest(data: ExploreLandMarkBean) {
        swip_landmark.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = LandMarkAdapter(data.data)
            var manager = LinearLayoutManager(this)
            manager.orientation = LinearLayout.VERTICAL
            recy_landmark.layoutManager = manager
            recy_landmark.adapter = adapter
        }
        adapter!!.setOnLoadMoreListener(object :BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                presenter.getLandMark(ExploreLandMarkBody(user.getCityID().toInt(),pageIndex,pageSize))
            }
        },recy_landmark)

        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<pageSize){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentLandMarkDetails(adapter!!.data[position].cityMapId.toString())
        }
    }

    private var adapter:LandMarkAdapter?=null
    private val presenter by lazy { LandMarkPresenter(this,this,this) }
    private var pageIndex=1
    private val pageSize=10

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_landmark

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        pageIndex=1
        presenter.getLandMark(ExploreLandMarkBody(user.getCityID().toInt(),pageIndex,pageSize))
    }

    override fun clickListener() {
        Click.viewClick(iv_back).subscribe {
            finish()
        }
        swip_landmark.setOnRefreshListener {
            pageIndex=1
            presenter.getLandMark(ExploreLandMarkBody(user.getCityID().toInt(),pageIndex,pageSize))
        }
    }

    override fun onResume() {
        super.onResume()
        if (user.getIsWant()&&user.getWantId()!=""){
            if (adapter!=null){
                adapter!!.data.forEachIndexed { position, dataBean ->
                    if (adapter!!.data[position].cityMapId.toString()==user.getWantId()){
                        if (user.getIsAddWant()==0){
                            adapter!!.data[position].wantUserNum=adapter!!.data[position].wantUserNum+1
                        }else if (user.getIsAddWant()==1){
                            adapter!!.data[position].wantUserNum=adapter!!.data[position].wantUserNum-1
                        }
                        adapter!!.notifyDataSetChanged()
                        user.setWantId("")
                        user.setIsWant(false)
                        user.setIsAddWant(-1)
                    }
                }
            }

        }
    }



}