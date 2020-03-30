package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.adapter.SearchActivityAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchActivityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.SearchActivityPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.SearchActivityView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

@SuppressLint("ValidFragment")
class SearchActivityFragment(val search:String):BaseFragment(),SearchActivityView{

    override fun getSearchActivityRequest(data: SearchActivityBean) {

        swip.isRefreshing=false
        if (adapter!=null){
            if (adapter!=null){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = SearchActivityAdapter(data.data)
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
                presenter.getSearchActivity(SearchBody(search, "5", pageIndex.toString(), pageSize.toString()))
            }
        },recy_search_user)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentActivitiesDetails(adapter!!.data[position].id)
        }
    }

    private var adapter:SearchActivityAdapter?=null
    private val presenter by lazy { SearchActivityPresenter(this,this,activity!!) }
    private var pageIndex=1
    private var pageSize=10


    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        presenter.getSearchActivity(SearchBody(search, "5", pageIndex.toString(), pageSize.toString()))
    }

    override fun setFragmentListener() {
        swip.setOnRefreshListener {
            pageIndex=1
            presenter.getSearchActivity(SearchBody(search, "5", pageIndex.toString(), pageSize.toString()))
        }
    }

    override fun layoutID(): Int = R.layout.fragment_search

    fun setUpdataWant(id:String){
        var avatar=""
        var use = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (use != null||business!=null) {
            var info = use.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                avatar=info[0].avatar
            }else if (inf != null && inf.size > 0) {
                avatar=inf[0].avatar
            }else{
                avatar=""
            }
        } else{
            avatar=""
        }
        if (adapter!=null&&avatar!=""){
            adapter!!.data.forEachIndexed { position, dataBean ->
                if (adapter!!.data[position].id.toString()==id){
                    if (user.getIsAddWant()==0) {
                        if (adapter!!.data[position].wantAvatars.size < 5) {
                            adapter!!.data[position].wantAvatars.add(avatar)
                        }
                        adapter!!.data[position].wantNum = adapter!!.data[position].wantNum + 1
                    }else if (user.getIsAddWant()==1){
                        if (adapter!!.data[position].wantNum<=5){
                            adapter!!.data[position].wantAvatars.remove(avatar)
                        }
                        adapter!!.data[position].wantNum = adapter!!.data[position].wantNum - 1
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