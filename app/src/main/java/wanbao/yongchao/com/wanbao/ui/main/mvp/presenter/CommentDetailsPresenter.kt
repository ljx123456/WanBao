package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommentDetailsView
import java.util.ArrayList

class CommentDetailsPresenter(owner: LifecycleOwner, val view: CommentDetailsView, val mContext: Context) : BasePresenter(owner, view, mContext), CommentDetailsData.CommentDetails,
        LikeData.Like, AddReplyData.AddReply,CommentData.Comment, CommentDelData.CommentDel {

    val del=CommentDelData(this)
    fun getDelComment(body: CommentDelBody){
        view.showLoading(mContext)
        del.getCommentDel(body)
    }
    override fun getCommentDelRequest(data: CommentDelBean) {
        view.dismissLoading(mContext)
        view.getCommentDelRequest()
    }

    override fun getCommentDelError() {
        view.dismissLoading(mContext)
    }

    val details=CommentData(this)
    fun getComment(body:CommentBody){
        view.showLoading(mContext)
        details.getComment(body)
    }
    override fun getCommentRequest(data: CommentBean) {
        view.dismissLoading(mContext)
        view.getCommentRequest(data)
    }

    override fun getCommentError() {
        view.dismissLoading(mContext)
        view.getCommentError()
    }

    val add=AddReplyData(this)
    fun getAddReply(body:AddReplyBody){
        view.showLoading(mContext)
        add.getAddReply(body)
    }
    override fun getAddReplyRequest(data: AddReplyBean) {
        view.dismissLoading(mContext)
        view.getAddReplyRequest(data)
    }

    override fun getAddReplyError() {
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

    val comment=CommentDetailsData(this)
    fun getCommentDetails(body:CommentDetailsBody){
        view.showLoading(mContext)
        comment.getCommentDetails(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        comment.getDisposable()?.let { list.add(it) }
        details.getDisposable()?.let { list.add(it) }
        del.getDisposable()?.let { list.add(it) }
        add.getDisposable()?.let { list.add(it) }
        like.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getCommentDetailsRequest(data: CommentDetailsBean) {
        view.dismissLoading(mContext)
        view.getCommentDetailsRequest(data)
    }

    override fun getCommentDetailsError() {
        view.dismissLoading(mContext)
        view.getCommentDetailsError()
    }
}