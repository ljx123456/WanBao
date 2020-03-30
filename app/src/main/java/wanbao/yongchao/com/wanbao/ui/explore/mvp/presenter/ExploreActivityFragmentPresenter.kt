package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreActivityBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ExploreActivityData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ExploreActivityFragmentView
import java.util.ArrayList

class ExploreActivityFragmentPresenter(owner: LifecycleOwner, val view: ExploreActivityFragmentView, val mContext: Context) : BasePresenter(owner, view, mContext),ExploreActivityData.ExploreActivity {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        activity.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val activity=ExploreActivityData(this)
    fun getExploreActivity(body: ExploreActivityBody){
        activity.getExploreActivity(body)
    }

    override fun getExploreActivityRequest(data: ExploreActivityBean) {
        view.getExploreActivityRequest(data)
    }

    override fun getExploreActivityError() {

    }
}