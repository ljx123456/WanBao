package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.SearchUserData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.SearchUserView
import java.util.ArrayList

class SearchUserPresenter(owner: LifecycleOwner, val view: SearchUserView, val mContext: Context) : BasePresenter(owner, view, mContext),SearchUserData.SearchUser {

    val user=SearchUserData(this)
    fun getSearchUser(body:SearchBody){
        user.getSearchUser(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        user.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getSearchUserRequest(data: FansBean) {
        view.getSeaarchUserRequest(data)
    }

    override fun getSearchUserError() {

    }
}