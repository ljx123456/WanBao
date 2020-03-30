package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantUserBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WantUserBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.WantUserData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.WantUserView
import java.util.ArrayList

class WantUserPresenter(owner: LifecycleOwner, val view: WantUserView, val mContext: Context) : BasePresenter(owner, view, mContext), WantUserData.WantUser {

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=WantUserData(this)
    fun getWantUser(body:WantUserBody){
        data.getWantUser(body)
    }

    override fun getWantUserRequest(data: WantUserBean) {
        view.getWantUserRequest(data)
    }

    override fun getWantUserError() {

    }
}