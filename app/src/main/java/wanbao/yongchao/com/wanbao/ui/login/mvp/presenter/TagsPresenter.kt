package wanbao.yongchao.com.wanbao.ui.login.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.data.TagsData
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.TagsView
import java.util.ArrayList

class TagsPresenter(owner: LifecycleOwner, val view: TagsView, val mContext: Context) : BasePresenter(owner, view, mContext),TagsData.Tags{

    private val tags=TagsData(this)

    fun getTags(){
        tags.getTags()
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        tags.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    override fun getTagsRequest(data: TagsBean) {
        view.getTagsRequest(data)
    }

    override fun getTagsError() {

    }
}