package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_community_hot.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.adapter.CommunityAdapter
import wanbao.yongchao.com.wanbao.ui.main.adapter.TopicAdapter
import wanbao.yongchao.com.wanbao.ui.main.dialog.CommunityReportDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.LoginDialog
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.TopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.CommunityHotFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunityHotFragmentView
import wanbao.yongchao.com.wanbao.ui.main.utils.TopLayoutManager
import wanbao.yongchao.com.wanbao.utils.dialog.ShareDialog
import wanbao.yongchao.com.wanbao.utils.http.BaseUrl
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.layoutUtils
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.pop.PopupWindowHelper
import wanbao.yongchao.com.wanbao.utils.share.ShareUtil
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.GlideCacheUtil
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView
import wanbao.yongchao.com.wanbao.view.ScrollLinearLayoutManager
import java.lang.Exception

@SuppressLint("ValidFragment")
class CommunityHotFragment(val type:String) :BaseFragment(),ShareDialog.Share,CommunityHotFragmentView, UserPermissions.MemoryReadPermissionsFace, LocationUtils.Location{
    override fun getCommunityHotError() {
        swip_community.isRefreshing=false
//        adapter = null
//        manage = ScrollLinearLayoutManager(activity!!)
//        manage.orientation = LinearLayout.VERTICAL
//        manage.setmCanVerticalScroll(true)
//        recy_community.layoutManager = manage
//        recy_community.adapter = adapter
        Log.e("测试","失败数据")
        swip_community.setOnRefreshListener {
            Log.e("测试","失败刷新")
            pageIndex=1
            if (topicId==""){
                presenter.getCommunityHot(CommunityBody(pageIndex, pageSize))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunityHot(CommunityBody(topicId, pageIndex, pageSize))
            }
        }

    }

    override fun getCommunitySquareError() {
        swip_community.isRefreshing=false
        swip_community.setOnRefreshListener {
            pageIndex=1
            if (topicId==""){
                presenter.getCommunitySquare(CommunityBody(pageIndex, pageSize))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunitySquare(CommunityBody(topicId, pageIndex, pageSize))
            }
        }
    }

    override fun getCommunityNearbyError() {
        swip_community.isRefreshing=false
        swip_community.setOnRefreshListener {
            pageIndex=1
            if (topicId==""){
                presenter.getCommunityNearby(CommunityBody(pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunityNearby(CommunityBody(topicId, pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
            }
        }
    }

    override fun getCommunityCityError() {
        swip_community.isRefreshing=false
        swip_community.setOnRefreshListener {
            pageIndex=1
            if (topicId==""){
                presenter.getCommunityCity(CommunityBody(pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunityCity(CommunityBody(topicId, pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
            }
        }
    }

    override fun getCommunityFocusError() {
        swip_community.isRefreshing=false
        swip_community.setOnRefreshListener {
            pageIndex=1
            if (topicId==""){
                presenter.getCommunityFocus(CommunityBody(pageIndex, pageSize))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunityFocus(CommunityBody(topicId, pageIndex, pageSize))
            }
        }
    }

    private var pos=0
    private var isLike=false
    private lateinit var v:TextView
    private var isCollect=false
    private var isCreated=false
    private var share_id=""

    override fun getCollectRequest() {
        if (isCollect){
            Toast.Tips("已取消收藏")
            adapter!!.data[pos].isCollect=0
//            adapter!!.notifyDataSetChanged()
//            adapter!!.notifyItemChanged(pos)
        }else{
            Toast.Tips("已收藏")
            adapter!!.data[pos].isCollect=1
//            adapter!!.notifyDataSetChanged()
//            adapter!!.notifyItemChanged(pos)
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

    override fun getLocationSuccess(city: String) {
        if (type=="附近") {
            if (topicId=="")
                presenter.getCommunityNearby(CommunityBody(pageIndex, pageSize, user.getLocationLng(), user.getLocationLat()))
            else
                presenter.getCommunityNearby(CommunityBody(topicId,pageIndex, pageSize, user.getLocationLng(), user.getLocationLat()))
        }
        else {
            if (topicId=="")
                presenter.getCommunityCity(CommunityBody(pageIndex, pageSize, user.getLocationLng(), user.getLocationLat()))
            else
                presenter.getCommunityCity(CommunityBody(topicId,pageIndex, pageSize, user.getLocationLng(), user.getLocationLat()))
        }
    }

    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        if (user.getLocationLng()==""||user.getLocationLat()==""){
            LocationUtils(this).getLocation()
        }else{
            if (type=="附近") {
                if (topicId=="")
                    presenter.getCommunityNearby(CommunityBody(pageIndex, pageSize, user.getLocationLng(), user.getLocationLat()))
                else
                    presenter.getCommunityNearby(CommunityBody(topicId,pageIndex, pageSize, user.getLocationLng(), user.getLocationLat()))
            }
            else {
                if (topicId=="")
                    presenter.getCommunityCity(CommunityBody(pageIndex, pageSize, user.getLocationLng(), user.getLocationLat()))
                else
                    presenter.getCommunityCity(CommunityBody(topicId,pageIndex, pageSize, user.getLocationLng(), user.getLocationLat()))
            }
        }
    }

    override fun requestPermissionsFaceError() {

    }

    override fun getCommunitySquareRequest(data: CommunityBean) {
        swip_community.isRefreshing=false

        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else {
                adapter!!.addData(data.data)
            }
        }else {
            adapter = CommunityAdapter(data.data,false)
            manage = ScrollLinearLayoutManager(activity!!)
            manage.orientation = LinearLayout.VERTICAL
            manage.setmCanVerticalScroll(true)
            recy_community.layoutManager = manage
            recy_community.adapter = adapter
        }
        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<9){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            pos = position
            intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                if (topicId==""){
                    presenter.getCommunitySquare(CommunityBody(pageIndex, pageSize))
                }else{
                    presenter.getCommunitySquare(CommunityBody(topicId, pageIndex, pageSize))
                }
            }
        },recy_community)

        swip_community.setOnRefreshListener {
            pageIndex=1
            if (topicId==""){
                presenter.getCommunitySquare(CommunityBody(pageIndex, pageSize))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunitySquare(CommunityBody(topicId, pageIndex, pageSize))
            }
        }

        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            pos = position
            when(view.id){
                R.id.iv_item_community_more -> {
                    var id=""
                    try {
                        var user=DBUtils.getUser()
                        id=user.userId
                    }catch (e:Exception){
                        try {
                            var user=DBUtils.getBusiness()
                            id=user.businessId
                        }catch (e:Exception){}
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
                                dialog.show(childFragmentManager,"")
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
                                dialog.show(childFragmentManager,"")
                            }
                        }

                        Click.viewClick(popView.findViewById(R.id.tv_report)).subscribe {
                            pop.dismiss()
                            if (isLogin) {
                                var dialog = CommunityReportDialog(adapter!!.data[position].id.toString())
                                dialog.show(activity!!.supportFragmentManager, "")
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(childFragmentManager,"")
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
                                dialog.show(childFragmentManager,"")
                            }
                        }
                    }
                }

                R.id.layout_comment ->{
                    intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
                }

                R.id.layout_like ->{
                    if (isLogin) {
                        v = adapter!!.getViewByPosition(position, R.id.tv_item_community_like) as TextView
                        pos = position
                        isLike = adapter!!.data[position].isLike == 1
                        presenter.getLike(LikeBody(adapter!!.data[position].id, 1, 2))
                    }else{
                        var dialog= LoginDialog()
                        dialog.show(childFragmentManager,"")
                    }
                }

                R.id.tv_item_community_topic ->{
                    intentUtils.intentTopicDetails(adapter!!.data[position].topicId.toString(),adapter!!.data[position].topicName)
                }

                R.id.layout_share ->{
                    share_id=adapter!!.data[position].id.toString()
                    shareDialog.show(childFragmentManager,"")
                }

                R.id.iv_item_community_header ->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
                }

                R.id.tv_item_community_name->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
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

                R.id.iv_item_community_play -> {
                    intentUtils.intentVideo(adapter!!.data[position].video)
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
                        dialog.show(childFragmentManager,"")
                    }
                }

            }
        }
    }

    override fun getCommunityNearbyRequest(data: CommunityBean) {
        swip_community.isRefreshing=false

        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else {
                adapter!!.addData(data.data)
            }
        }else {
            adapter = CommunityAdapter(data.data,false)
            manage = ScrollLinearLayoutManager(activity!!)
            manage.orientation = LinearLayout.VERTICAL
            manage.setmCanVerticalScroll(true)
            recy_community.layoutManager = manage
            recy_community.adapter = adapter
            adapter!!.setEmptyView(layoutUtils.getNoneCommunity())
        }
        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<9){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            pos = position
            intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                if (topicId==""){
                    presenter.getCommunityNearby(CommunityBody(pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
                }else{
                    presenter.getCommunityNearby(CommunityBody(topicId, pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
                }
            }
        },recy_community)

        swip_community.setOnRefreshListener {
            pageIndex=1
            if (topicId==""){
                presenter.getCommunityNearby(CommunityBody(pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunityNearby(CommunityBody(topicId, pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
            }
        }

        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            pos = position
            when(view.id){
                R.id.iv_item_community_more -> {
                    var id=""
                    try {
                        var user=DBUtils.getUser()
                        id=user.userId
                    }catch (e:Exception){
                        try {
                            var user=DBUtils.getBusiness()
                            id=user.businessId
                        }catch (e:Exception){}
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
                                dialog.show(childFragmentManager,"")
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
                                dialog.show(childFragmentManager,"")
                            }
                        }

                        Click.viewClick(popView.findViewById(R.id.tv_report)).subscribe {
                            pop.dismiss()
                            if (isLogin) {
                                var dialog = CommunityReportDialog(adapter!!.data[position].id.toString())
                                dialog.show(activity!!.supportFragmentManager, "")
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(childFragmentManager,"")
                            }
                        }

//                        Click.viewClick(popView.findViewById(R.id.tv_uninterested)).subscribe {
//                            pop.dismiss()
//                            Toast.Tips("          操作成功\n" +
//                                    "将减少同类信息推荐")
//                        }
                    }else if(id==adapter!!.data[position].user.id.toString()){
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
                                dialog.show(childFragmentManager,"")
                            }
                        }
                    }
                }

                R.id.layout_comment ->{
                    intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
                }

                R.id.layout_like ->{
                    if (isLogin) {
                        v = adapter!!.getViewByPosition(position, R.id.tv_item_community_like) as TextView
                        pos = position
                        isLike = adapter!!.data[position].isLike == 1
                        presenter.getLike(LikeBody(adapter!!.data[position].id, 1, 2))
                    }else{
                        var dialog= LoginDialog()
                        dialog.show(childFragmentManager,"")
                    }
                }

                R.id.tv_item_community_topic ->{
                    intentUtils.intentTopicDetails(adapter!!.data[position].topicId.toString(),adapter!!.data[position].topicName)
                }

                R.id.layout_share ->{
                    share_id=adapter!!.data[position].id.toString()
                    shareDialog.show(childFragmentManager,"")
                }

                R.id.iv_item_community_header ->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
                }

                R.id.tv_item_community_name->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
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

                R.id.iv_item_community_play -> {
                    intentUtils.intentVideo(adapter!!.data[position].video)
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
                        dialog.show(childFragmentManager,"")
                    }
                }

            }
        }
    }

    override fun getCommunityCityRequest(data: CommunityBean) {
        swip_community.isRefreshing=false

        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else {
                adapter!!.addData(data.data)
            }
        }else {
            adapter = CommunityAdapter(data.data,false)
            manage = ScrollLinearLayoutManager(activity!!)
            manage.orientation = LinearLayout.VERTICAL
            manage.setmCanVerticalScroll(true)
            recy_community.layoutManager = manage
            recy_community.adapter = adapter
        }
        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<9){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            pos = position
            intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                if (topicId==""){
                    presenter.getCommunityCity(CommunityBody(pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
                }else{
                    presenter.getCommunityCity(CommunityBody(topicId, pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
                }
            }
        },recy_community)

        swip_community.setOnRefreshListener {
            pageIndex=1
            if (topicId==""){
                presenter.getCommunityCity(CommunityBody(pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunityCity(CommunityBody(topicId, pageIndex, pageSize,user.getLocationLng(),user.getLocationLat()))
            }
        }

        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            pos = position
            when(view.id){
                R.id.iv_item_community_more -> {
                    var id=""
                    try {
                        var user=DBUtils.getUser()
                        id=user.userId
                    }catch (e:Exception){
                        try {
                            var user=DBUtils.getBusiness()
                            id=user.businessId
                        }catch (e:Exception){}
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
                                dialog.show(childFragmentManager,"")
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
                                dialog.show(childFragmentManager,"")
                            }
                        }

                        Click.viewClick(popView.findViewById(R.id.tv_report)).subscribe {
                            pop.dismiss()
                            if (isLogin) {
                                var dialog = CommunityReportDialog(adapter!!.data[position].id.toString())
                                dialog.show(activity!!.supportFragmentManager, "")
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(childFragmentManager,"")
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
                                dialog.show(childFragmentManager,"")
                            }
                        }
                    }
                }

                R.id.layout_comment ->{
                    intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
                }

                R.id.layout_like ->{
                    if (isLogin) {
                        v = adapter!!.getViewByPosition(position, R.id.tv_item_community_like) as TextView
                        pos = position
                        isLike = adapter!!.data[position].isLike == 1
                        presenter.getLike(LikeBody(adapter!!.data[position].id, 1, 2))
                    }else{
                        var dialog= LoginDialog()
                        dialog.show(childFragmentManager,"")
                    }
                }

                R.id.tv_item_community_topic ->{
                    intentUtils.intentTopicDetails(adapter!!.data[position].topicId.toString(),adapter!!.data[position].topicName)
                }

                R.id.layout_share ->{
                    share_id=adapter!!.data[position].id.toString()
                    shareDialog.show(childFragmentManager,"")
                }

                R.id.iv_item_community_header ->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
                }

                R.id.tv_item_community_name->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
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

                R.id.iv_item_community_play -> {
                    intentUtils.intentVideo(adapter!!.data[position].video)
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
                        dialog.show(childFragmentManager,"")
                    }
                }

            }
        }
    }

    override fun getCommunityFocusRequest(data: CommunityBean) {
        dismiss()
        swip_community.isRefreshing=false

        if (adapter!=null){
            if (pageIndex==1){
                Log.e("测试","刷新")
                adapter!!.setNewData(data.data)
            }else {
                adapter!!.addData(data.data)
            }
        }else {
            adapter = CommunityAdapter(data.data,true)
            manage = ScrollLinearLayoutManager(activity!!)
            manage.orientation = LinearLayout.VERTICAL
            manage.setmCanVerticalScroll(true)
            recy_community.layoutManager = manage
            recy_community.adapter = adapter
        }
        adapter!!.setLoadMoreView(CustomLoadMoreView())
        focusAdapter=adapter
        if (data.data.size<9){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            pos = position
            intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
            user.setDelId(position.toString())
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                if (topicId==""){
                    presenter.getCommunityFocus(CommunityBody(pageIndex, pageSize))
                }else{
                    presenter.getCommunityFocus(CommunityBody(topicId, pageIndex, pageSize))
                }
            }
        },recy_community)

        swip_community.setOnRefreshListener {
            pageIndex=1
            if (topicId==""){
                presenter.getCommunityFocus(CommunityBody(pageIndex, pageSize))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunityFocus(CommunityBody(topicId, pageIndex, pageSize))
            }
        }

        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            pos = position
            when(view.id){
                R.id.iv_item_community_more -> {
                    var id=""
                    try {
                        var user=DBUtils.getUser()
                        id=user.userId
                    }catch (e:Exception){
                        try {
                            var user=DBUtils.getBusiness()
                            id=user.businessId
                        }catch (e:Exception){}
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
                                dialog.show(childFragmentManager,"")
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
                                dialog.show(childFragmentManager,"")
                            }
                        }

                        Click.viewClick(popView.findViewById(R.id.tv_report)).subscribe {
                            pop.dismiss()
                            if (isLogin) {
                                var dialog = CommunityReportDialog(adapter!!.data[position].id.toString())
                                dialog.show(activity!!.supportFragmentManager, "")
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(childFragmentManager,"")
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
                                dialog.show(childFragmentManager,"")
                            }
                        }
                    }
                }

                R.id.layout_comment ->{
                    intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
                    user.setDelId(position.toString())
//                    Log.e("测试posi",position.toString())
                }

                R.id.layout_like ->{
                    if (isLogin) {
                        v = adapter!!.getViewByPosition(position, R.id.tv_item_community_like) as TextView
                        pos = position
                        isLike = adapter!!.data[position].isLike == 1
                        presenter.getLike(LikeBody(adapter!!.data[position].id, 1, 2))
                    }else{
                        var dialog= LoginDialog()
                        dialog.show(childFragmentManager,"")
                    }
                }

                R.id.tv_item_community_topic ->{
                    intentUtils.intentTopicDetails(adapter!!.data[position].topicId.toString(),adapter!!.data[position].topicName)
                }

                R.id.layout_share ->{
                    share_id=adapter!!.data[position].id.toString()
                    shareDialog.show(childFragmentManager,"")
                }

                R.id.iv_item_community_header ->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
                }

                R.id.tv_item_community_name->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
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

                R.id.iv_item_community_play -> {
                    intentUtils.intentVideo(adapter!!.data[position].video)
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
                        dialog.show(childFragmentManager,"")
                    }
                }

            }
        }
    }

    override fun getCommunityHotRequest(data: CommunityBean) {

        swip_community.isRefreshing=false

        if (adapter!=null){
            if (pageIndex==1){
                adapter!!.setNewData(data.data)
            }else {
                adapter!!.addData(data.data)
            }
        }else {
            adapter = CommunityAdapter(data.data,false)
            manage = ScrollLinearLayoutManager(activity!!)
            manage.orientation = LinearLayout.VERTICAL
            manage.setmCanVerticalScroll(true)
            recy_community.layoutManager = manage
            recy_community.adapter = adapter
        }
        adapter!!.setLoadMoreView(CustomLoadMoreView())
        if (data.data.size<9){
            adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
        }else{
            adapter!!.loadMoreComplete()
        }

        adapter!!.setOnItemClickListener { mAdapter, view, position ->
            pos = position
            intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
        }

        adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageIndex=pageIndex+1
                if (topicId==""){
                    presenter.getCommunityHot(CommunityBody(pageIndex, pageSize))
                }else{
                    presenter.getCommunityHot(CommunityBody(topicId, pageIndex, pageSize))
                }
            }
        },recy_community)

        swip_community.setOnRefreshListener {
            pageIndex=1
            if (topicId==""){
                presenter.getCommunityHot(CommunityBody(pageIndex, pageSize))
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
                presenter.getCommunityHot(CommunityBody(topicId, pageIndex, pageSize))
            }
        }

        adapter!!.setOnItemChildClickListener { mAdapter, view, position ->
            pos = position
            when(view.id){
                R.id.iv_item_community_more -> {
                    var id=""
                    try {
                        var user=DBUtils.getUser()
                        id=user.userId
                    }catch (e:Exception){
                        try {
                            var user=DBUtils.getBusiness()
                            id=user.businessId
                        }catch (e:Exception){}
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
                                dialog.show(childFragmentManager,"")
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
                                dialog.show(childFragmentManager,"")
                            }
                        }

                        Click.viewClick(popView.findViewById(R.id.tv_report)).subscribe {
                            pop.dismiss()
                            if (isLogin) {
                                var dialog = CommunityReportDialog(adapter!!.data[position].id.toString())
                                dialog.show(activity!!.supportFragmentManager, "")
                            }else{
                                var dialog= LoginDialog()
                                dialog.show(childFragmentManager,"")
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
                                dialog.show(childFragmentManager,"")
                            }
                        }
                    }
                }

                R.id.layout_comment ->{
                    intentUtils.intentCommunityDetails(adapter!!.data[position].id.toString())
                }

                R.id.layout_like ->{
                    if (isLogin) {
                        v = adapter!!.getViewByPosition(position, R.id.tv_item_community_like) as TextView
                        pos = position
                        isLike = adapter!!.data[position].isLike == 1
                        presenter.getLike(LikeBody(adapter!!.data[position].id, 1, 2))
                    }else{
                        var dialog= LoginDialog()
                        dialog.show(childFragmentManager,"")
                    }
                }

                R.id.tv_item_community_topic ->{
                    intentUtils.intentTopicDetails(adapter!!.data[position].topicId.toString(),adapter!!.data[position].topicName)
                }

                R.id.layout_share ->{
                    share_id=adapter!!.data[position].id.toString()
                    shareDialog.show(childFragmentManager,"")
                }

                R.id.iv_item_community_header ->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
                }

                R.id.tv_item_community_name->{
                    if (adapter!!.data[position].user.role==1) {//用户
                        intentUtils.intentUserHomePage(adapter!!.data[position].user.id.toString())
                    }else{
                        intentUtils.intentBusinessHomePage(adapter!!.data[position].user.id.toString())
                    }
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

                R.id.iv_item_community_play -> {
                    intentUtils.intentVideo(adapter!!.data[position].video)
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
                        dialog.show(childFragmentManager,"")
                    }
                }

            }
        }
    }

    override fun getTopicRequest(data: TopicBean) {
        list.clear()
        list1.clear()
        list=data.data
        list.forEach {
            list1.add(it.name)
        }
        swip_community.isRefreshing=false
        topicAdapter=TopicAdapter(list)
        var manager= TopLayoutManager(activity)
        manager.orientation= LinearLayout.HORIZONTAL
        recy_hot_topic.layoutManager=manager
        recy_hot_topic.adapter=topicAdapter
        chooseTopic.setList(list1)

        topicAdapter.setOnItemClickListener { mAdapter, view, position ->

            if (topicAdapter.data[position].isFlag==true){
                topicAdapter.data[position].isFlag = false
                topicAdapter.notifyDataSetChanged()
                chooseTopic.cancelAllSelectedItems()
                topicId=""
                setList()
            }else {
                topicAdapter.data[position].isFlag = true
                topicId=topicAdapter.data[position].id.toString()
                setTopicList()
                topicAdapter.data.forEach {
                    if (topicAdapter.data[position].name != it.name) {
                        it.isFlag = false
                    }
                }
                topicAdapter.notifyDataSetChanged()
                chooseTopic.cancelAllSelectedItems()
                chooseTopic.setIndexItemSelected(position)
                recy_hot_topic.smoothScrollToPosition(position)
            }
        }

        chooseTopic.setOnItemClickListener { position, text ->
            if (list[position].isFlag==true){
                chooseTopic.cancelAllSelectedItems()
                topicAdapter.data[position].isFlag=false
                topicAdapter.notifyDataSetChanged()
                topicId=""
                setList()
            }else {
                topicAdapter.data.forEachIndexed { index, topicBean ->
                    if (position == index) {
                        topicBean.isFlag = true
                        topicId=topicBean.id.toString()
                        setTopicList()
                    } else {
                        topicBean.isFlag = false
                    }
                }
                topicAdapter.notifyDataSetChanged()
                recy_hot_topic.smoothScrollToPosition(position)
            }
            layout_content.visibility= View.VISIBLE
            scroll_topic.visibility= View.GONE
        }
    }

    override fun getTopicError() {
        swip_community.isRefreshing=false
        Log.e("测试","失败话题")
        swip_community.setOnRefreshListener {
            Log.e("测试","失败话题刷新")
            pageIndex=1
            if (topicId==""){
//                presenter.getCommunityFocus(CommunityBody(pageIndex, pageSize))
                setList()
                try {
                    var user=DBUtils.getUser()
                    presenter.getTopicList(TopicBody(user.token))
                }catch (e:Exception){
                    try {
                        var business=DBUtils.getBusiness()
                        presenter.getTopicList(TopicBody(business.token))
                    }catch (e:Exception){
                        presenter.getTopicList(TopicBody(""))
                    }

                }
            }else{
//                presenter.getCommunityFocus(CommunityBody(topicId, pageIndex, pageSize))
                setList()
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


    private val presenter by lazy { CommunityHotFragmentPresenter(this,this,activity!!) }
    private lateinit var manage:ScrollLinearLayoutManager
    private lateinit var pop: PopupWindowHelper
    private lateinit var popView: View
    private lateinit var shareDialog:ShareDialog
    private  var adapter:CommunityAdapter?=null
    private var topicId=""
    private var pageIndex=1
    private var pageSize=9


    private lateinit var topicAdapter: TopicAdapter
    var list=ArrayList<TopicBean.DataBean>()
    var list1=ArrayList<String>()
    private var isLogin=false
    private var user_id=""

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
                isLogin=true
            }else if (inf != null && inf.size > 0) {
                isLogin=true
            }else{
                isLogin=false
            }
        } else{
            isLogin=false
        }

        setList()
        isCreated=true

        shareDialog= ShareDialog(this)

        if (type=="关注"){
            layout_topic.visibility=View.GONE
        }else{
            layout_topic.visibility=View.VISIBLE
        }

        try {
            var user=DBUtils.getUser()
            presenter.getTopicList(TopicBody(user.token))
        }catch (e:Exception){
            try {
                var business=DBUtils.getBusiness()
                presenter.getTopicList(TopicBody(business.token))
            }catch (e:Exception){
                presenter.getTopicList(TopicBody(""))
            }

        }


    }

    override fun setFragmentListener() {

//        swip_community.setOnRefreshListener {
//            swip_community.isRefreshing=false
//        }

        Click.viewClick(iv_topic_down).subscribe {
            layout_content.visibility= View.GONE
            scroll_topic.visibility= View.VISIBLE

        }

        Click.viewClick(iv_topic_top).subscribe {
            layout_content.visibility= View.VISIBLE
            scroll_topic.visibility= View.GONE
        }

    }

    override fun layoutID(): Int = R.layout.fragment_community_hot

    fun setList(){
        pageIndex=1
        when(type){
            "热门"->{
                presenter.getCommunityHot(CommunityBody(pageIndex, pageSize))
            }

            "广场"->{
                presenter.getCommunitySquare(CommunityBody(pageIndex, pageSize))
            }

            "附近"->{
                UserPermissions.userLocation(activity!!, this)
            }

            "同城"->{
                UserPermissions.userLocation(activity!!, this)
            }

            "关注"->{
                try {
                    if (DBUtils.getUser().token!=null&&DBUtils.getUser().token!=""){
                        presenter.getCommunityFocus(CommunityBody(pageIndex, pageSize))
                    }
                }catch (e:Exception){
                    try {
                        if (DBUtils.getBusiness().token!=null&&DBUtils.getBusiness().token!=""){
                            presenter.getCommunityFocus(CommunityBody(pageIndex, pageSize))
                        }
                    }catch (e:Exception){

                    }

                }

            }
        }
    }

    fun setTopicList(){
        pageIndex=1
        when(type){
            "热门"->{
                presenter.getCommunityHot(CommunityBody(topicId,pageIndex, pageSize))
            }

            "广场"->{
                presenter.getCommunitySquare(CommunityBody(topicId,pageIndex, pageSize))
            }

            "附近"->{
                UserPermissions.userLocation(activity!!, this)
            }

            "同城"->{
                UserPermissions.userLocation(activity!!, this)
            }

        }
    }

    fun setScroll(flag:Boolean){
        manage.setmCanVerticalScroll(flag)
    }

    fun scrollTop(){
        recy_community.smoothScrollToPosition(0)
    }

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (!isCreated) {
//            return
//        }
//
//        if (userVisibleHint) {
//            setList()
//        }
//
//
//    }

    fun setRelease(){
        Log.e("测试","刷新")
//        swip_community.setOnRefreshListener {

//        swip_community.isRefreshing=false
            pageIndex=1
//        try {
//            var user=DBUtils.getUser()
//            presenter.getTopicList(TopicBody(user.token))
//        }catch (e:Exception){
//            try {
//                var business=DBUtils.getBusiness()
//                presenter.getTopicList(TopicBody(business.token))
//            }catch (e:Exception){
//                presenter.getTopicList(TopicBody(""))
//            }
//
//        }
//        }
        presenter.getCommunityFocus(CommunityBody(pageIndex, pageSize))
//        try {
//            var user=DBUtils.getUser()
//            presenter.getTopicList(TopicBody(user.token))
//        }catch (e:Exception){
//            try {
//                var business=DBUtils.getBusiness()
//                presenter.getTopicList(TopicBody(business.token))
//            }catch (e:Exception){
//                presenter.getTopicList(TopicBody(""))
//            }
//
//        }

    }

    override fun onDestroy() {
        super.onDestroy()

//        val refWatcher = AppProject.getRefWatcher(context!!)
//        refWatcher.watch(this)
        GlideCacheUtil.getInstance().clearImageMemoryCache(context!!)
        GlideCacheUtil.getInstance().clearImageAllCache(context!!)//清除图片所有缓存
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden){
            GlideCacheUtil.getInstance().clearImageMemoryCache(context!!)
            GlideCacheUtil.getInstance().clearImageAllCache(context!!)//清除图片所有缓存
        }
    }


    private var focusAdapter:CommunityAdapter?=null
    override fun onResume() {
        super.onResume()
        if (focusAdapter!=null&&focusAdapter!!.data.size>0&&user.getIsDelMine()){
//            adapter!!.data.removeAt(user.getDelId().toInt())
            focusAdapter!!.remove(user.getDelId().toInt())
            user.setIsDelMine(false)
        }
    }


    fun setLike(like:Boolean){
        isLike=like
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



}