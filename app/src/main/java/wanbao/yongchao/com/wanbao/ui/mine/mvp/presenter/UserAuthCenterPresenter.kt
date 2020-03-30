package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.AuthInfoData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.UserAuthCenterView
import java.util.ArrayList

class UserAuthCenterPresenter(owner: LifecycleOwner, val view: UserAuthCenterView, val mContext: Context) : BasePresenter(owner, view, mContext),AuthInfoData.AuthInfo {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        info.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val info=AuthInfoData(this)
    fun getAuthInfo(body:AuthInfoBody){
        view.showLoading(mContext)
        info.getAuthInfo(body)
    }

    override fun getAuthInfoRequest(data: AuthInfoBean) {
        view.dismissLoading(mContext)
        view.getAuthInfoRequest(data)
    }

    override fun getAuthInfoError() {
        view.dismissLoading(mContext)
    }
}