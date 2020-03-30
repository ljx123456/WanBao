package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FansBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.FansData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AtFragmentView
import java.util.ArrayList

class AtFragmentPresenter(owner: LifecycleOwner, val view: AtFragmentView, val mContext: Context) : BasePresenter(owner, view, mContext),FansData.FansList {

    val data=FansData(this)
    fun getFans(body:FansBody){
        data.getFans(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getFansRequest(data: FansBean) {
        view.getFansRequest(data)
    }

    override fun getFansError() {
    }
}