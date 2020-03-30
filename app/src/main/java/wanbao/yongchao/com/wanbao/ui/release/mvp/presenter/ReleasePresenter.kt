package wanbao.yongchao.com.wanbao.ui.release.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.ReleaseCommunityBody
import wanbao.yongchao.com.wanbao.ui.release.mvp.data.ReleaseCommunityData
import wanbao.yongchao.com.wanbao.ui.release.mvp.view.ReleaseView
import java.util.ArrayList

class ReleasePresenter(owner: LifecycleOwner, val view: ReleaseView, val mContext: Context) : BasePresenter(owner, view, mContext),ReleaseCommunityData.ReleaseCommunity {

    val release=ReleaseCommunityData(this)
    fun getReleaseCommunity(body:ReleaseCommunityBody){
//        view.showLoading(mContext)
        release.getReleaseCommunity(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        release.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getReleaseCommunityRequest(data: ReleaseBean) {
//        view.dismissLoading(mContext)
        view.getReleaseCommunityRequest(data)
    }

    override fun getReleaseCommunityError() {
//        view.dismissLoading(mContext)
        view.getReleaseCommunityError()
    }
}