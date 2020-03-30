package wanbao.yongchao.com.wanbao.ui.login.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.LoginThirdBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.LoginThirdBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.data.LoginThirdData
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.LoginThirdView
import java.util.ArrayList

class LoginThirdPresenter(owner: LifecycleOwner, val view: LoginThirdView, val mContext: Context) : BasePresenter(owner, view, mContext),LoginThirdData.LoginThird {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=LoginThirdData(this)
    fun getLoginThird(body:LoginThirdBody){
        view.showLoading(mContext)
        data.getLoginThird(body)
    }

    override fun getLoginThirdRequest(data: LoginThirdBean) {
        view.dismissLoading(mContext)
        view.getLoginThirdRequest(data)
    }

    override fun getLoginThirdError() {
        view.dismissLoading(mContext)
    }
}