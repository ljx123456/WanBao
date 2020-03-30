package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddressHomePageBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.AddressHomePageBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.AddressHomePageData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AddressHomePageView
import java.util.ArrayList

class AddressHomePagePresenter(owner: LifecycleOwner, val view: AddressHomePageView, val mContext: Context) : BasePresenter(owner, view, mContext), AddressHomePageData.AddressHomePage {

    val data=AddressHomePageData(this)
    fun getAddressHomePage(body:AddressHomePageBody){
        view.showLoading(mContext)
        data.getAddressHomePage(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getAddressHomePageRequest(data: AddressHomePageBean) {
        view.dismissLoading(mContext)
        view.getAddressHomePageRequest(data)
    }

    override fun getAddressHomePageError() {
        view.dismissLoading(mContext)
        view.getAddressHomePageError()
    }
}