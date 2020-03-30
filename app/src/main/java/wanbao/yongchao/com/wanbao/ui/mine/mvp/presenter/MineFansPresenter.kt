package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.FocusData
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.UnFocusData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineFansView
import java.util.ArrayList

class MineFansPresenter(owner: LifecycleOwner, val view: MineFansView, val mContext: Context) : BasePresenter(owner, view, mContext), FocusData.Focus, UnFocusData.UnFocus {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        un.getDisposable()?.let { list.add(it) }
        f.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

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
    fun getFocus(body: FocusBody){
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
}