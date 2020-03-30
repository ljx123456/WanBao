package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.BusinessActivityBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.BusinessActivityData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.BusinessActivityView
import java.util.ArrayList

class BusinessActivityPresenter(owner: LifecycleOwner, val view: BusinessActivityView, val mContext: Context) : BasePresenter(owner, view, mContext), BusinessActivityData.BusinessActivity{
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=BusinessActivityData(this)
    fun getBusinessActivity(body:BusinessActivityBody){
        data.getBusinessActivity(body)
    }

    override fun getBusinessActivityRequest(data: ExploreActivityBean) {
        view.getBusinessActivityRequest(data)
    }
    override fun getBusinessActivityError() {

    }
}