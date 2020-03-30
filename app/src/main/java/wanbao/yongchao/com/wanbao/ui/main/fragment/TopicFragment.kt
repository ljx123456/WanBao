package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_topic.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.activity.AddressHomePageActivity
import wanbao.yongchao.com.wanbao.ui.main.adapter.CommunityAdapter
import wanbao.yongchao.com.wanbao.ui.main.adapter.TopicAdapter
import wanbao.yongchao.com.wanbao.ui.main.dialog.CommunityReportDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.LoginDialog
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.TopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.HomePageCommunityPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.HomePageCommunityView
import wanbao.yongchao.com.wanbao.ui.main.utils.TopLayoutManager
import wanbao.yongchao.com.wanbao.utils.dialog.ShareDialog
import wanbao.yongchao.com.wanbao.utils.http.BaseUrl
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.pop.PopupWindowHelper
import wanbao.yongchao.com.wanbao.utils.share.ShareUtil
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView
import wanbao.yongchao.com.wanbao.view.ScrollLinearLayoutManager
import java.lang.Exception

@SuppressLint("ValidFragment")
class TopicFragment(val type:String,val id:String) :BaseFragment(),ShareDialog.Share,HomePageCommunityView{

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
        if ((parentFragment!=null&&parentFragment!!.isVisible)||(activity!=null&&!activity!!.isFinishing)) {
            if (data.data != null && data.data.size > 0) {
                Log.e("测试", "显示动态")
                recy_community.visibility = View.VISIBLE
                layout_null.visibility = View.GONE
                if (parentFragment != null) {
                    if (parentFragment!! is MineFragment) {
                        (parentFragment!! as MineFragment).setAddCommunity(false)
//                        var user = GreenDaoHelper.getDaoSessions().userDBDao
//                        if (user != null) {
//                            var info = user.loadAll()
//                            if (info != null && info.size > 0) {
//                                (parentFragment!! as MineFragment).setAddCommunity(false)
//                            }
//                        }
                    }
                }

                if (activity != null) {
                    if (activity!! is AddressHomePageActivity) {
                        (activity!! as AddressHomePageActivity).setNum(data.data[0].topicDynamicNum)
                    }
                }
                if (adapter != null) {
                    if (pageIndex == 1) {
                        Log.e("测试","刷新")
                        adapter!!.setNewData(data.data)
                    } else {
                        adapter!!.addData(data.data)
                    }
                } else {
                    adapter = CommunityAdapter(data.data, isMine)
                    var manage = LinearLayoutManager(activity!!)
                    manage.orientation = LinearLayout.VERTICAL
//        manage.setmCanVerticalScroll(false)
                    recy_community.layoutManager = manage
                    recy_community.adapter = adapter
                    recy_community.isNestedScrollingEnabled = false
//                    recy_community.setFocusable(false)
//                    recy_community.setHasFixedSize(true)
                }

                adapter!!.setLoadMoreView(CustomLoadMoreView())
                if (data.data.size < pageSize) {
                    adapter!!.loadMoreEnd()
                    hasMore = false
//                    adapter!!.removeAllFooterView()
                    if (isFirst) {
                        isFirst=false
                        adapter!!.removeAllFooterView()
                        adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null))
                    }
                } else {
                    adapter!!.loadMoreComplete()
                    hasMore = true
                }


                adapter!!.setOnItemClickListener { mAdapter, view, position ->
                    intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
                    user.setDelId(position.toString())
                }

                shareDialog = ShareDialog(this)

//        adapter.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
//            override fun onLoadMoreRequested() {
//                Log.e("测试","加载更多")
//            }
//        },recy_community)


                adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
                    when (view.id) {
                        R.id.iv_item_community_more -> {
                            var id = ""
                            try {
                                var user = DBUtils.getUser()
                                id = user.userId
                            } catch (e: Exception) {
                                try {
                                    var user = DBUtils.getBusiness()
                                    id = user.businessId
                                } catch (e: Exception) {
                                }
                            }
                            if (id != adapter!!.data[position].user.id.toString()) {
                                popView = LayoutInflater.from(mContext!!).inflate(R.layout.pop_community_more, null)
                                pop = PopupWindowHelper(popView, mContext)
                                pop.showAsDropDown(view, 0, 0)
                                if (adapter!!.data[position].isFocus == 0) {//未关注
                                    if (adapter!!.data[position].user.role == 1) {//用户
                                        popView.findViewById<TextView>(R.id.tv_follow).text = "关注用户"
                                    } else {
                                        popView.findViewById<TextView>(R.id.tv_follow).text = "关注商家"
                                    }
                                } else {
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
                                            presenter!!.getFocus(FocusBody(adapter!!.data[position].user.id.toString(), "1", adapter!!.data[position].id.toString(), "1"))
                                        } else {
                                            presenter!!.getUnFocus(UnFocusBody(adapter!!.data[position].user.id.toString(), "1"))
                                        }
                                    } else {
                                        var dialog = LoginDialog()
                                        dialog.show(childFragmentManager, "")
                                    }

                                }
                                Click.viewClick(popView.findViewById(R.id.tv_collect)).subscribe {
                                    pop.dismiss()
                                    if (isLogin) {
                                        pos = position
                                        isCollect = adapter!!.data[position].isCollect == 1
                                        presenter!!.getCollect(LikeBody(adapter!!.data[position].id, 1, 1))
                                    } else {
                                        var dialog = LoginDialog()
                                        dialog.show(childFragmentManager, "")
                                    }
                                }

                                Click.viewClick(popView.findViewById(R.id.tv_report)).subscribe {
                                    pop.dismiss()
                                    if (isLogin) {
                                        var dialog = CommunityReportDialog(adapter!!.data[position].id.toString())
                                        dialog.show(activity!!.supportFragmentManager, "")
                                    } else {
                                        var dialog = LoginDialog()
                                        dialog.show(childFragmentManager, "")
                                    }
                                }

//                        Click.viewClick(popView.findViewById(R.id.tv_uninterested)).subscribe {
//                            pop.dismiss()
//                            Toast.Tips("          操作成功\n" +
//                                    "将减少同类信息推荐")
//                        }
                            } else {
                                popView = LayoutInflater.from(mContext!!).inflate(R.layout.pop_community_more_mine, null)
                                pop = PopupWindowHelper(popView, mContext)
                                pop.showAsDropDown(view, 0, 0)
                                Click.viewClick(popView.findViewById(R.id.tv_follow)).subscribe {
                                    pop.dismiss()
                                    if (isLogin) {
                                        pos = position
                                        presenter!!.getCommunityDel(CommunityDelBody(adapter!!.data[position].id.toString()))
                                    } else {
                                        var dialog = LoginDialog()
                                        dialog.show(childFragmentManager, "")
                                    }
                                }
                            }
                        }

                        R.id.layout_comment -> {
                            intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
                            user.setDelId(position.toString())
                        }

                        R.id.layout_like -> {
//                        v=adapter!!.getViewByPosition(position,R.id.tv_item_community_like) as TextView
                            if (isLogin) {
                                pos = position
                                isLike = adapter!!.data[position].isLike == 1
                                presenter!!.getLike(LikeBody(adapter!!.data[position].id, 1, 2))
                            } else {
                                var dialog = LoginDialog()
                                dialog.show(childFragmentManager, "")
                            }
                        }

                        R.id.tv_item_community_topic -> {
                            intentUtils.intentTopicDetails(adapter!!.data[position].topicId.toString(), adapter!!.data[position].topicName)
                        }

                        R.id.layout_share -> {
                            share_id=adapter!!.data[position].id.toString()
                            shareDialog.show(childFragmentManager, "")
                        }

                        R.id.tv_item_community_name -> {
                            intentUtils.intentUserHomePage(adapter!!.data[position].userId.toString())
                        }

                        R.id.iv_item_community_header -> {
                            intentUtils.intentUserHomePage(adapter!!.data[position].userId.toString())
                        }

                        R.id.iv_item_community_play -> {
                            intentUtils.intentVideo(adapter!!.data[position].video)
                        }

                        R.id.tv_item_community_address -> {
                            if (adapter!!.data[position].locationType == 3) {//打卡类型：3 商家，2 定位，1 探索-城市坐标
                                intentUtils.intentBusinessHomePage(adapter!!.data[position].objectId.toString())
                            } else {
                                intentUtils.intentAddressHomePage(adapter!!.data[position].objectId.toString())
                            }
//                        else if (adapter!!.data[position].locationType==2){
//                            intentUtils.intentAddressHomePage(adapter!!.data[position].objectId.toString())
//                        }else if (adapter!!.data[position].locationType==1){
//                            intentUtils.intentLandMarkDetails(adapter!!.data[position].objectId.toString())
//                        }
                        }

                        R.id.tv_item_community_focus -> {
                            if (isLogin) {
                                pos = position
                                user_id = adapter!!.data[position].user.id.toString()
                                if (adapter!!.data[position].isFocus == 0) {//未关注
                                    presenter!!.getFocus(FocusBody(adapter!!.data[position].user.id.toString(), "1", adapter!!.data[position].id.toString(), "1"))
                                } else {
                                    presenter!!.getUnFocus(UnFocusBody(adapter!!.data[position].user.id.toString(), "1"))
                                }
                            } else {
                                var dialog = LoginDialog()
                                dialog.show(childFragmentManager, "")
                            }
                        }
                    }
                }
            } else {
                hasMore = false
                if (adapter != null) {
                    adapter!!.loadMoreEnd()
//                    adapter!!.removeAllFooterView()
                    if (isFirst) {
                        isFirst=false
                        adapter!!.removeAllFooterView()
                        adapter!!.addFooterView(View.inflate(activity!!, R.layout.layout_community_footer, null))
                    }
                }

                if (pageIndex == 1) {
                    recy_community.visibility = View.GONE
                    layout_null.visibility = View.VISIBLE
                    if (parentFragment != null) {
                        if (parentFragment!! is MineFragment) {
                            (parentFragment!! as MineFragment).setAddCommunity(true)
//                            var user = GreenDaoHelper.getDaoSessions().userDBDao
//                            if (user != null) {
//                                var info = user.loadAll()
//                                if (info != null && info.size > 0) {
//                                    layout_null.visibility = View.GONE
//                                    (parentFragment!! as MineFragment).setAddCommunity(true)
//                                }
//                            }
                        }
                    }
                }

                if (activity != null) {
                    if (activity!! is AddressHomePageActivity) {
                        (activity!! as AddressHomePageActivity).setNum(0)
                    }
                }
            }

        }
    }

    override fun setShareWX() {
        ShareUtil.shareWxCircle(activity!!,"来自晚豹APP的一条热门动态","来自晚豹APP的一条热门动态", BaseUrl.HOST_URL+"share/page?id="+share_id+"&type=3")
    }

    override fun setSharePYQ() {
//        Toast.Tips("分享到朋友圈")
        ShareUtil.shareWx(activity!!,"来自晚豹APP的一条热门动态","来自晚豹APP的一条热门动态",BaseUrl.HOST_URL+"share/page?id="+share_id+"&type=3")
    }

    override fun setShareQQ() {
//        Toast.Tips("分享到QQ")
        ShareUtil.QQShare(activity!!,"晚豹App","来自晚豹APP的一条热门动态",BaseUrl.HOST_URL+"share/page?id="+share_id+"&type=3")
    }

    private var adapter:CommunityAdapter?=null
    private lateinit var pop: PopupWindowHelper
    private lateinit var popView: View
    private lateinit var shareDialog:ShareDialog
    private var hasMore=true
    private var pos=0
    private var isLike=false
    private lateinit var v:TextView
    private var isCollect=false
    private var user_id=""
    private var share_id=""

    private var isFirst=true

    private var presenter :HomePageCommunityPresenter?=null
    private var pageIndex=1
    private var pageSize=9

    private var isLogin=false

    private var isMine=false

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {

        presenter = HomePageCommunityPresenter(this,this,context!!)

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
        if (type=="动态")
            presenter!!.getHomePageCommunity(HomePageCommunityBody(id,pageIndex.toString(),pageSize.toString()))
        else if (type=="打卡")
            presenter!!.getHomePageLocationCommunity(HomePageLocationCommunityBody(id,pageIndex,pageSize))
        else if (type=="话题")
            presenter!!.getTopicDetails(CommunityBody(id,pageIndex,pageSize))
    }

    override fun setFragmentListener() {


    }

    override fun layoutID(): Int = R.layout.fragment_topic


    fun setLoadMore(){
//        Log.e("测试","加载更多")
        if (hasMore&&presenter!=null) {
            pageIndex = pageIndex + 1
            if (type == "动态")
                presenter!!.getHomePageCommunity(HomePageCommunityBody(id, pageIndex.toString(), pageSize.toString()))
            else if (type == "打卡")
                presenter!!.getHomePageLocationCommunity(HomePageLocationCommunityBody(id, pageIndex, pageSize))
            else if (type=="话题")
                presenter!!.getTopicDetails(CommunityBody(id,pageIndex,pageSize))
        }
    }

    fun setRefresh(){
        Log.e("测试","刷新")
        hasMore=true
        pageIndex=1
        if (presenter!=null) {
            if (type == "动态")
                presenter!!.getHomePageCommunity(HomePageCommunityBody(id, pageIndex.toString(), pageSize.toString()))
            else if (type == "打卡")
                presenter!!.getHomePageLocationCommunity(HomePageLocationCommunityBody(id, pageIndex, pageSize))
            else if (type == "话题")
                presenter!!.getTopicDetails(CommunityBody(id, pageIndex, pageSize))
        }
    }

    fun setMine(){
        isMine=true
    }


    fun setDel(){
        if (adapter!=null&&user.getIsDelMine()){
            adapter!!.remove(user.getDelId().toInt())
            user.setIsDelMine(false)
        }
    }



}