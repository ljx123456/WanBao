package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.HomePageCommunityView
import java.util.ArrayList

class HomePageCommunityPresenter(owner: LifecycleOwner, val view: HomePageCommunityView, val mContext: Context) : BasePresenter(owner, view, mContext),
        HomePageCommunityData.HomePageCommunity,HomePageLocationCommunityData.HomePageLocationCommunity,CommunityHotData.CommunityHot ,
        CommunityDelData.CommunityDel, FocusData.Focus, UnFocusData.UnFocus , LikeData.Like, CollectData.Collect{

    val collect=CollectData(this)
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

    val like=LikeData(this)
    fun getLike(body: LikeBody){
        like.getLike(body)
    }

    override fun getLikeRequest(data: CommunityDelBean) {
        view.getLikeRequest()
    }

    override fun getLikeError() {

    }

    val un=UnFocusData(this)
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


    val f=FocusData(this)
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

    val del=CommunityDelData(this)
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

    val topic=CommunityHotData(this)
    fun getTopicDetails(body:CommunityBody){
        topic.getCommunityHot(body)
    }

    override fun getCommunityHotRequest(data: CommunityBean) {
        view.getHomePageCommunityRequest(data)
    }

    override fun getCommunityHotError() {

    }

    val location=HomePageLocationCommunityData(this)
    fun getHomePageLocationCommunity(body: HomePageLocationCommunityBody){
//        view.showLoading(mContext)
        location.getHomePageLocationCommunity(body)
    }

    override fun getHomePageLocationCommunityRequest(data: CommunityBean) {
//        view.dismissLoading(mContext)
        view.getHomePageCommunityRequest(data)
    }

    override fun getHomePageLocationCommunityError() {
//        view.dismissLoading(mContext)
    }

    val data=HomePageCommunityData(this)
    fun getHomePageCommunity(body:HomePageCommunityBody){
//        view.showLoading(mContext)
        data.getHomePageCommunity(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        topic.getDisposable()?.let { list.add(it) }
        del.getDisposable()?.let { list.add(it) }
        f.getDisposable()?.let { list.add(it) }
        un.getDisposable()?.let { list.add(it) }
        like.getDisposable()?.let { list.add(it) }
        collect.getDisposable()?.let { list.add(it) }
        data.getDisposable()?.let { list.add(it) }
        topic.getDisposable()?.let { list.add(it) }
        location.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getHomePageCommunityRequest(data: CommunityBean) {
//        view.dismissLoading(mContext)
        view.getHomePageCommunityRequest(data)
    }

    override fun getHomePageCommunityError() {
//        view.dismissLoading(mContext)

    }
}