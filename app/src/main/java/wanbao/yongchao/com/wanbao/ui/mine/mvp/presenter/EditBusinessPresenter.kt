package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditBusinessBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditBusinessBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.EditBusinessData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditBusinessView
import java.util.ArrayList

class EditBusinessPresenter(owner: LifecycleOwner, val view: EditBusinessView, val mContext: Context) : BasePresenter(owner, view, mContext), EditBusinessData.EditBusiness {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        edit.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val edit=EditBusinessData(this)
    fun setEditBusiness(body: EditBusinessBody){
        view.showLoading(mContext)
        edit.getEditBusiness(body)
    }

    override fun getEditBusinessRequest(data: EditBusinessBean) {
        view.dismissLoading(mContext)
        view.getEditBusinessRequest(data)
    }

    override fun getEditBusinessError() {
        view.dismissLoading(mContext)
    }
}