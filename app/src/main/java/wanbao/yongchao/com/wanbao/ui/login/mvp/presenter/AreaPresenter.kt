package wanbao.yongchao.com.wanbao.ui.login.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.AreaBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.AreaBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.data.AreaData
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.AreaView
import java.util.ArrayList

class AreaPresenter(owner: LifecycleOwner, val view: AreaView, val mContext: Context) : BasePresenter(owner, view, mContext),AreaData.Area{

    private val area=AreaData(this)

    fun getArea(body: AreaBody){
        area.getArea(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        area.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getAreaRequest(data: AreaBean) {
        view.getAreaRequest(data)
    }

    override fun getAreaError() {
        view.getAreaError()
    }
}