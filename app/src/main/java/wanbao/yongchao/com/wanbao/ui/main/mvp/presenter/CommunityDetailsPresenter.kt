package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDetailsBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunityDetailsView
import java.util.ArrayList

class CommunityDetailsPresenter(owner: LifecycleOwner, val view: CommunityDetailsView, val mContext: Context) : BasePresenter(owner, view, mContext),CommunityDetailsData.CommunityDetails
        , CommunityDelData.CommunityDel, FocusData.Focus, UnFocusData.UnFocus , LikeData.Like, CollectData.Collect{

    val collect= CollectData(this)
    fun getCollect(body: LikeBody){
        view.showLoading(mContext)
        collect.getLike(body)
    }
    override fun getCollectRequest(data: CommunityDelBean) {
        view.dismissLoading(mContext)
        view.getCollectRequest()
    }

    override fun getCollectError() {
        view.dismissLoading(mContext)

    }

    val like= LikeData(this)
    fun getLike(body: LikeBody){
        like.getLike(body)
    }

    override fun getLikeRequest(data: CommunityDelBean) {
        view.getLikeRequest()
    }

    override fun getLikeError() {

    }

    val un= UnFocusData(this)
    fun getUnFocus(body: UnFocusBody){
//        view.showLoading(mContext)
        un.getUnFocus(body)
    }

    override fun getUnFocusRequest(data: CommunityDelBean) {
//        view.dismissLoading(mContext)
        view.getUnFocusRequest()
    }

    override fun getUnFocusError() {
//        view.dismissLoading(mContext)
    }


    val f= FocusData(this)
    fun getFocus(body: FocusBody){
//        view.showLoading(mContext)
        f.getFocus(body)
    }

    override fun getFocusRequest(data: CommunityDelBean) {
//        view.dismissLoading(mContext)
        view.getFocusRequest()
    }

    override fun getFocusError() {
//        view.dismissLoading(mContext)
    }

    val del= CommunityDelData(this)
    fun getCommunityDel(body: CommunityDelBody){
        view.showLoading(mContext)
        del.getCommunityDel(body)
    }

    override fun getCommunityDelRequest(data: CommunityDelBean) {
        view.dismissLoading(mContext)
        view.getCommunityDelRequest()
    }

    override fun getCommunityDelError() {
        view.dismissLoading(mContext)
    }

    val details=CommunityDetailsData(this)
    fun getCommunityDetails(body:CommunityDetailsBody){
        view.showLoading(mContext)
        details.getCommunityDetails(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        details.getDisposable()?.let { list.add(it) }
        del.getDisposable()?.let { list.add(it) }
        f.getDisposable()?.let { list.add(it) }
        un.getDisposable()?.let { list.add(it) }
        like.getDisposable()?.let { list.add(it) }
        collect.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getCommunityDetailsRequest(data: CommunityDetailsBean) {
        view.dismissLoading(mContext)
        view.getCommunityDetailsRequest(data)
    }

    override fun getCommunityDetailsError() {
        view.dismissLoading(mContext)
        view.getCommunityDetailsError()
    }
}