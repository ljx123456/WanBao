package wanbao.yongchao.com.wanbao.ui.login.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.LoginCodeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.LoginCodeBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.data.LoginCodeData
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.LoginCodeView
import java.util.ArrayList


class LoginCodePresenter(owner: LifecycleOwner, val view: LoginCodeView, val mContext: Context) : BasePresenter(owner, view, mContext),LoginCodeData.LoginCode{

    private val login=LoginCodeData(this)

    fun getLogin(body:LoginCodeBody){
        view.showLoading(mContext)
        login.getLoginCode(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        login.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getLoginCodeRequest(data: LoginCodeBean) {
        view.dismissLoading(mContext)
        view.getLoginCodeRequest(data)
    }


    override fun getLoginCodeError() {
        view.dismissLoading(mContext)
        view.getLoginCodeError()
    }
}