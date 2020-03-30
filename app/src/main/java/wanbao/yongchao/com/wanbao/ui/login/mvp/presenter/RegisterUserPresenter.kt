package wanbao.yongchao.com.wanbao.ui.login.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.UserBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.RegisterUserBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.data.RegisterUserData
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.RegisterUserView
import java.util.ArrayList

class RegisterUserPresenter(owner: LifecycleOwner, val view: RegisterUserView, val mContext: Context) : BasePresenter(owner, view, mContext),RegisterUserData.RegisterUser{

    private val user=RegisterUserData(this)

    fun getRegisterUser(body:RegisterUserBody){
        view.showLoading(mContext)
        user.getCode(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        user.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {
    }

    override fun getRegisterUserRequest(data: UserBean) {
        view.dismissLoading(mContext)
        view.getRegisterUserRequest(data)
    }

    override fun getRegisterUserError() {
        view.dismissLoading(mContext)
        view.getRegisterUserError()
    }
}