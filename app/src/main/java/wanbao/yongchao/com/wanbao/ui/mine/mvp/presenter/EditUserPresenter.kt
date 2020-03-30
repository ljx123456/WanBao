package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditUserBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditUserBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.EditUserData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditUserView
import java.util.ArrayList

class EditUserPresenter(owner: LifecycleOwner, val view: EditUserView, val mContext: Context) : BasePresenter(owner, view, mContext),EditUserData.EditUser {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        edit.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val edit=EditUserData(this)
    fun setEditUser(body:EditUserBody){
        view.showLoading(mContext)
        edit.getEditUser(body)
    }

    override fun getEditUserRequest(data: EditUserBean) {
        view.dismissLoading(mContext)
        view.getEditUserRequest(data)
    }

    override fun getEditUserError() {
        view.dismissLoading(mContext)
    }
}