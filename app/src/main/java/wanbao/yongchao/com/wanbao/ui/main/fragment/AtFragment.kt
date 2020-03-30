package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.alibaba.fastjson.JSONObject
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_at.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.main.adapter.AtAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AtBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FansBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.AtFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AtFragmentView
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

@SuppressLint("ValidFragment")
class AtFragment(val type:String,val set:setAt):BaseFragment(),AtFragmentView{
    override fun getFansRequest(data: FansBean) {

//        swip.isRefreshing=false
//        if (data)
        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else{
                adapter!!.addData(data.data)
            }
        }else {
            adapter = AtAdapter(data.data)
            var manager = LinearLayoutManager(activity!!)
            manager.orientation = LinearLayout.VERTICAL
            recy_at.layoutManager = manager
            recy_at.adapter = adapter
        }
        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            posi=position
            if (adapter!!.data[position].isFlag==false) {
                adapter!!.data[position].isFlag=true
                adapter!!.notifyDataSetChanged()
                set.setAt(adapter!!.data[position])
            }else{
                adapter!!.data[position].isFlag=false
                adapter!!.notifyDataSetChanged()
                set.cancel(adapter!!.data[position])
            }
        }

        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<10){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                if (type=="粉丝"){
                    if (id!="") {
                        presenter.getFans(FansBody(id,"2", pageIndex.toString(), pageSize.toString()))
                    }
                }else if (type=="关注"){
                    if (id!="") {
                        presenter.getFans(FansBody(id,"1", pageIndex.toString(), pageSize.toString()))
                    }
                }
            }
        },recy_at)
//
//        swip.setOnRefreshListener {
//            pageIndex=1
//            if (type=="粉丝"){
//                presenter.getFans(FansBody("2",pageIndex.toString(),pageSize.toString()))
//            }else if (type=="关注"){
//                presenter.getFans(FansBody("1",pageIndex.toString(),pageSize.toString()))
//            }
//        }
    }

    var list=ArrayList<AtBean>()
    var chooseList=ArrayList<AtBean>()
    var adapter:AtAdapter?=null
    var posi=0
    var pageIndex=1
    var pageSize=10
    lateinit var mCache:ACache
    private val presenter by lazy { AtFragmentPresenter(this,this,activity!!) }
    private var id=""

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                id=info.get(0).userId
            }else if (inf != null && inf.size > 0) {
                id=inf.get(0).businessId
            }
        }
        mCache= ACache.get(activity!!)
        if (type=="最近"){
            if (mCache.getAsString("NearUser") != null && !"".equals(mCache.getAsString("NearUser"))) {
                var list = JSONObject.parseArray(mCache.getAsString("NearUser"), FansBean.DataBean::class.java)
                if (list.size>0){
                    adapter = AtAdapter(list)
                    var manager = LinearLayoutManager(activity!!)
                    manager.orientation = LinearLayout.VERTICAL
                    recy_at.layoutManager = manager
                    recy_at.adapter = adapter
                    adapter!!.setOnItemClickListener { mAdapter, view, position ->
                        posi=position
                        if (adapter!!.data[position].isFlag==false) {
                            adapter!!.data[position].isFlag=true
                            adapter!!.notifyDataSetChanged()
                            set.setAt(adapter!!.data[position])
                        }else{
                            adapter!!.data[position].isFlag=false
                            adapter!!.notifyDataSetChanged()
                            set.cancel(adapter!!.data[position])
                        }
                    }
                }else{

                }
            }

        }else if (type=="粉丝"){
            if (id!="") {
                presenter.getFans(FansBody(id,"2", pageIndex.toString(), pageSize.toString()))
            }
        }else{
            if (id!="") {
                presenter.getFans(FansBody(id,"1", pageIndex.toString(), pageSize.toString()))
            }
        }

    }

    override fun setFragmentListener() {

    }

    override fun layoutID(): Int = R.layout.fragment_at


    fun setFlag(flag:Boolean){
        adapter!!.data[posi].isFlag=flag
        adapter!!.notifyDataSetChanged()
    }

    fun setCancel(bean:FansBean.DataBean){
        if (adapter!=null){
            adapter!!.data.forEach {
                if (it.nickname==bean.nickname){
                    it.isFlag=false
                }
            }
            adapter!!.notifyDataSetChanged()
        }
    }

    fun setUpdateList(beans:ArrayList<FansBean.DataBean>){
        if (adapter!=null){
            adapter!!.data.forEach {
                var b=it
                b.isFlag=false
                beans.forEach {
                    if (it.id==b.id){
                        b.isFlag=true
                    }
                }
            }
            adapter!!.notifyDataSetChanged()
        }
    }

    interface setAt{
        fun setAt(bean:FansBean.DataBean)
        fun cancel(bean: FansBean.DataBean)
    }
}