package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantGoBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantUserBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WantActivityGoBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.WantActivityGoData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.WantActivityGoView
import java.util.ArrayList

class WantActivityGoPresenter(owner: LifecycleOwner, val view: WantActivityGoView, val mContext: Context) : BasePresenter(owner, view, mContext), WantActivityGoData.WantActivityGo {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=WantActivityGoData(this)
    fun getWantActivityGo(body:WantActivityGoBody){
        data.getWantActivityGo(body)
    }

    override fun getWantActivityGoRequest(data: WantUserBean) {
        view.getWantActivityGoRequest(data)
    }

    override fun getWantActivityGoError() {

    }
}