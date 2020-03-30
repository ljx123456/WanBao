package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.SearchFansData
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.SearchUserData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AtDialogView
import java.util.ArrayList

class AtDialogPresenter(owner: LifecycleOwner, val view: AtDialogView, val mContext: Context) : BasePresenter(owner, view, mContext),SearchUserData.SearchUser,SearchFansData.SearchFans {

    val fans=SearchFansData(this)
    fun getSearchFans(body: SearchBody){
        view.showLoading(mContext)
        fans.getSearchFans(body)
    }

    override fun getSearchFansRequest(data: FansBean) {
        view.dismissLoading(mContext)
        view.getSearchFansRequest(data)
    }

    override fun getSearchFansError() {

    }

    val data=SearchUserData(this)
    fun getSearchUser(body:SearchBody){
//        view.showLoading(mContext)
        data.getSearchUser(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
        fans.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getSearchUserRequest(data: FansBean) {
//        view.dismissLoading(mContext)
        view.getSearchUserRequest(data)
    }

    override fun getSearchUserError() {

    }
}