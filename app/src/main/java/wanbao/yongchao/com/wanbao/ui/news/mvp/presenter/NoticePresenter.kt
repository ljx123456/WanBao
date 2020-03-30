package wanbao.yongchao.com.wanbao.ui.news.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.NoticeData
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NoticeView

import java.util.ArrayList

class NoticePresenter(owner: LifecycleOwner, val view: NoticeView, val mContext: Context) : BasePresenter(owner, view, mContext),NoticeData.Notice {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=NoticeData(this)
    fun getNotice(body:NoticeBody){
        data.getNotice(body)
    }

    override fun getNoticeRequest(data: NoticeBean) {
        view.getNoticeRequest(data)
    }

    override fun getNoticeError() {

    }
}