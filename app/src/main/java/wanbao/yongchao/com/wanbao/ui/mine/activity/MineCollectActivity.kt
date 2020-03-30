package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_mine_collect.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.main.adapter.CommunityAdapter
import wanbao.yongchao.com.wanbao.ui.main.dialog.CommunityReportDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.LoginDialog
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityDelBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.LikeBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.LikeCommunityBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.LikeCommunityPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.LikeCommunityView
import wanbao.yongchao.com.wanbao.utils.dialog.ShareDialog
import wanbao.yongchao.com.wanbao.utils.http.BaseUrl
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.utils.pop.PopupWindowHelper
import wanbao.yongchao.com.wanbao.utils.share.ShareUtil
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView
import java.lang.Exception

class MineCollectActivity: BaseActivity(), ShareDialog.Share , LikeCommunityView {

    override fun getCollectRequest() {
        if (isCollect){
            Toast.Tips("已取消收藏")
            adapter!!.data[pos].isCollect=0
//            adapter!!.notifyDataSetChanged()
        }else{
            Toast.Tips("已收藏")
            adapter!!.data[pos].isCollect=1
//            adapter!!.notifyDataSetChanged()
        }
    }

    override fun getLikeRequest() {
        if (!isLike) {
//            v.setTextColor(Color.parseColor("#FC4625"))
//            var draw = activity!!.resources.getDrawable(R.mipmap.homepage_button_list_like_pre)
//            draw.setBounds(0, 0, draw.minimumWidth, draw.minimumHeight)
//            v.setCompoundDrawables(draw, null, null, null)
            adapter!!.data[pos].isLike=1
            adapter!!.data[pos].likeNum=(adapter!!.data[pos].likeNum+1)
//            adapter!!.notifyDataSetChanged()
//            adapter!!.notifyItemChanged(pos)
            var bundle = Bundle()
            if (adapter!!.data[pos].likeNum>=1000) {
                bundle.putString("add", "999+")
            }else{
                bundle.putString("add", adapter!!.data[pos].likeNum.toString())
            }
            adapter!!.notifyItemChanged(pos, bundle)
        }else{
//            v.setTextColor(Color.parseColor("#a6ffffff"))
//            var draw = activity!!.resources.getDrawable(R.mipmap.homepage_button_list_like_nor)
//            draw.setBounds(0, 0, draw.minimumWidth, draw.minimumHeight)
//            v.setCompoundDrawables(draw, null, null, null)
            adapter!!.data[pos].isLike=0
            adapter!!.data[pos].likeNum=(adapter!!.data[pos].likeNum-1)
//            adapter!!.notifyDataSetChanged()
//            adapter!!.notifyItemChanged(pos)
            var bundle = Bundle()
            if (adapter!!.data[pos].likeNum>=1000){
                bundle.putString("cancel","999+")
            }else {
                bundle.putString("cancel", adapter!!.data[pos].likeNum.toString())
            }
            adapter!!.notifyItemChanged(pos, bundle)
        }
    }

    override fun getFocusRequest() {
        adapter!!.data.forEachIndexed { position, dataBean ->
            if (dataBean.user.id.toString()==user_id){
                adapter!!.data[position].isFocus=1
//        adapter!!.notifyDataSetChanged()
//                if (adapter!!.getViewByPosition(position,R.id.tv_item_community_focus)!=null) {
//                    (adapter!!.getViewByPosition(position, R.id.tv_item_community_focus) as TextView).setText("已关注")
//                    (adapter!!.getViewByPosition(position, R.id.tv_item_community_focus) as TextView).setTextColor(Color.parseColor("#40ffffff"))
//                }
                var bundle = Bundle()
                bundle.putString("focus", "")
                adapter!!.notifyItemChanged(position, bundle)
            }
        }
        Toast.Tips("已关注")
    }

    override fun getUnFocusRequest() {
        adapter!!.data.forEachIndexed { position, dataBean ->
            if (dataBean.user.id.toString()==user_id){
                adapter!!.data[position].isFocus=0
//        adapter!!.notifyDataSetChanged()
//                if (adapter!!.getViewByPosition(position,R.id.tv_item_community_focus)!=null) {
//                    (adapter!!.getViewByPosition(position, R.id.tv_item_community_focus) as TextView).setText("关注")
//                    (adapter!!.getViewByPosition(position, R.id.tv_item_community_focus) as TextView).setTextColor(Color.parseColor("#fcc725"))
//                }
                var bundle = Bundle()
                bundle.putString("unfocus", "")
                adapter!!.notifyItemChanged(position, bundle)
            }
        }
        Toast.Tips("取消关注")
    }

    override fun getCommunityDelRequest() {
        adapter!!.remove(pos)
        Toast.Tips("已删除")
    }

    override fun getHomePageCommunityRequest(data: CommunityBean) {
        swip.isRefreshing=false
        Log.e("测试","显示动态")
        if (adapter != null) {
            if (pageIndex == 1) {
                adapter!!.setNewData(data.data)
            } else {
                adapter!!.addData(data.data)
            }
        } else {
            adapter = CommunityAdapter(data.data,false)
            var manage = LinearLayoutManager(this)
            manage.orientation = LinearLayout.VERTICAL
//        manage.setmCanVerticalScroll(false)
            recy_collect.layoutManager = manage
            recy_collect.adapter = adapter
            adapter!!.setEmptyView(layoutUtils.getNoneCollect())

        }

        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size < pageSize) {
            adapter!!.loadMoreEnd()
//                hasMore=false
////            adapter!!.removeAllFooterView()
//                adapter!!.addFooterView(View.inflate(this,R.layout.layout_community_footer,null))
        } else {
            adapter!!.loadMoreComplete()
//                hasMore=true
        }


        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
        }



        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                var body= LikeCommunityBody()
                body.pageIndex=pageIndex
                body.pageSize=pageSize
                body.type=1
                presenter.getLikeCommunity(body)
            }
        },recy_collect)


        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            when (view.id) {
                R.id.iv_item_community_more -> {
                    var id=""
                    try {
                        var user= DBUtils.getUser()
                        id=user.userId
                    }catch (e: Exception){
                        try {
                            var user= DBUtils.getBusiness()
                            id=user.businessId
                        }catch (e: Exception){}
                    }
                    if (id!=adapter!!.data[position].user.id.toString()) {
                        popView = LayoutInflater.from(mContext!!).inflate(R.layout.pop_community_more, null)
                        pop = PopupWindowHelper(popView, mContext)
                        pop.showAsDropDown(view, 0, 0)
                        if (adapter!!.data[position].isFocus==0){//未关注
                            if (adapter!!.data[position].user.role == 1) {//用户
                                popView.findViewById<TextView>(R.id.tv_follow).text = "关注用户"
                            } else {
                                popView.findViewById<TextView>(R.id.tv_follow).text = "关注商家"
                            }
                        }else {
                            popView.findViewById<TextView>(R.id.tv_follow).text = "取消关注"
                        }

                        if (adapter!!.data[position].isCollect == 0) {//未收藏
                            popView.findViewById<TextView>(R.id.tv_collect).text = "收藏"
                        } else {
                            popView.findViewById<TextView>(R.id.tv_collect).text = "取消收藏"
                        }


                        Click.viewClick(popView.findViewById(R.id.tv_follow)).subscribe {
                            pop.dismiss()
                            if (isLogin) {
                                pos = position
                                if (adapter!!.data[position].isFocus == 0) {//未关注
                                    presenter.getFocus(FocusBody(adapter!!.data[position].user.id.toString(), "1", adapter!!.data[position].id.toString(), "1"))
                                } else {
                                    presenter.getUnFocus(UnFocusBody(adapter!!.data[position].user.id.toString(), "1"))
                                }
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(supportFragmentManager,"")
                            }

                        }
                        Click.viewClick(popView.findViewById(R.id.tv_collect)).subscribe {
                            pop.dismiss()
                            if (isLogin) {
                                pos = position
                                isCollect = adapter!!.data[position].isCollect == 1
                                presenter.getCollect(LikeBody(adapter!!.data[position].id, 1, 1))
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(supportFragmentManager,"")
                            }
                        }

                        Click.viewClick(popView.findViewById(R.id.tv_report)).subscribe {
                            pop.dismiss()
                            if (isLogin) {
                                var dialog = CommunityReportDialog(adapter!!.data[position].id.toString())
                                dialog.show(supportFragmentManager, "")
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(supportFragmentManager,"")
                            }
                        }

//                        Click.viewClick(popView.findViewById(R.id.tv_uninterested)).subscribe {
//                            pop.dismiss()
//                            Toast.Tips("          操作成功\n" +
//                                    "将减少同类信息推荐")
//                        }
                    }else if (id==adapter!!.data[position].user.id.toString()){
                        popView = LayoutInflater.from(mContext!!).inflate(R.layout.pop_community_more_mine, null)
                        pop = PopupWindowHelper(popView, mContext)
                        pop.showAsDropDown(view, 0, 0)
                        Click.viewClick(popView.findViewById(R.id.tv_follow)).subscribe {
                            pop.dismiss()
                            if (isLogin) {
                                pos = position
                                presenter.getCommunityDel(CommunityDelBody(adapter!!.data[position].id.toString()))
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(supportFragmentManager,"")
                            }
                        }
                    }
                }

                R.id.layout_comment ->{
                    intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
                }

                R.id.layout_like ->{
//                        v=adapter!!.getViewByPosition(position,R.id.tv_item_community_like) as TextView
                    pos=position
                    if (isLogin) {
                        isLike = adapter!!.data[position].isLike == 1
                        presenter.getLike(LikeBody(adapter!!.data[position].id, 1, 2))
                    }else{
                        var dialog= LoginDialog()
                        dialog.show(supportFragmentManager,"")
                    }
                }

                R.id.tv_item_community_topic -> {
                    intentUtils.intentTopicDetails(adapter!!.data[position].topicId.toString(),adapter!!.data[position].topicName)
                }

                R.id.layout_share -> {
                    share_id=adapter!!.data[position].id.toString()
                    shareDialog.show(supportFragmentManager, "")
                }

                R.id.tv_item_community_name ->{
                    intentUtils.intentUserHomePage(adapter!!.data[position].userId.toString())
                }

                R.id.iv_item_community_header ->{
                    intentUtils.intentUserHomePage(adapter!!.data[position].userId.toString())
                }

                R.id.iv_item_community_play -> {
                    intentUtils.intentVideo(adapter!!.data[position].video)
                }

                R.id.tv_item_community_address -> {
                    if (adapter!!.data[position].locationType==3){//打卡类型：3 商家，2 定位，1 探索-城市坐标
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].objectId.toString())
                    }else{
                        intentUtils.intentAddressHomePage(adapter!!.data[position].objectId.toString())
                    }
//                    else if (adapter!!.data[position].locationType==2){
//                        intentUtils.intentAddressHomePage(adapter!!.data[position].objectId.toString())
//                    }else if (adapter!!.data[position].locationType==1){
//                        intentUtils.intentLandMarkDetails(adapter!!.data[position].objectId.toString())
//                    }
                }

                R.id.tv_item_community_focus ->{
                    if (isLogin) {
                        user_id=adapter!!.data[position].user.id.toString()
                        pos = position
                        if (adapter!!.data[position].isFocus == 0) {//未关注
                            presenter.getFocus(FocusBody(adapter!!.data[position].user.id.toString(), "1", adapter!!.data[position].id.toString(), "1"))
                        } else {
                            presenter.getUnFocus(UnFocusBody(adapter!!.data[position].user.id.toString(), "1"))
                        }
                    }else{
                        var dialog= LoginDialog()
                        dialog.show(supportFragmentManager,"")
                    }
                }
            }
        }

    }

    override fun setShareWX() {
        ShareUtil.shareWxCircle(this,"来自晚豹APP的一条热门动态","来自晚豹APP的一条热门动态", BaseUrl.HOST_URL+"share/page?id="+share_id+"&type=3")
    }

    override fun setSharePYQ() {
//        Toast.Tips("分享到朋友圈")
        ShareUtil.shareWx(this,"来自晚豹APP的一条热门动态","来自晚豹APP的一条热门动态",BaseUrl.HOST_URL+"share/page?id="+share_id+"&type=3")
    }

    override fun setShareQQ() {
//        Toast.Tips("分享到QQ")
        ShareUtil.QQShare(this,"晚豹App","来自晚豹APP的一条热门动态",BaseUrl.HOST_URL+"share/page?id="+share_id+"&type=3")
    }

    private var adapter: CommunityAdapter?=null
    private lateinit var pop: PopupWindowHelper
    private lateinit var popView: View
    private lateinit var shareDialog: ShareDialog
    private var pos=0
    private var isLike=false
    private lateinit var v: TextView
    private var isCollect=false

    private val presenter by lazy { LikeCommunityPresenter(this,this,this) }
    private var pageIndex=1
    private var pageSize=10

    private var isLogin=false
    private var user_id=""
    private var share_id=""

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_mine_collect

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                isLogin=true
            }else if (inf != null && inf.size > 0) {
                isLogin=true
            }else{
                isLogin=false
            }
        } else{
            isLogin=false
        }
        pageIndex=1
        var body= LikeCommunityBody()
        body.pageIndex=pageIndex
        body.pageSize=pageSize
        body.type=1
        presenter.getLikeCommunity(body)
        shareDialog = ShareDialog(this)
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }

        swip.setOnRefreshListener {
            pageIndex=1
            var body= LikeCommunityBody()
            body.pageIndex=pageIndex
            body.pageSize=pageSize
            body.type=1
            presenter.getLikeCommunity(body)
        }
    }
}