package wanbao.yongchao.com.wanbao.ui.news.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.DelNoticeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeCommentBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelNoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.DelNoticeData
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.NoticeCommentData
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NoticeCommentView
import java.util.ArrayList

class NoticeCommentPresenter(owner: LifecycleOwner, val view: NoticeCommentView, val mContext: Context) : BasePresenter(owner, view, mContext), NoticeCommentData.NoticeComment, DelNoticeData.DelNotice {

    private val data=NoticeCommentData(this)
    fun getNoticeComment(body: NoticeBody){
        data.getNoticeComment(body)
    }

    override fun getNoticeCommentRequest(data: NoticeCommentBean) {
        view.NoticeCommentRequest(data)
    }

    override fun getNoticeCommentError() {

    }

    val del= DelNoticeData(this)
    fun delNotice(body: DelNoticeBody){
        del.getDelNotice(body)
    }

    override fun getDelNoticeRequest(data: DelNoticeBean) {
        view.getDelNoticeRequset()
    }

    override fun getDelNoticeError() {

    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
        del.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }
}