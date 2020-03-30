package wanbao.yongchao.com.wanbao.ui.release.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter

import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ClockAddressBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.ClockAddressBody
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.SearchClockBody
import wanbao.yongchao.com.wanbao.ui.release.mvp.data.ClockAddressData
import wanbao.yongchao.com.wanbao.ui.release.mvp.data.SearchClockData
import wanbao.yongchao.com.wanbao.ui.release.mvp.view.ReleaseAddressView
import java.util.ArrayList

class ReleaseAddressPresenter(owner: LifecycleOwner, val view: ReleaseAddressView, val mContext: Context) : BasePresenter(owner, view, mContext),ClockAddressData.ClockAddress,SearchClockData.SearchClock {

    val search=SearchClockData(this)
    fun getSearchClock(body: SearchClockBody){
        search.getSearchAddress(body)
    }

    override fun getSearchAddressRequest(data: ClockAddressBean) {
        view.getSearchAddressRequest(data)
    }

    override fun getSearchAddressError() {

    }

    val location=ClockAddressData(this)
    fun getLocationAddress(body:ClockAddressBody){
        location.getClockAddress(body)
    }

    override fun getClockAddressRequest(data: ClockAddressBean) {
        view.getReleaseAddressRequest(data)
    }

    override fun getClockAddressError() {

    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        search.getDisposable()?.let { list.add(it) }
        location.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }
}