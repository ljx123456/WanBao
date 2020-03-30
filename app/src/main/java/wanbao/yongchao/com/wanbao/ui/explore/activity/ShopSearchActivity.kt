package wanbao.yongchao.com.wanbao.ui.explore.activity

import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_shop_search.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.adapter.SearchHotShopAdapter
import wanbao.yongchao.com.wanbao.ui.explore.adapter.ShopAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreShopBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.SearchHotShopBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.SearchShopPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.SearchShopView
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class ShopSearchActivity:BaseActivity(),SearchShopView{
    override fun getSearchHotShopRequest(data: SearchHotShopBean) {
        if (data.data.size>0){
            layout_hot_search.visibility=View.VISIBLE

//            if (adapter!=null){
//                adapter!!.addData(data.data)
//            }else {
                adapter = SearchHotShopAdapter(data.data)
                var manager = LinearLayoutManager(this)
                manager.orientation = LinearLayout.VERTICAL
                recy_hot_shop.layoutManager = manager
                recy_hot_shop.adapter = adapter
//            }

            adapter!!.setOnItemClickListener { mAdapter, view, position ->
                intentUtils.intentBusinessHomePage(adapter!!.data[position].businessId.toString())
            }
        }else{
//            if (hotPageIndex==1) {
                layout_hot_search.visibility = View.GONE
//            }
        }
    }

    override fun getSearchShopRequest(data: ExploreShopBean) {
        recy_shop.visibility=View.VISIBLE
        if (searchAdapter!=null){
            if (pageIndex==1) {
                searchAdapter!!.setNewData(data.data)
            }else{
                searchAdapter!!.addData(data.data)
            }
        }else{
            searchAdapter= ShopAdapter(data.data)
            var manager=LinearLayoutManager(this)
            manager.orientation=LinearLayout.VERTICAL
            recy_shop.layoutManager=manager
            recy_shop.adapter=searchAdapter
        }

        searchAdapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<pageSize){
            searchAdapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            searchAdapter!!.loadMoreComplete()
        }

        searchAdapter!!.setOnLoadMoreListener(object :BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                var body=ExploreShopBody()
                body.sort=1
                body.type=0
                body.lat= user.getLocationLat()
                body.lng= user.getLocationLng()
                body.cityId=user.getCityID()
                body.like=search
                body.pageIndex=pageIndex
                body.pageSize=pageSize
                presenter.getSearchShop(body)
            }
        },recy_shop)

        searchAdapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentBusinessHomePage(searchAdapter!!.data[position].businessId.toString())
        }
    }

    private var historyList=ArrayList<String>()
    private var adapter: SearchHotShopAdapter?=null
    var searchAdapter: ShopAdapter?=null
    private val presenter by lazy { SearchShopPresenter(this,this,this) }
    private var pageIndex=1
    private var pageSize=10
    private var search=""


    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_shop_search

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

        var mCache = ACache.get(this)
        mCache.getAsString("HistorySearchShop")
        if (mCache.getAsString("HistorySearchShop") != null && !"".equals(mCache.getAsString("HistorySearchShop"))) {
            historyList = Gson().fromJson(mCache.getAsString("HistorySearchShop"), ArrayList<String>()::class.java)
            if(historyList.size>0){
                layout_history.visibility= View.VISIBLE
                history.setList(historyList)
            }else{
                layout_history.visibility= View.GONE
            }
        }else{
            layout_history.visibility= View.GONE
        }

        presenter.getHotShop(SearchHotShopBody(user.getCityID().toInt()))


    }

    override fun clickListener() {

        Click.viewClick(tv_cancel).subscribe {
            finish()
        }

        history.setOnItemClickListener { position, text ->
            layout_history.visibility= View.GONE
            layout_hot_search.visibility=View.GONE
            edit_search.setText(text)
//            getSearchList()
            search=text
            historyList.add(search)
            pageIndex=1
            var body=ExploreShopBody()
            body.sort=1
            body.type=0
            body.lat= user.getLocationLat()
            body.lng= user.getLocationLng()
            body.cityId=user.getCityID()
            body.like=search
            body.pageIndex=pageIndex
            body.pageSize=pageSize
            presenter.getSearchShop(body)
        }

        edit_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId== EditorInfo.IME_ACTION_SEARCH){
                if (edit_search.text!=null&&edit_search.text.toString().isNotEmpty()){
                    layout_history.visibility=View.GONE
                    layout_hot_search.visibility=View.GONE
                    search=edit_search.text.toString()
                    historyList.add(search)
                    pageIndex=1
                    var body=ExploreShopBody()
                    body.sort=1
                    body.type=0
                    body.lat= user.getLocationLat()
                    body.lng= user.getLocationLng()
                    body.cityId=user.getCityID()
                    body.like=search
                    body.pageIndex=pageIndex
                    body.pageSize=pageSize
                    presenter.getSearchShop(body)
                }else{
                    Toast.Tips("请输入搜索内容")
                }
            }
            return@setOnEditorActionListener false
        }

        Click.viewClick(iv_delete).subscribe {
            ShowDialog.showCustomDialogNoTitle(this,"继续操作将清空历史搜索记录","清空","取消",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            dialog.dismiss()
                            historyList.clear()
                            var mCache = ACache.get(this@ShopSearchActivity)
                            mCache.put("HistorySearchShop","")
                            layout_history.visibility=View.GONE
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
            })

        }

//        Click.viewClick()

    }



    override fun onDestroy() {
        super.onDestroy()
        if (historyList.size>0) {
            var mCache = ACache.get(this)
            mCache.put("HistorySearchShop", Gson().toJson(historyList))
        }
    }
}