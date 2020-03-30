package wanbao.yongchao.com.wanbao.ui.login.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.BusinessBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.data.RegisterBusinessData
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.RegisterBusinessView
import java.util.ArrayList

class RegisterBusinessPresenter(owner: LifecycleOwner, val view: RegisterBusinessView, val mContext: Context) : BasePresenter(owner, view, mContext),RegisterBusinessData.RegisterBusiness{

    private val business=RegisterBusinessData(this)

    override fun addDisposableList(list: ArrayList<Disposable>) {
        business.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    fun getRegisterBusiness(body:BusinessBody){
        view.showLoading(mContext)
        business.getRegisterBusiness(body)
    }

    override fun getRegisterBusinessRequest(data: BusinessBean) {
        view.dismissLoading(mContext)
        view.getRegisterBusinessRequest(data)
    }

    override fun getRegisterBusinessError() {
        view.dismissLoading(mContext)
        view.getRegisterBusinessError()

    }
}