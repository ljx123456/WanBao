package wanbao.yongchao.com.wanbao.ui.set.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UpdateBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.data.UpdateData
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.SplashView
import java.util.ArrayList

class SplashPresenter(owner: LifecycleOwner, val view: SplashView, val context: Context)
    : BasePresenter(owner, view, context), UpdateData.Update {

    override fun getUpdateRequest(data: UpdateBean) {
//        view.dismissLoading(context)
        view.getUpdateRequest(data)
    }

    override fun getUpdateError(code:Int,msg:String) {
        view.getUpdateError(code,msg)
    }

    private val update = UpdateData(this)

    fun getUpdata(body: UpdateBody) {
//        view.showLoading(context)
        update.getUpdate(body)
    }

    //取消网络请求
    override fun addDisposableList(list: ArrayList<Disposable>) {
        update.getDisposable()?.let { list.add(it) }
    }


    override fun presenterDestroy() {

    }
}