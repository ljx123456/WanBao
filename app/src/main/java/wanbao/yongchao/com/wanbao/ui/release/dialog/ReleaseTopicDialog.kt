package wanbao.yongchao.com.wanbao.ui.release.dialog

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.dialog_release_topic.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.HotTopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchTopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.CommunitySearchPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.SearchTopicPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunitySearchView
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.SearchTopicView
import wanbao.yongchao.com.wanbao.ui.release.adapter.ReleaseHotTopicAdapter
import wanbao.yongchao.com.wanbao.ui.release.adapter.ReleaseSearchTopicAdapter
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

@SuppressLint("ValidFragment")
class ReleaseTopicDialog(val topic:Topic):BaseDialogFragment(), CommunitySearchView, SearchTopicView {
    override fun getSearchTopicRequest(data: SearchTopicBean) {
        if (isVisible) {
            layout_hot.visibility = View.GONE
            recy_search.visibility = View.VISIBLE
            if (searchAdapter != null) {
                if (pageIndex == 1) {
                    searchAdapter!!.setNewData(data.data)
                } else {
                    searchAdapter!!.addData(data.data)
                }
            } else {
                searchAdapter = ReleaseSearchTopicAdapter(data.data)
                var manager = LinearLayoutManager(activity!!)
                manager.orientation = LinearLayout.VERTICAL
                recy_search.layoutManager = manager
                recy_search.adapter = searchAdapter
            }

            searchAdapter!!.setLoadMoreView(CustomLoadMoreView())
            if (data.data.size < pageSize) {
                searchAdapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
            } else {
                searchAdapter!!.loadMoreComplete()
            }

            searchAdapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
                override fun onLoadMoreRequested() {
                    pageIndex = pageIndex + 1
                    searchPresenter.getSearchTopic(SearchBody(search, "3", pageIndex.toString(), pageSize.toString()))
                }
            }, recy_search)

            searchAdapter!!.setOnItemClickListener { mAdapter, view, position ->
                topic.setTopic(searchAdapter!!.data[position].name, searchAdapter!!.data[position].id.toString())
                pageIndex = 1
                search = ""
                edit_search.setText("")
                dismiss()
            }
        }
    }

    override fun getHotTopicRequest(data: HotTopicBean) {
        if (isVisible) {
            if (data.data != null && data.data.size > 0) {
                layout_hot.visibility = View.VISIBLE
                recy_search.visibility = View.GONE
                var adapter = ReleaseHotTopicAdapter(data.data)
                var manager = LinearLayoutManager(activity!!)
                manager.orientation = LinearLayout.VERTICAL
                recy_hot_search.layoutManager = manager
                recy_hot_search.adapter = adapter
                adapter.setOnItemClickListener { mAdapter, view, position ->
                    topic.setTopic(adapter.data[position].name, adapter.data[position].id.toString())
                    pageIndex = 1
                    search = ""
                    edit_search.setText("")
                    dismiss()
                }
            }
        }
    }

    private val hotPresenter by lazy { CommunitySearchPresenter(this,this,activity!!) }
    private val searchPresenter by lazy { SearchTopicPresenter(this,this,activity!!) }
    private var searchAdapter: ReleaseSearchTopicAdapter?=null
    private var pageIndex=1
    private var pageSize=10
    private var search=""

    override fun setLayoutID(): Int = R.layout.dialog_release_topic

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        hotPresenter.getHotTopic()
    }

    override fun clickListener() {
        Click.viewClick(iv_close).subscribe {
            dismiss()
        }

        edit_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId== EditorInfo.IME_ACTION_SEARCH){
                if (edit_search.text!=null&&edit_search.text.toString().isNotEmpty()){
                    search=edit_search.text.toString()
                    pageIndex=1
                    searchPresenter.getSearchTopic(SearchBody(search,"3",pageIndex.toString(),pageSize.toString()))
                }else{
                    Toast.Tips("请输入搜索内容")
                }
            }
            return@setOnEditorActionListener false
        }
    }

    interface Topic{
        fun setTopic(topic:String,id:String)
    }
}