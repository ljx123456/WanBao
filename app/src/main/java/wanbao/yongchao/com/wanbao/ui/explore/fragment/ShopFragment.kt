package wanbao.yongchao.com.wanbao.ui.explore.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.blankj.utilcode.util.IntentUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.adapter.ShopAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreShopBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.ShopFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ShopFragmentView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

@SuppressLint("ValidFragment")
class ShopFragment(val type:String):BaseFragment(),ShopFragmentView , UserPermissions.MemoryReadPermissionsFace, LocationUtils.Location{
    override fun getShopRequest(data: ExploreShopBean) {
        swip.isRefreshing=false
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = ShopAdapter(data.data)
            var manager = LinearLayoutManager(activity!!)
            manager.orientation = LinearLayout.VERTICAL
            recy_search_user.layoutManager = manager
            recy_search_user.adapter = adapter
        }
        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<pageSize){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnLoadMoreListener(object :BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                var body=ExploreShopBody()
                body.pageIndex=pageIndex
                body.pageSize=pageSize
                body.cityId=user.getCityID()
                body.sort=sort
                body.lng=user.getLocationLng()
                body.lat=user.getLocationLat()
                when(type){
                    "精选"->{
                        body.type=-1
                    }
                    "全部"->{
                        body.type=0
                    }
                    "美食"->{
                        body.type=11
                    }
                    "旅游"->{
                        body.type=12
                    }
                    "娱乐"->{
                        body.type=13
                    }
                    "生活"->{
                        body.type=14
                    }
                    "文化"->{
                        body.type=15
                    }
                }
                presenter.getShop(body)
            }
        },recy_search_user)


        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentBusinessHomePage(adapter!!.data[position].businessId.toString())
        }

    }

    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        LocationUtils(this).getLocationOnce()
    }

    override fun requestPermissionsFaceError() {
        Toast.Tips("请打开定位权限")
    }

    override fun getLocationSuccess(city: String) {
        var body=ExploreShopBody()
        body.pageIndex=pageIndex
        body.pageSize=pageSize
        body.cityId=user.getCityID()
        body.sort=sort
        body.lng=user.getLocationLng()
        body.lat=user.getLocationLat()
        when(type){
            "精选"->{
                body.type=-1
            }
            "全部"->{
                body.type=0
            }
            "美食"->{
                body.type=11
            }
            "旅游"->{
                body.type=12
            }
            "娱乐"->{
                body.type=13
            }
            "生活"->{
                body.type=14
            }
            "文化"->{
                body.type=15
            }
        }
        presenter.getShop(body)
    }

    private val presenter by lazy { ShopFragmentPresenter(this,this,activity!!) }
    private var adapter:ShopAdapter?=null
    private var pageIndex=1
    private var pageSize=10
    private var sort=1


    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        pageIndex=1
        sort=1
        if (user.getLocationLat()!=""){
            var body=ExploreShopBody()
            body.pageIndex=pageIndex
            body.pageSize=pageSize
            body.cityId=user.getCityID()
            body.sort=sort
            body.lng=user.getLocationLng()
            body.lat=user.getLocationLat()
            when(type){
                "精选"->{
                    body.type=-1
                }
                "全部"->{
                    body.type=0
                }
                "美食"->{
                    body.type=11
                }
                "旅游"->{
                    body.type=12
                }
                "娱乐"->{
                    body.type=13
                }
                "生活"->{
                    body.type=14
                }
                "文化"->{
                    body.type=15
                }
            }
            presenter.getShop(body)
        }else{
            UserPermissions.userLocation(activity!!,this)
        }


    }

    override fun setFragmentListener() {
        swip.setOnRefreshListener {
            pageIndex=1
            if (user.getLocationLat()!=""){
                var body=ExploreShopBody()
                body.pageIndex=pageIndex
                body.pageSize=pageSize
                body.cityId=user.getCityID()
                body.sort=sort
                body.lng=user.getLocationLng()
                body.lat=user.getLocationLat()
                when(type){
                    "精选"->{
                        body.type=-1
                    }
                    "全部"->{
                        body.type=0
                    }
                    "美食"->{
                        body.type=11
                    }
                    "旅游"->{
                        body.type=12
                    }
                    "娱乐"->{
                        body.type=13
                    }
                    "生活"->{
                        body.type=14
                    }
                    "文化"->{
                        body.type=15
                    }
                }
                presenter.getShop(body)
            }
        }
    }

    override fun layoutID(): Int = R.layout.fragment_search

    fun setSort(str:String){
        Log.e("测试",str)
        if (str=="距离最近"){
            sort=1
        }else{
            sort=2
        }
        pageIndex=1
        if (user.getLocationLat()!=""){
            var body=ExploreShopBody()
            body.pageIndex=pageIndex
            body.pageSize=pageSize
            body.cityId=user.getCityID()
            body.sort=sort
            body.lng=user.getLocationLng()
            body.lat=user.getLocationLat()
            when(type){
                "精选"->{
                    body.type=-1
                }
                "全部"->{
                    body.type=0
                }
                "美食"->{
                    body.type=11
                }
                "旅游"->{
                    body.type=12
                }
                "娱乐"->{
                    body.type=13
                }
                "生活"->{
                    body.type=14
                }
                "文化"->{
                    body.type=15
                }
            }
            presenter.getShop(body)
        }
    }
}