package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.ReportBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.ReportData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.ReportDetailsDialogView
import java.util.ArrayList

class ReportDetailsDialogPresenter(owner: LifecycleOwner, val view: ReportDetailsDialogView, val mContext: Context) : BasePresenter(owner, view, mContext),ReportData.Report {

    val data=ReportData(this)
    fun getReport(body:ReportBody){
        view.showLoading(mContext)
        data.getReport(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getReportRequest(data: CommunityDelBean) {
        view.dismissLoading(mContext)
        view.getReportRequest()
    }

    override fun getReportError() {
        view.dismissLoading(mContext)
    }
}