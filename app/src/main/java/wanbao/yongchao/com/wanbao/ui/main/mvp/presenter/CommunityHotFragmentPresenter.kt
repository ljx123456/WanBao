package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.TopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunityHotFragmentView
import java.util.ArrayList

class CommunityHotFragmentPresenter(owner: LifecycleOwner, val view: CommunityHotFragmentView, val mContext: Context) : BasePresenter(owner, view, mContext),TopicData.TopicList
        ,CommunityHotData.CommunityHot,CommunitySquareData.CommunitySquare,CommunityNearbyData.CommunityNearby,CommunityCityData.CommunityCity,CommunityFocusData.CommunityFocus
        , CommunityDelData.CommunityDel,FocusData.Focus,UnFocusData.UnFocus ,LikeData.Like,CollectData.Collect{

    val collect=CollectData(this)
    fun getCollect(body:LikeBody){
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
    fun getLike(body:LikeBody){
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
    fun getFocus(body:FocusBody){
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
//        view.showLoading(mContext)
        del.getCommunityDel(body)
    }

    override fun getCommunityDelRequest(data: CommunityDelBean) {
//        view.dismissLoading(mContext)
        view.getCommunityDelRequest()
    }

    override fun getCommunityDelError() {
//        view.dismissLoading(mContext)
    }

    val focus=CommunityFocusData(this)
    fun getCommunityFocus(body: CommunityBody){
        focus.getCommunityFocus(body)
    }

    override fun getCommunityFocusRequest(data: CommunityBean) {
        view.getCommunityFocusRequest(data)
    }

    override fun getCommunityFocusError() {

    }

    val city=CommunityCityData(this)
    fun getCommunityCity(body: CommunityBody){
        city.getCommunityCity(body)
    }

    override fun getCommunityCityRequest(data: CommunityBean) {
        view.getCommunityCityRequest(data)
    }

    override fun getCommunityCityError() {

    }

    val nearby=CommunityNearbyData(this)
    fun getCommunityNearby(body: CommunityBody){
        nearby.getCommunityNearby(body)
    }

    override fun getCommunityNearbyRequest(data: CommunityBean) {
        view.getCommunityNearbyRequest(data)
    }

    override fun getCommunityNearbyError() {

    }

    val square=CommunitySquareData(this)
    fun getCommunitySquare(body:CommunityBody){
        square.getCommunitySquare(body)
    }

    override fun getCommunitySquareRequest(data: CommunityBean) {
        view.getCommunitySquareRequest(data)
    }

    override fun getCommunitySquareError() {

    }

    val hot=CommunityHotData(this)
    fun getCommunityHot(body: CommunityBody){
        hot.getCommunityHot(body)
    }

    override fun getCommunityHotRequest(data: CommunityBean) {
        view.getCommunityHotRequest(data)
    }

    override fun getCommunityHotError() {

    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        hot.getDisposable()?.let { list.add(it) }
        topic.getDisposable()?.let { list.add(it) }
        square.getDisposable()?.let { list.add(it) }
        nearby.getDisposable()?.let { list.add(it) }
        city.getDisposable()?.let { list.add(it) }
        focus.getDisposable()?.let { list.add(it) }
        topic.getDisposable()?.let { list.add(it) }
        del.getDisposable()?.let { list.add(it) }
        f.getDisposable()?.let { list.add(it) }
        un.getDisposable()?.let { list.add(it) }
        like.getDisposable()?.let { list.add(it) }
        collect.getDisposable()?.let { list.add(it) }

    }

    override fun presenterDestroy() {

    }

    val topic=TopicData(this)
    fun getTopicList(body:TopicBody){
        topic.getTopic(body)
    }

    override fun getTopicRequest(data: TopicBean) {
        view.getTopicRequest(data)
    }

    override fun getTopicError() {
        view.getTopicError()
    }
}