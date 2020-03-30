package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WebBannerBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WebBannerBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.WebBannerData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.WebBannerView
import java.util.ArrayList

class WebBannerPresenter(owner: LifecycleOwner, val view: WebBannerView, val mContext: Context) : BasePresenter(owner, view, mContext), WebBannerData.WebBanner {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=WebBannerData(this)
    fun getWebBanner(body:WebBannerBody){
        view.showLoading(mContext)
        data.getWebBanner(body)
    }
    override fun getWebBannerRequest(data: WebBannerBean) {
        view.dismissLoading(mContext)
        view.getWebBannerRequest(data)
    }

    override fun getWebBannerError() {
        view.dismissLoading(mContext)
    }
}