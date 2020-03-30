package wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.UserInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.UserInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.data.UserInfoData
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineUserView
import java.util.ArrayList

class MineUserPresenter(owner: LifecycleOwner, val view: MineUserView, val mContext: Context) : BasePresenter(owner, view, mContext),UserInfoData.UserInfo {
    override fun addDisposableList(list: ArrayList<Disposable>) {
        info.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val info=UserInfoData(this)
    fun getUserInfo(body:UserInfoBody){
//        view.showLoading(mContext)
        info.getUserInfo(body)
    }

    override fun getUserInfoRequest(data: UserInfoBean) {
//        view.dismissLoading(mContext)
        view.getUserInfoRequest(data)
    }

    override fun getUserInfoError() {
//        view.dismissLoading(mContext)
        view.getUserInfoError()
    }
}