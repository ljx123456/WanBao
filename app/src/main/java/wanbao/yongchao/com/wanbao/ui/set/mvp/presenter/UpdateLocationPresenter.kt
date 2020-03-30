package wanbao.yongchao.com.wanbao.ui.set.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateLocationBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UpdateLocationBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.data.UpdateLocationData
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.UpdateLocationView
import java.util.ArrayList

class UpdateLocationPresenter(owner: LifecycleOwner, val view: UpdateLocationView, val context: Context) : BasePresenter(owner, view, context), UpdateLocationData.UpdateLocation {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=UpdateLocationData(this)
    fun getUpdataLocation(body:UpdateLocationBody){
        data.getUpdateLocation(body)
    }

    override fun getUpdateLocationRequest(data: UpdateLocationBean) {
        view.getUpdateLocationRequest(data)
    }

    override fun getUpdateLocationError(code: Int, msg: String) {

    }
}