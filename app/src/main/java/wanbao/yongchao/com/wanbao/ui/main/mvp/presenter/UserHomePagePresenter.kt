package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.UserHomePageBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UserHomePageBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UserHomePageForNameBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.FocusData
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.UnFocusData
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.UserHomePageData
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.UserHomePageForNameData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.UserHomePageView
import java.util.ArrayList

class UserHomePagePresenter(owner: LifecycleOwner, val view: UserHomePageView, val mContext: Context) : BasePresenter(owner, view, mContext), UserHomePageData.UserHomePage,
        UserHomePageForNameData.UserHomePageForName, FocusData.Focus, UnFocusData.UnFocus  {

    val un=UnFocusData(this)
    fun getUnFocus(body: UnFocusBody){
        view.showLoading(mContext)
        un.getUnFocus(body)
    }

    override fun getUnFocusRequest(data: CommunityDelBean) {
        view.dismissLoading(mContext)
        view.getUnFocusRequest()
    }

    override fun getUnFocusError() {
        view.dismissLoading(mContext)
    }


    val f=FocusData(this)
    fun getFocus(body: FocusBody){
        view.showLoading(mContext)
        f.getFocus(body)
    }

    override fun getFocusRequest(data: CommunityDelBean) {
        view.dismissLoading(mContext)
        view.getFocusRequest()
    }

    override fun getFocusError() {
        view.dismissLoading(mContext)
    }

    val name=UserHomePageForNameData(this)
    fun getUserHomePageForName(body:UserHomePageForNameBody){
        view.showLoading(mContext)
        name.getUserHomePage(body)
    }

    override fun getUserHomePageForNameRequest(data: UserHomePageBean) {
        view.dismissLoading(mContext)
        view.getUserHomePageRequest(data)
    }

    override fun getUserHomePageForNameError() {
        view.dismissLoading(mContext)
        view.getUserHomePageError()
    }

    val data=UserHomePageData(this)
    fun getUserHomePage(body:UserHomePageBody){
        view.showLoading(mContext)
        data.getUserHomePage(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
        name.getDisposable()?.let { list.add(it) }
        f.getDisposable()?.let { list.add(it) }
        un.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getUserHomePageRequest(data: UserHomePageBean) {
        view.dismissLoading(mContext)
        view.getUserHomePageRequest(data)
    }

    override fun getUserHomePageError() {
        view.dismissLoading(mContext)
        view.getUserHomePageError()
    }
}