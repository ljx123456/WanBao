package wanbao.yongchao.com.wanbao.ui.set.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.ChangePhoneBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.ChangePhoneBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.data.ChangePhoneData
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.ChangePhoneView
import java.util.ArrayList

class ChangePhonePresenter(owner: LifecycleOwner, val view: ChangePhoneView, val mContext: Context) : BasePresenter(owner, view, mContext), ChangePhoneData.ChangePhone {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=ChangePhoneData(this)
    fun getChangePhone(body:ChangePhoneBody){
        view.showLoading(mContext)
        data.getChangePhone(body)
    }

    override fun getChangePhoneRequest(data: ChangePhoneBean) {
        view.dismissLoading(mContext)
        view.getChangePhoneRequest(data)
    }

    override fun getChangePhoneError() {
        view.dismissLoading(mContext)
    }
}