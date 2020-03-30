package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunityCommentView
import java.util.ArrayList

class CommunityCommentPresenter(owner: LifecycleOwner, val view: CommunityCommentView, val mContext: Context) : BasePresenter(owner, view, mContext),CommunityCommentData.CommunityComment,
        LikeData.Like,AddCommentData.AddComment ,CommentDelData.CommentDel, AddReplyData.AddReply{

    val addComment=AddReplyData(this)
    fun getAddReply(body: AddReplyBody){
        view.showLoading(mContext)
        addComment.getAddReply(body)
    }
    override fun getAddReplyRequest(data: AddReplyBean) {
        view.dismissLoading(mContext)
        view.getAddReplyRequest(data)
    }

    override fun getAddReplyError() {
        view.dismissLoading(mContext)
    }

    val del=CommentDelData(this)
    fun getDelComment(body:CommentDelBody){
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

    val add=AddCommentData(this)
    fun getAddComment(body:AddCommentBody){
        view.showLoading(mContext)
        add.getAddComment(body)
    }

    override fun getAddCommentRequest(data: AddCommentBean) {
        view.dismissLoading(mContext)
        view.getAddCommentRequest(data)
    }

    override fun getAddCommentError() {
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

    val comment=CommunityCommentData(this)
    fun getCommunityComment(body:CommunityCommentBody){
        comment.getCommunityComment(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        comment.getDisposable()?.let { list.add(it) }
        addComment.getDisposable()?.let { list.add(it) }
        del.getDisposable()?.let { list.add(it) }
        add.getDisposable()?.let { list.add(it) }
        like.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getCommunityCommentRequest(data: CommunityCommentBean) {
        view.CommunityCommentRequest(data)
    }

    override fun getCommunityCommentError() {
        view.CommmunityCommentError()
    }
}