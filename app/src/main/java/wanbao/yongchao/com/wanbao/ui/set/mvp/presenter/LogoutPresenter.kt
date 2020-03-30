package wanbao.yongchao.com.wanbao.ui.set.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.LogoutBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.LogoutBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.data.LogoutData
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.LogoutView
import java.util.ArrayList

class LogoutPresenter(owner: LifecycleOwner, val view: LogoutView, val mContext: Context) : BasePresenter(owner, view, mContext), LogoutData.Logout{
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=LogoutData(this)
    fun getLogout(body:LogoutBody){
        view.showLoading(mContext)
        data.getLogout(body)
    }

    override fun getLogoutRequest(data: LogoutBean) {
        view.dismissLoading(mContext)
        view.getLogoutRequest(data)
    }

    override fun getLogoutError() {
        view.dismissLoading(mContext)
    }
}