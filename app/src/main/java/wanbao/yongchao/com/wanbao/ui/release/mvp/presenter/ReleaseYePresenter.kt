package wanbao.yongchao.com.wanbao.ui.release.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseYeBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.ReleaseYeBody
import wanbao.yongchao.com.wanbao.ui.release.mvp.data.ReleaseYeData
import wanbao.yongchao.com.wanbao.ui.release.mvp.view.ReleaseYeView
import java.util.ArrayList

class ReleaseYePresenter(owner: LifecycleOwner, val view: ReleaseYeView, val mContext: Context) : BasePresenter(owner, view, mContext),ReleaseYeData.ReleaseYe {

    val release=ReleaseYeData(this)
    fun getReleaseYe(body:ReleaseYeBody){
        view.showLoading(mContext)
        release.getReleaseYe(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        release.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getReleaseYeRequest(data: ReleaseYeBean) {
        view.dismissLoading(mContext)
        view.getReleaseYeRequest(data)
    }

    override fun getReleaseYeError() {
        view.dismissLoading(mContext)
    }
}