package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchAddressBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.SearchAddressData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.SearchAddressView
import java.util.ArrayList

class SearchAddressPresenter(owner: LifecycleOwner, val view: SearchAddressView, val mContext: Context) : BasePresenter(owner, view, mContext), SearchAddressData.SearchAddress {

    val data=SearchAddressData(this)
    fun getSearchAddress(body: SearchBody){
        data.getSearchAddress(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getSearchAddressRequest(data: SearchAddressBean) {
        view.getSearchAddressRequest(data)
    }

    override fun getSearchAddressError() {

    }
}