package wanbao.yongchao.com.wanbao.ui.set.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.ChangePhoneBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.NoticeStatusBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.ChangeNoticeStatusBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.NoticeStatusBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.data.ChangeNoticeStatusData
import wanbao.yongchao.com.wanbao.ui.set.mvp.data.NoticeStatusData
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.NoticeStatusView
import java.util.ArrayList

class NoticeStatusPresenter(owner: LifecycleOwner, val view: NoticeStatusView, val mContext: Context) : BasePresenter(owner, view, mContext), NoticeStatusData.NoticeStatus,ChangeNoticeStatusData.ChangeNoticeStatus {

    val change=ChangeNoticeStatusData(this)
    fun getChangeNoticeStatus(body:ChangeNoticeStatusBody){
        change.getChangeNoticeStatus(body)
    }

    override fun getChangeNoticeStatusRequest(data: ChangePhoneBean) {
        view.getChangeNoticeStatusRequest()
    }

    override fun getChangeNoticeStatusError() {
        view.getChangeNoticeStatusError()
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
        change.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=NoticeStatusData(this)
    fun getNoticeStatus(body:NoticeStatusBody){
        view.showLoading(mContext)
        data.getNoticeStatus(body)
    }

    override fun getNoticeStatusRequest(data: NoticeStatusBean) {
        view.dismissLoading(mContext)
        view.getNoticeStatusRequest(data)
    }

    override fun getNoticeStatusError() {
        view.dismissLoading(mContext)
    }
}