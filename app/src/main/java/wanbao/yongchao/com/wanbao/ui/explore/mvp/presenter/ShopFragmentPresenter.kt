package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreShopBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ExploreShopData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ShopFragmentView
import java.util.ArrayList

class ShopFragmentPresenter(owner: LifecycleOwner, val view: ShopFragmentView, val mContext: Context) : BasePresenter(owner, view, mContext), ExploreShopData.ExploreShop {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=ExploreShopData(this)
    fun getShop(body:ExploreShopBody){
        data.getExploreShop(body)
    }

    override fun getExploreShopRequest(data: ExploreShopBean) {
        view.getShopRequest(data)
    }

    override fun getExploreShopError() {

    }
}