package wanbao.yongchao.com.wanbao.ui.mine.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.ui.main.adapter.FansAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FansBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.AtFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AtFragmentView
import wanbao.yongchao.com.wanbao.ui.mine.adapter.MineFansAdapter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.MineFansPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineFansView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView


@SuppressLint("ValidFragment")
class MineFanFragment(val id:String,val title:String): BaseFragment(), AtFragmentView,MineFansView {
    override fun getFocusRequest() {
        if (title=="粉丝") {
            adapter!!.data[posi].mutualFocus = 1
        }else{
            adapter!!.data[posi].isFlag=false
        }
        adapter!!.notifyDataSetChanged()
    }

    override fun getUnFocusRequest() {
        if (title=="粉丝") {
            adapter!!.data[posi].mutualFocus = 0
        }else{
            adapter!!.data[posi].mutualFocus = 0
            adapter!!.data[posi].isFlag=true
        }
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
            adapter = MineFansAdapter(data.data,title)
            var manager = LinearLayoutManager(activity!!)
            manager.orientation = LinearLayout.VERTICAL
            recy_search_user.layoutManager = manager
            recy_search_user.adapter = adapter

            if (title=="粉丝") {
                adapter!!.setEmptyView(layoutUtils.getNoneMineFans())
            }else{
                adapter!!.setEmptyView(layoutUtils.getNoneMineFocus())
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
                    presenter.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
                }else{
                    presenter.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
                }
            }
        },recy_search_user)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            if (adapter!!.data[position].role==1){//用户
                intentUtils.intentUserHomePage(adapter!!.data[position].id.toString())
            }else{
                intentUtils.intentBusinessHomePage(adapter!!.data[position].id.toString())
            }
        }


        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            posi=position
            if (title=="粉丝"){
                if (adapter!!.data[position].mutualFocus==0){
                    fansPresenter.getFocus(FocusBody(adapter!!.data[position].id.toString(),"1"))
                }else{
                    fansPresenter.getUnFocus(UnFocusBody(adapter!!.data[position].id.toString(),"1"))
                }
            }else{
                if (adapter!!.data[position].mutualFocus==1){
                    fansPresenter.getUnFocus(UnFocusBody(adapter!!.data[position].id.toString(), "1"))
                }else {
                    if (adapter!!.data[position].isFlag) {
                        fansPresenter.getFocus(FocusBody(adapter!!.data[position].id.toString(), "1"))
                    } else {
                        fansPresenter.getUnFocus(UnFocusBody(adapter!!.data[position].id.toString(), "1"))
                    }
                }
            }
        }



    }


    private val presenter by lazy { AtFragmentPresenter(this,this,activity!!) }
    private val fansPresenter by lazy { MineFansPresenter(this,this,activity!!) }
    private var pageIndex=1
    private var pageSize=10
    private var adapter: MineFansAdapter?=null
    private var posi=0
    private var isCreate=false

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        isCreate=true
        pageIndex=1
        if (title=="关注"){
            presenter.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
        }else{
            presenter.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
        }

    }

    override fun setFragmentListener() {
        swip.setOnRefreshListener {
            pageIndex=1
            if (title=="关注"){
                presenter.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
            }else{
                presenter.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
            }
        }
    }

    override fun layoutID(): Int = R.layout.fragment_search


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser&&isCreate) {
            pageIndex=1
            if (title=="关注"){
                presenter.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
            }else{
                presenter.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
            }
        }
    }

    fun setRefresh(){
        pageIndex=1
        if (title=="关注"){
            presenter.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
        }else{
            presenter.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
        }
    }

//    override fun onResume() {
//        super.onResume()
//        pageIndex=1
//        if (title=="关注"){
//            presenter.getFans(FansBody(id,"1",pageIndex.toString(),pageSize.toString()))
//        }else{
//            presenter.getFans(FansBody(id,"2",pageIndex.toString(),pageSize.toString()))
//        }
//    }

}