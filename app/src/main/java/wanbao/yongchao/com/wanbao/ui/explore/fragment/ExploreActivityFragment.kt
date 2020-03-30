package wanbao.yongchao.com.wanbao.ui.explore.fragment

import android.annotation.SuppressLint
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_explore_activities.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.adapter.ExploreActivityAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreActivityBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.ExploreActivityFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ExploreActivityFragmentView
import wanbao.yongchao.com.wanbao.ui.main.fragment.ExploreFragment
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils

@SuppressLint("ValidFragment")
class ExploreActivityFragment(var type:String):BaseFragment() ,ExploreActivityFragmentView{
    override fun getExploreActivityRequest(data: ExploreActivityBean) {
        if ((parentFragment!=null&&parentFragment!!.isVisible)||(activity!=null&&!activity!!.isFinishing)) {
            if (data.data != null && data.data.size > 0) {
                recy_community_comment.visibility = View.VISIBLE
                layout_none.visibility = View.GONE
                if (adapter != null) {
                    adapter!!.addData(data.data)
                } else {
                    adapter = ExploreActivityAdapter(data.data)
                    var manager = LinearLayoutManager(activity!!)
                    manager.orientation = LinearLayout.VERTICAL
                    recy_community_comment.layoutManager = manager
                    recy_community_comment.adapter = adapter
                    recy_community_comment.isNestedScrollingEnabled = false
                    adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null), adapter!!.data.size - 1)
                }
//        adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null),adapter!!.data.size-1)
                if (data.data.size < pageSize) {
                    isFlag = false
                } else {
                    isFlag = true
                }

                adapter!!.setOnItemClickListener { mAdapter, view, position ->
                    intentUtils.intentActivitiesDetails(adapter!!.data[position].id)
                }
            } else {
//            view_a.visibility=View.VISIBLE
                if (pageIndex == 1) {
                    if (parentFragment != null && parentFragment is ExploreFragment) {
                        (parentFragment as ExploreFragment).remove()
                    }
                }
                isFlag = false
            }
        }
    }

    private var adapter: ExploreActivityAdapter?=null
    private val presenter by lazy { ExploreActivityFragmentPresenter (this,this,activity!!)}
    private var pageIndex=1
    private var pageSize=10
    private var isFlag=true
    private var isFirst=true

    private var avatar=""

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        pageIndex=1
        var body=ExploreActivityBody()
        body.cityId=user.getCityID().toInt()
        body.pageIndex=pageIndex
        body.pageSize=pageSize
        body.type=type
        presenter.getExploreActivity(body)

    }

    override fun setFragmentListener() {

    }

    override fun layoutID(): Int = R.layout.fragment_explore_activities



    fun setLoadMore(){
        if (isFlag) {
            pageIndex = pageIndex + 1
            var body = ExploreActivityBody()
            body.cityId = user.getCityID().toInt()
            body.pageIndex = pageIndex
            body.pageSize = pageSize
            body.type = type
            presenter.getExploreActivity(body)
        }

    }

    fun setUpdataWant(id:String){
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
        if (adapter!=null&&avatar!=""){
            adapter!!.data.forEachIndexed { position, dataBean ->
                if (adapter!!.data[position].id.toString()==id){
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

    fun setSort(sort:String){
        this.type=sort
        pageIndex=1
        var body=ExploreActivityBody()
        body.cityId=user.getCityID().toInt()
        body.pageIndex=pageIndex
        body.pageSize=pageSize
        body.type=type
        presenter.getExploreActivity(body)
    }



}