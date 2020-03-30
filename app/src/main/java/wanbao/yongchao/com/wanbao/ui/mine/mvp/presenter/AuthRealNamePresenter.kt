package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthRealNameBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthRealNameBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.AuthRealNameData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.AuthRealNameView
import java.util.ArrayList

class AuthRealNamePresenter(owner: LifecycleOwner, val view: AuthRealNameView, val mContext: Context) : BasePresenter(owner, view, mContext),AuthRealNameData.AuthRealName{
    override fun addDisposableList(list: ArrayList<Disposable>) {
        auth.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val auth=AuthRealNameData(this)
    fun getAuthRealName(body:AuthRealNameBody){
        view.showLoading(mContext)
        auth.getAuthRealName(body)
    }

    override fun getAuthRealNameRequest(data: AuthRealNameBean) {
        view.dismissLoading(mContext)
        view.getAuthRealNameRequest(data)
    }

    override fun getAuthRealNameError() {
        view.dismissLoading(mContext)
    }
}