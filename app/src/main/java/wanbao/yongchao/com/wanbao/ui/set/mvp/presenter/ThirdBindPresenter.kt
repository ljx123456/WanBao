package wanbao.yongchao.com.wanbao.ui.set.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.LoginThirdBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.LogoutBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.data.ThirdBindData
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.ThirdBindView
import java.util.ArrayList

class ThirdBindPresenter(owner: LifecycleOwner, val view: ThirdBindView, val mContext: Context) : BasePresenter(owner, view, mContext), ThirdBindData.ThirdBind {

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=ThirdBindData(this)
    fun getThirdBind(body:LoginThirdBody){
        view.showLoading(mContext)
        data.getThirdBind(body)
    }

    override fun getThirdBindRequest(data: LogoutBean) {
        view.dismissLoading(mContext)
        view.getThirdBindRequest()
    }

    override fun getThirdBindError() {
        view.dismissLoading(mContext)
    }
}