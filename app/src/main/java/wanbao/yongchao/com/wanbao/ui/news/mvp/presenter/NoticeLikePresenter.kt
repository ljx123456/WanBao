package wanbao.yongchao.com.wanbao.ui.news.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.DelNoticeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeLikeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelNoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.DelNoticeData
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.NoticeLikeData
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NoticeLikeView
import java.util.ArrayList

class NoticeLikePresenter(owner: LifecycleOwner, val view: NoticeLikeView, val mContext: Context) : BasePresenter(owner, view, mContext),NoticeLikeData.NoticeLike, DelNoticeData.DelNotice {

    private val data=NoticeLikeData(this)
    fun getNoticeLike(body:NoticeBody){
        data.getNoticeLike(body)
    }

    override fun getNoticeLikeRequest(data: NoticeLikeBean) {
        view.NoticeLikeRequest(data)
    }

    override fun getNoticeLikeError() {

    }

    val del=DelNoticeData(this)
    fun delNotice(body:DelNoticeBody){
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