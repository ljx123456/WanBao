package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.WantActivitiesBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.WantActivitiesData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.WantActivitiesView
import java.util.ArrayList

class WantActivitiesPresenter(owner: LifecycleOwner, val view: WantActivitiesView, val mContext: Context) : BasePresenter(owner, view, mContext),WantActivitiesData.WantActivities {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=WantActivitiesData(this)
    fun getWantActivities(body:WantActivitiesBody){
        data.getWantActivities(body)
    }

    override fun getWantActivitiesRequest(data: ExploreActivityBean) {
        view.getWantActivitiesRequest(data)
    }

    override fun getWantActivitiesError() {

    }
}