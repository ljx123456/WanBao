package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchLandMarkBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.SearchLandMarkData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.SearchLandMarkView
import java.util.ArrayList

class SearchLandMarkPresenter(owner: LifecycleOwner, val view: SearchLandMarkView, val mContext: Context) : BasePresenter(owner, view, mContext), SearchLandMarkData.SearchLandMark {

    val data=SearchLandMarkData(this)
    fun getSearchLandMark(body:SearchBody){
        data.getSearchLandMark(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getSearchLandMarkRequest(data: SearchLandMarkBean) {
        view.getSearchLandMark(data)
    }

    override fun getSearchLandMarkError() {

    }
}