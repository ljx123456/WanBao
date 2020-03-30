package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityDelBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.LikeBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.*
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.LikeCommunityBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.LikeCommunityData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.LikeCommunityView
import java.util.ArrayList

class LikeCommunityPresenter(owner: LifecycleOwner, val view: LikeCommunityView, val mContext: Context) : BasePresenter(owner, view, mContext), LikeCommunityData.LikeCommunity, CommunityDelData.CommunityDel, FocusData.Focus, UnFocusData.UnFocus , LikeData.Like, CollectData.Collect{

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

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
        del.getDisposable()?.let { list.add(it) }
        f.getDisposable()?.let { list.add(it) }
        un.getDisposable()?.let { list.add(it) }
        like.getDisposable()?.let { list.add(it) }
        collect.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=LikeCommunityData(this)
    fun getLikeCommunity(body:LikeCommunityBody){
        data.getLikeCommunity(body)
    }

    override fun getLikeCommunityRequest(data: CommunityBean) {
        view.getHomePageCommunityRequest(data)
    }

    override fun getLikeCommunityError() {

    }
}