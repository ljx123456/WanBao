package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchActivityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.SearchActivityData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.SearchActivityView
import java.util.ArrayList

class SearchActivityPresenter(owner: LifecycleOwner, val view: SearchActivityView, val mContext: Context) : BasePresenter(owner, view, mContext),SearchActivityData.SearchActivity {

    val data=SearchActivityData(this)
    fun getSearchActivity(body:SearchBody){
        data.getSearchActivity(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getSearchActivityRequest(data: SearchActivityBean) {
        view.getSearchActivityRequest(data)
    }

    override fun getSearchActivityError() {

    }
}