package wanbao.yongchao.com.wanbao.ui.explore.activity

import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_activities_search.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.adapter.ExploreActivityAdapter
import wanbao.yongchao.com.wanbao.ui.explore.adapter.SearchHotActivitiesAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotActivitiesBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreActivityBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.SearchHotActivitiesBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.SearchActivitiesPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.SearchActivitiesView
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class ActivitiesSearchActivity:BaseActivity(),SearchActivitiesView{
    override fun getSearchHotActivitiesRequest(data: SearchHotActivitiesBean) {
        if (data.data.size>0){
            layout_hot_search.visibility=View.VISIBLE
            hotSearchAdapter= SearchHotActivitiesAdapter(data.data)
            var manager= LinearLayoutManager(this)
            manager.orientation= LinearLayout.VERTICAL
            recy_hot_activities.layoutManager=manager
            recy_hot_activities.adapter=hotSearchAdapter

            hotSearchAdapter.setOnItemClickListener { adapter, view, position ->
                intentUtils.intentActivitiesDetails(hotSearchAdapter.data[position].id)
            }
        }else{
            layout_hot_search.visibility=View.GONE
        }
    }

    override fun getSearchActivitiesRequest(data: ExploreActivityBean) {
        recy_activities.visibility=View.VISIBLE
        if (adapter!=null){
            if (pageIndex==1) {
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }

        }else{
            adapter= ExploreActivityAdapter(data.data)
            var manager=LinearLayoutManager(this)
            manager.orientation=LinearLayout.VERTICAL
            recy_activities.layoutManager=manager
            recy_activities.adapter=adapter

        }

        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<pageSize){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                var body= ExploreActivityBody()
                body.type="0"
                body.cityId=user.getCityID().toInt()
                body.like=search
                body.pageIndex=pageIndex
                body.pageSize=pageSize
                presenter.getSearchActivities(body)
            }
        },recy_activities)

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentActivitiesDetails(adapter!!.data[position].id)
        }
    }

    private var historyList=ArrayList<String>()

    private val presenter by lazy { SearchActivitiesPresenter(this,this,this) }

    private lateinit var hotSearchAdapter:SearchHotActivitiesAdapter

    private var adapter:ExploreActivityAdapter?=null
    private var pageIndex=1
    private val pageSize=10
    private var search=""


    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_activities_search

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

        var mCache = ACache.get(this)
        mCache.getAsString("HistorySearchActivities")
        if (mCache.getAsString("HistorySearchActivities") != null && !"".equals(mCache.getAsString("HistorySearchActivities"))) {
            historyList = Gson().fromJson(mCache.getAsString("HistorySearchActivities"), ArrayList<String>()::class.java)
            if(historyList.size>0){
                layout_history.visibility= View.VISIBLE
                history.setList(historyList)
            }else{
                layout_history.visibility= View.GONE
            }
        }else{
            layout_history.visibility= View.GONE
        }

        presenter.getSearchHotActivities(SearchHotActivitiesBody(2368))



    }

    override fun clickListener() {

        Click.viewClick(tv_cancel).subscribe {
            finish()
        }

        history.setOnItemClickListener { position, text ->
            layout_history.visibility= View.GONE
            layout_hot_search.visibility=View.GONE
            edit_search.setText(text)
            search=text
            historyList.add(search)
            pageIndex=1
            var body= ExploreActivityBody()
            body.type="0"
            body.cityId=user.getCityID().toInt()
            body.like=search
            body.pageIndex=pageIndex
            body.pageSize=pageSize
            presenter.getSearchActivities(body)
        }

        edit_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId== EditorInfo.IME_ACTION_SEARCH){
                if (edit_search.text!=null&&edit_search.text.toString().isNotEmpty()){
                    layout_history.visibility=View.GONE
                    layout_hot_search.visibility=View.GONE
                    search=edit_search.text.toString()
                    historyList.add(search)
                    pageIndex=1
                    var body= ExploreActivityBody()
                    body.type="0"
                    body.cityId=user.getCityID().toInt()
                    body.like=search
                    body.pageIndex=pageIndex
                    body.pageSize=pageSize
                    presenter.getSearchActivities(body)
                }else{
                    Toast.Tips("请输入搜索内容")
                }
            }
            return@setOnEditorActionListener false
        }

        Click.viewClick(iv_delete).subscribe {
            ShowDialog.showCustomDialogNoTitle(this,"继续操作将清空历史搜索记录","清空","取消",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            dialog.dismiss()
                            historyList.clear()
                            var mCache = ACache.get(this@ActivitiesSearchActivity)
                            mCache.put("HistorySearchActivities","")
                            layout_history.visibility=View.GONE
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
            })

        }

    }



    override fun onDestroy() {
        super.onDestroy()
        if (historyList.size>0) {
            var mCache = ACache.get(this)
            mCache.put("HistorySearchActivities", Gson().toJson(historyList))
        }
    }

    override fun onResume() {
        super.onResume()
        var avatar=""
        var use = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (use != null||business!=null) {
            var info = use.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                avatar=info[0].avatar
            }else if (inf != null && inf.size > 0) {
                avatar=inf[0].avatar
            }else{
                avatar=""
            }
        } else{
            avatar=""
        }
        if (avatar!=""&&user.getIsWant()&&user.getWantId()!=""){
            if (adapter!=null){
                adapter!!.data.forEachIndexed { position, dataBean ->
                    if (adapter!!.data[position].id.toString()==user.getWantId()){
                        if (user.getIsAddWant()==0) {
                            if (adapter!!.data[position].wantAvatars.size < 5) {
                                adapter!!.data[position].wantAvatars.add(avatar)
                            }
                            adapter!!.data[position].wantNum = adapter!!.data[position].wantNum + 1
                        }else if (user.getIsAddWant()==1){
                            if (adapter!!.data[position].wantNum<=5){
                                adapter!!.data[position].wantAvatars.remove(avatar)
                            }
                            adapter!!.data[position].wantNum = adapter!!.data[position].wantNum - 1
                        }
                        adapter!!.notifyDataSetChanged()
                        user.setWantId("")
                        user.setIsWant(false)
                        user.setIsAddWant(-1)
                    }
                }
            }
        }
    }
}