package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_community_like.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.UserBean
import wanbao.yongchao.com.wanbao.ui.main.adapter.CommunityLikeAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityLikeBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityLikeBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.CommunityLikePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunityLikeView
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

@SuppressLint("ValidFragment")
class CommunityLikeFragment(val id:String):BaseFragment(),CommunityLikeView{
    override fun getCommunityLikeRequest(data: CommunityLikeBean) {
        if ((parentFragment!=null&&parentFragment!!.isVisible)||(activity!=null&&!activity!!.isFinishing)) {
            if (data.data != null && data.data.size > 0) {
                recy_community_comment.visibility = View.VISIBLE
                layout_null.visibility = View.GONE
                if (adapter != null) {
                    if (pageIndex == 1) {
                        adapter!!.setNewData(data.data)
                    } else {
                        adapter!!.addData(data.data)
                    }
                } else {
                    adapter = CommunityLikeAdapter(data.data)
                    var manager = LinearLayoutManager(activity!!)
                    manager.orientation = LinearLayout.VERTICAL
                    recy_community_comment.layoutManager = manager
                    recy_community_comment.adapter = adapter
                }

//            adapter!!.setLoadMoreView(CustomLoadMoreView())
                if (data.data.size < 10) {
                    isFlag = false
//                adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
                    adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null))
                }
//            else {
//                adapter!!.loadMoreComplete()
//            }

                adapter!!.setOnItemClickListener { mAdapter, view, position ->

                }

//            adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
//                override fun onLoadMoreRequested() {
//                    pageIndex = pageIndex + 1
//                    presenter.getCommunityLike(CommunityLikeBody(id, pageIndex.toString(), pageSize.toString()))
//                }
//            }, recy_community_comment)
            } else {
                isFlag = false
                if (pageIndex == 1) {
                    recy_community_comment.visibility = View.VISIBLE
                    layout_null.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun getCommunityLikeError() {

    }


    private  var adapter:CommunityLikeAdapter?=null
    private val presenter by lazy { CommunityLikePresenter(this,this,activity!!) }
    private var pageIndex=1
    private var pageSize=10
    private var isFlag=true

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        presenter.getCommunityLike(CommunityLikeBody(id,pageIndex.toString(),pageSize.toString()))
    }

    override fun setFragmentListener() {

    }

    override fun layoutID(): Int = R.layout.fragment_community_like

    fun setMore(){
        if (isFlag) {
            pageIndex = pageIndex + 1
            presenter.getCommunityLike(CommunityLikeBody(id, pageIndex.toString(), pageSize.toString()))
        }
    }

    fun setAdd(){
        var bean=CommunityLikeBean.DataBean()
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                bean.avatar=info[0].avatar
                bean.nickname=info[0].nickName
                bean.id=info[0].id.toInt()
                bean.signature=info[0].signature
                bean.releaseTime="刚刚"
                bean.createTime="刚刚"
            }else if (inf != null && inf.size > 0) {
                bean.avatar=inf[0].avatar
                bean.nickname=inf[0].nickName
                bean.id=inf[0].id.toInt()
                bean.signature=inf[0].signature
                bean.releaseTime="刚刚"
                bean.createTime="刚刚"
//                isLogin=true
            }else{
//                isLogin=false
            }
        }
        if (adapter!=null){
            adapter!!.addData(0,bean)
        }else{
            var list=ArrayList<CommunityLikeBean.DataBean>()
            list.add(bean)
            recy_community_comment.visibility = View.VISIBLE
            layout_null.visibility = View.GONE
            adapter = CommunityLikeAdapter(list)
            var manager = LinearLayoutManager(activity!!)
            manager.orientation = LinearLayout.VERTICAL
            recy_community_comment.layoutManager = manager
            recy_community_comment.adapter = adapter


                isFlag = false
                adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null))
//            else {
//                adapter!!.loadMoreComplete()
//            }

            adapter!!.setOnItemClickListener { mAdapter, view, position ->

            }
        }
    }

//    fun

}