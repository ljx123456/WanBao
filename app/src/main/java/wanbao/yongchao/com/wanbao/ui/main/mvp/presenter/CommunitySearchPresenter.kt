package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.HotTopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.HotTopicData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunitySearchView
import java.util.ArrayList

class CommunitySearchPresenter(owner: LifecycleOwner, val view: CommunitySearchView, val mContext: Context) : BasePresenter(owner, view, mContext),HotTopicData.HotTopic {

    val data=HotTopicData(this)
    fun getHotTopic(){
        data.getHotTopic()
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getHotTopicRequest(data: HotTopicBean) {
        view.getHotTopicRequest(data)
    }

    override fun getHotTopicError() {

    }
}