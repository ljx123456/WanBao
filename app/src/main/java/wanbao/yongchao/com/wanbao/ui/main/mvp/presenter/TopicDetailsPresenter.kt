package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.CommunityHotData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.TopicDetailsView
import java.util.ArrayList

class TopicDetailsPresenter(owner: LifecycleOwner, val view: TopicDetailsView, val mContext: Context) : BasePresenter(owner, view, mContext),CommunityHotData.CommunityHot {

    val data=CommunityHotData(this)
    fun getTopicDetails(body:CommunityBody){
        view.showLoading(mContext)
        data.getCommunityHot(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getCommunityHotRequest(data: CommunityBean) {
        view.dismissLoading(mContext)
        view.getTopicDetailRequest(data)
    }

    override fun getCommunityHotError() {

    }
}