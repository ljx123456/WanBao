package wanbao.yongchao.com.wanbao.ui.set.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UnbindBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.SuggestBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.data.SuggestData
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.SuggestView
import java.util.ArrayList

class SuggestPresenter(owner: LifecycleOwner, val view: SuggestView, val mContext: Context) : BasePresenter(owner, view, mContext), SuggestData.Suggest {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=SuggestData(this)
    fun getSuggest(body:SuggestBody){
        view.showLoading(mContext)
        data.getSuggest(body)
    }

    override fun getSuggestRequest(data: UnbindBean) {
        view.dismissLoading(mContext)
        view.getSuggestRequest()
    }

    override fun getSuggestError() {
        view.dismissLoading(mContext)
    }
}