package wanbao.yongchao.com.wanbao.ui.set.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UnbindBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UnbindBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.data.UnbindData
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.AccountView
import java.util.ArrayList

class AccountPresenter(owner: LifecycleOwner, val view: AccountView, val mContext: Context) : BasePresenter(owner, view, mContext),UnbindData.Unbind {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=UnbindData(this)
    fun getUnbind(body:UnbindBody){
        view.showLoading(mContext)
        data.getUnbind(body)
    }

    override fun getUnbindRequest(data: UnbindBean) {
        view.dismissLoading(mContext)
        view.getUnbindRequest(data)
    }

    override fun getUnbindError() {
        view.dismissLoading(mContext)
    }
}