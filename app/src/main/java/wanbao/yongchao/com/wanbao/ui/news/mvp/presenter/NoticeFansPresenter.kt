package wanbao.yongchao.com.wanbao.ui.news.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.DelNoticeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeFansBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelNoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.DelNoticeData
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.NoticeFansData
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NoticeFansView
import java.util.ArrayList

class NoticeFansPresenter(owner: LifecycleOwner, val view: NoticeFansView, val mContext: Context) : BasePresenter(owner, view, mContext), NoticeFansData.NoticeFans, DelNoticeData.DelNotice {

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

    val data=NoticeFansData(this)
    fun getNoticeFans(body:NoticeBody){
        data.getNoticeFans(body)
    }

    override fun getNoticeFansRequest(data: NoticeFansBean) {
        view.getNoticeFansRequest(data)
    }

    override fun getNoticeFansError() {

    }
}
