package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchTopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.SearchTopicData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.SearchTopicView
import java.util.ArrayList

class SearchTopicPresenter(owner: LifecycleOwner, val view: SearchTopicView, val mContext: Context) : BasePresenter(owner, view, mContext),SearchTopicData.SearchTopic {

    val data=SearchTopicData(this)
    fun getSearchTopic(body:SearchBody){
        data.getSearchTopic(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getSearchTopicRequest(data: SearchTopicBean) {
        view.getSearchTopicRequest(data)
    }

    override fun getSearchTopicError() {

    }
}