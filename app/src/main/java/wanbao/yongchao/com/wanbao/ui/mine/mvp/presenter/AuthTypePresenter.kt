package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthTypeBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthTypeBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.AuthTypeData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.AuthTypeView
import java.util.ArrayList

class AuthTypePresenter(owner: LifecycleOwner, val view: AuthTypeView, val mContext: Context) : BasePresenter(owner, view, mContext), AuthTypeData.AuthType {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=AuthTypeData(this)
    fun getAuthType(body:AuthTypeBody){
        view.showLoading(mContext)
        data.getAuthType(body)
    }

    override fun getAuthTypeRequest(data: AuthTypeBean) {
        view.dismissLoading(mContext)
        view.getAuthTypeRequest(data)
    }

    override fun getAuthTypeError() {
        view.dismissLoading(mContext)
    }
}