package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseContext
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.ui.main.activity.BusinessHomePageActivity
import wanbao.yongchao.com.wanbao.ui.main.adapter.FansAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FansBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.AtFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AtFragmentView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

@SuppressLint("ValidFragment")
class FansFragment(val id:String,val title:String): BaseFragment(),AtFragmentView{
    override fun getFansRequest(data: FansBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = FansAdapter(data.data)
            var manager = LinearLayoutManager(activity!!)
            manager.orientation = LinearLayout.VERTICAL
            recy_search_user.layoutManager = manager
            recy_search_user.adapter = adapter
            if (title=="粉丝") {
                adapter!!.setEmptyView(layoutUtils.getNoneFans())
            }else{
                adapter!!.setEmptyView(layoutUtils.getNoneFocus())
            }
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
                if (title=="关注"){
                    presenter!!.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
                }else{
                    presenter!!.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
                }
            }
        },recy_search_user)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
//            if (adapter!!.data[position].role==1){//用户
//                intentUtils.intentUserHomePage(adapter!!.data[position].id.toString())
//            }else{
//                intentUtils.intentBusinessHomePage(adapter!!.data[position].id.toString())
//            }
            var intent=Intent(BaseContext.getContext(), BusinessHomePageActivity::class.java)
            intent.putExtra("id", adapter!!.data[position].id)
            startActivityForResult(intent,100)
        }

        swip.setOnRefreshListener {
            pageIndex=1
            if (title=="关注"){
                presenter!!.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
            }else{
                presenter!!.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
            }
        }




    }


    private var presenter :AtFragmentPresenter?=null
    private var pageIndex=1
    private var pageSize=10
    private var adapter:FansAdapter?=null

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        presenter=AtFragmentPresenter(this,this,activity!!)
        pageIndex=1
        if (title=="关注"){
            presenter!!.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
        }else{
            presenter!!.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
        }



    }

    override fun setFragmentListener() {

    }

    override fun layoutID(): Int = R.layout.fragment_search


    fun setRefresh() {
        if (presenter!=null) {
            pageIndex = 1
            if (title == "关注") {
                presenter!!.getFans(FansBody(id, "1", pageIndex.toString(), pageSize.toString()))
            } else {
                presenter!!.getFans(FansBody(id, "2", pageIndex.toString(), pageSize.toString()))
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser&&presenter!=null){
            pageIndex=1
            if (title=="关注"){
                presenter!!.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
            }else{
                presenter!!.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
            }
        }
    }




}