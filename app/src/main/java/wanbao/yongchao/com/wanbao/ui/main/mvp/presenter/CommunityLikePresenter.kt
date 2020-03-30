package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityLikeBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityLikeBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.CommunityLikeData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunityLikeView
import java.util.ArrayList

class CommunityLikePresenter(owner: LifecycleOwner, val view: CommunityLikeView, val mContext: Context) : BasePresenter(owner, view, mContext), CommunityLikeData.CommunityLike {


    val data=CommunityLikeData(this)
    fun getCommunityLike(body:CommunityLikeBody){
        data.getCommunityLike(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getCommunityLikeRequest(data: CommunityLikeBean) {
        view.getCommunityLikeRequest(data)
    }

    override fun getCommunityLikeError() {
        view.getCommunityLikeError()
    }
}