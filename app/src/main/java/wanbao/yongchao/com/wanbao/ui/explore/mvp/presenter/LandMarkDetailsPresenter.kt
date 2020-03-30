package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantGoBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WantGoBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.WantGoData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.LandMarkDetailsView
import java.util.ArrayList

class LandMarkDetailsPresenter(owner: LifecycleOwner, val view: LandMarkDetailsView, val mContext: Context) : BasePresenter(owner, view, mContext),WantGoData.WantGo {

    override fun addDisposableList(list: ArrayList<Disposable>) {
        want.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {
    }

    val want=WantGoData(this)
    fun getWantGo(body:WantGoBody){
        view.showLoading(mContext)
        want.getWantGo(body)
    }

    override fun getWantGoRequest(data: WantGoBean) {
        view.dismissLoading(mContext)
        view.getWantGoRequest()
    }

    override fun getWantGoError() {
        view.dismissLoading(mContext)
    }
}