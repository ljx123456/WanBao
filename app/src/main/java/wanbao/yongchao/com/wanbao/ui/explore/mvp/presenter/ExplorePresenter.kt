package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreBannerBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreLandMarkBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreActivityBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreBannerBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreLandMarkBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreShopBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ExploreActivityData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ExploreBannerData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ExploreLandMarkData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ExploreShopData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ExploreView
import java.util.ArrayList

class ExplorePresenter(owner: LifecycleOwner, val view: ExploreView, val mContext: Context) : BasePresenter(owner, view, mContext),ExploreBannerData.ExploreBanner
        ,ExploreLandMarkData.ExploreLandMark, ExploreShopData.ExploreShop{

    val shop=ExploreShopData(this)
    fun getExploreShop(body:ExploreShopBody){
        shop.getExploreShop(body)
    }

    override fun getExploreShopRequest(data: ExploreShopBean) {
        view.getExploreShopRequest(data)
    }

    override fun getExploreShopError() {
        view.getExploreShopError()
    }

    val land=ExploreLandMarkData(this)
    fun getExploreLandMark(body:ExploreLandMarkBody){
        land.getExploreLandMark(body)
    }

    override fun getExploreLandMarkRequest(data: ExploreLandMarkBean) {
        view.getExploreLandMarkRequest(data)
    }

    override fun getExploreLandMarkError() {
        view.getExploreLandMarkError()
    }

    val data=ExploreBannerData(this)
    fun getExploreBanner(body:ExploreBannerBody){
        data.getExploreBanner(body)
    }

    override fun getExploreBannerRequest(data: ExploreBannerBean) {
        view.getExploreBannerRequest(data)
    }

    override fun getExploreBannerError() {
        view.getExploreBannerError()
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {

    }

    override fun presenterDestroy() {

    }
}