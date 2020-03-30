package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreLandMarkBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreLandMarkBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ExploreLandMarkData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.LandMarkView
import java.util.ArrayList

class LandMarkPresenter(owner: LifecycleOwner, val view: LandMarkView, val mContext: Context) : BasePresenter(owner, view, mContext),ExploreLandMarkData.ExploreLandMark {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=ExploreLandMarkData(this)
    fun getLandMark(body:ExploreLandMarkBody){
        data.getExploreLandMark(body)
    }

    override fun getExploreLandMarkRequest(data: ExploreLandMarkBean) {
        view.getLandMarkRequest(data)
    }

    override fun getExploreLandMarkError() {

    }
}