package wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ActivitiesDetailsBean

import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ActivitiesDetailsBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.data.ActivitiesDetailsData
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ActivitiesDetailsView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.LikeBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.CollectData
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.FocusData
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.UnFocusData
import java.util.ArrayList

class ActivitiesDetailsPresenter(owner: LifecycleOwner, val view: ActivitiesDetailsView, val mContext: Context) : BasePresenter(owner, view, mContext)
        , ActivitiesDetailsData.ActivitiesDetails,CollectData.Collect,FocusData.Focus,UnFocusData.UnFocus{

    val focus=FocusData(this)
    fun getFocus(body:FocusBody){
//        view.showLoading(mContext)
        focus.getFocus(body)
    }

    override fun getFocusRequest(data: CommunityDelBean) {
//        view.dismissLoading(mContext)
        view.getFocusRequest()
    }

    override fun getFocusError() {
//        view.dismissLoading(mContext)
    }

    val unfocus=UnFocusData(this)
    fun getUnFocus(body:UnFocusBody){
//        view.showLoading(mContext)
        unfocus.getUnFocus(body)
    }
    override fun getUnFocusRequest(data: CommunityDelBean) {
//        view.dismissLoading(mContext)
        view.getUnFocusRequest()
    }

    override fun getUnFocusError() {
//        view.dismissLoading(mContext)
    }

    val want=CollectData(this)
    fun getWant(body:LikeBody){
        view.showLoading(mContext)
        want.getLike(body)
    }

    override fun getCollectRequest(data: CommunityDelBean) {
        view.dismissLoading(mContext)
        view.getWantGoRequest()
    }

    override fun getCollectError() {
        view.dismissLoading(mContext)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        details.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val details=ActivitiesDetailsData(this)
    fun getActivitiesDetails(body:ActivitiesDetailsBody){
        view.showLoading(mContext)
        details.getActivitiesDetails(body)
    }

    override fun getActivitiesDetailsRequest(data: ActivitiesDetailsBean) {
        view.dismissLoading(mContext)
        view.getActivitiesDetailsRequest(data)
    }

    override fun getActivitiesDetailsError() {
        view.dismissLoading(mContext)
        view.getActivitiesDetailsError()
    }
}