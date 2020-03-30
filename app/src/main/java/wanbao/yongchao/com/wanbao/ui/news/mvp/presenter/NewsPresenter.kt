package wanbao.yongchao.com.wanbao.ui.news.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.DelSystemBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NewsBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelSystemBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NewsBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.DelSystemNoticeData
import wanbao.yongchao.com.wanbao.ui.news.mvp.data.NewsData
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NewsView
import java.util.ArrayList

class NewsPresenter(owner: LifecycleOwner, val view: NewsView, val mContext: Context) : BasePresenter(owner, view, mContext), NewsData.News, DelSystemNoticeData.DelSystem {

    val del=DelSystemNoticeData(this)
    fun delSystem(body:DelSystemBody){
        del.getDelSystem(body)
    }

    override fun getDelSystemRequest(data: DelSystemBean) {
        view.delSystemNotice()
    }

    override fun getDelSystemError() {

    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
        del.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=NewsData(this)
    fun getNews(body:NewsBody){
        data.getNews(body)
    }

    override fun getNewsRequest(data: NewsBean) {
        view.getNewsRequest(data)
    }

    override fun getNewsError() {

    }
}