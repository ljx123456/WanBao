package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreShopBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.SearchHotShopBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ExploreShopData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.SearchHotShopData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.SearchShopView
import java.util.ArrayList

class SearchShopPresenter(owner: LifecycleOwner, val view: SearchShopView, val mContext: Context) : BasePresenter(owner, view, mContext),SearchHotShopData.SearchHotShop ,ExploreShopData.ExploreShop{

    val search=ExploreShopData(this)
    fun getSearchShop(body:ExploreShopBody){
        search.getExploreShop(body)
    }

    override fun getExploreShopRequest(data: ExploreShopBean) {
        view.getSearchShopRequest(data)
    }

    override fun getExploreShopError() {

    }

    val hot=SearchHotShopData(this)
    fun getHotShop(body:SearchHotShopBody){
        view.showLoading(mContext)
        hot.getSearchHotShop(body)
    }

    override fun getSearchHotShopRequest(data: SearchHotShopBean) {
        view.dismissLoading(mContext)
        view.getSearchHotShopRequest(data)
    }

    override fun getSearchHotShopError() {
        view.dismissLoading(mContext)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        hot.getDisposable()?.let { list.add(it) }
        search.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }
}