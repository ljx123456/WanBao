package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.annotation.SuppressLint
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.ui.main.adapter.SearchUserAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.SearchUserPresenter

import wanbao.yongchao.com.wanbao.ui.main.mvp.view.SearchUserView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

@SuppressLint("ValidFragment")
class SearchUserFragment(val search:String):BaseFragment(),SearchUserView{
    override fun getSeaarchUserRequest(data: FansBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = SearchUserAdapter(data.data)
            var manager = LinearLayoutManager(activity!!)
            manager.orientation = LinearLayout.VERTICAL
            recy_search_user.layoutManager = manager
            recy_search_user.adapter = adapter
            adapter!!.setEmptyView(layoutUtils.getNoneSearch())
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
                presenter.getSearchUser(SearchBody(search,"2",pageIndex.toString(),pageSize.toString()))
            }
        },recy_search_user)
        
        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentBusinessHomePage(adapter!!.data[position].id.toString())
        }
        
    }

    private var adapter:SearchUserAdapter?=null
    private var pageIndex=1
    private var pageSize=10
    private val presenter by lazy { SearchUserPresenter(this,this,activity!!) }

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        pageIndex=1
        presenter.getSearchUser(SearchBody(search,"2",pageIndex.toString(),pageSize.toString()))
    }

    override fun setFragmentListener() {
        swip.setOnRefreshListener { 
            pageIndex=1
            presenter.getSearchUser(SearchBody(search,"2",pageIndex.toString(),pageSize.toString()))
        }
    }

    override fun layoutID(): Int = R.layout.fragment_search
    

}