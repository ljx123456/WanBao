package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchLandMarkBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.WantActivitiesBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.WantLandMarkData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.WantLandMarkView
import java.util.ArrayList

class WantLandMarkPresenter(owner: LifecycleOwner, val view: WantLandMarkView, val mContext: Context) : BasePresenter(owner, view, mContext),WantLandMarkData.WantLandMark {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=WantLandMarkData(this)
    fun getWantLandMark(body:WantActivitiesBody){
        data.getWantLandMark(body)
    }

    override fun getWantLandMarkRequest(data: SearchLandMarkBean) {
        view.getLandMarkRequest(data)
    }

    override fun getWantLandMarkError() {

    }
}