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
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.adapter.SearchAddressAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchAddressBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.SearchAddressPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.SearchAddressView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

@SuppressLint("ValidFragment")
class SearchAddressFragment(val search:String):BaseFragment(),SearchAddressView , UserPermissions.MemoryReadPermissionsFace, LocationUtils.Location{
    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        LocationUtils(this).getLocation()
    }

    override fun requestPermissionsFaceError() {
        Toast.Tips("请打开定位权限")
    }

    override fun getLocationSuccess(city: String) {
        if (user.getLocationLng()!=""&&user.getLocationLat()!="") {
            presenter.getSearchAddress(SearchBody(search, "4", pageIndex.toString(), pageSize.toString(), user.getLocationLng(), user.getLocationLat()))
            swip.setOnRefreshListener {
                pageIndex=1
                presenter.getSearchAddress(SearchBody(search, "4", pageIndex.toString(), pageSize.toString(), user.getLocationLng(), user.getLocationLat()))
            }
        }
    }

    override fun getSearchAddressRequest(data: SearchAddressBean) {

        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = SearchAddressAdapter(data.data)
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
                presenter.getSearchAddress(SearchBody(search,"4",pageIndex.toString(),pageSize.toString(),user.getLocationLng(),user.getLocationLat()))
            }
        },recy_search_user)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentAddressHomePage(adapter!!.data[position].cityMapId.toString())
        }

    }

    private var adapter:SearchAddressAdapter?=null
    private var pageIndex=1
    private var pageSize=10
    private val presenter by lazy { SearchAddressPresenter(this,this,activity!!) }


    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        UserPermissions.userLocation(activity!!, this)
    }

    override fun setFragmentListener() {

    }

    override fun layoutID(): Int = R.layout.fragment_search


}