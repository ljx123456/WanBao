package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotActivitiesBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreActivityBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.SearchHotActivitiesBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ExploreActivityData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.SearchHotActivitiesData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.SearchActivitiesView
import java.util.ArrayList

class SearchActivitiesPresenter(owner: LifecycleOwner, val view: SearchActivitiesView, val mContext: Context) : BasePresenter(owner, view, mContext),SearchHotActivitiesData.SearchHotActivities,ExploreActivityData.ExploreActivity {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        hot.getDisposable()?.let { list.add(it) }
        search.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val hot=SearchHotActivitiesData(this)
    fun getSearchHotActivities(body:SearchHotActivitiesBody){
        view.showLoading(mContext)
        hot.getSearchHotActivities(body)
    }

    override fun getSearchHotActivitiesRequest(data: SearchHotActivitiesBean) {
        view.dismissLoading(mContext)
        view.getSearchHotActivitiesRequest(data)
    }

    override fun getSearchHotActivitiesError() {
        view.dismissLoading(mContext)
    }

    val search=ExploreActivityData(this)
    fun getSearchActivities(body:ExploreActivityBody){
        search.getExploreActivity(body)
    }

    override fun getExploreActivityRequest(data: ExploreActivityBean) {
        view.getSearchActivitiesRequest(data)
    }

    override fun getExploreActivityError() {

    }
}