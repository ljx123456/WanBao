package wanbao.yongchao.com.wanbao.ui.news.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.DelNoticeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeAtBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelNoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.DelNoticeData
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.NoticeAtData
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NoticeAtView
import java.util.ArrayList

class NoticeAtPresenter(owner: LifecycleOwner, val view: NoticeAtView, val mContext: Context) : BasePresenter(owner, view, mContext), NoticeAtData.NoticeAt, DelNoticeData.DelNotice {

    private val data=NoticeAtData(this)
    fun getNoticeAt(body: NoticeBody){
        data.getNoticeAt(body)
    }

    override fun getNoticeAtRequest(data: NoticeAtBean) {
        view.NoticeAtRequest(data)
    }

    override fun getNoticeAtError() {

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