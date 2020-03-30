package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.util.Log
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.callback.GetUserInfoCallback
import cn.jpush.im.android.api.model.UserInfo
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_news.*
import wanbao.yongchao.com.wanbao.JMessage.ConversationListFragment
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.activity.MainActivity
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AtNearBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.UserInfoBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NewsBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelSystemBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NewsBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.presenter.NewsPresenter
import wanbao.yongchao.com.wanbao.ui.news.mvp.view.NewsView
import wanbao.yongchao.com.wanbao.ui.news.utils.TagUtil
import wanbao.yongchao.com.wanbao.utils.Badge.BadgeHelper
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click

class NewsFragment : BaseFragment(),NewsView{
    override fun delSystemNotice() {
        TagUtil.setTag(false)
        user.setIsDel(true)
    }

    override fun getNewsRequest(data: NewsBean) {
        if(data.data!=null) {
            if (data.data.fansMessageNum > 0) {
                if (badge1 == null) {
                    badge1 = BadgeHelper(context!!).setBadgeType(BadgeHelper.Type.TYPE_TEXT).setBadgeOverlap(true)
                }
                badge1!!.bindToTargetView(tv_fans)
                badge1!!.setBadgeNumber(data.data.fansMessageNum)
                badge1!!.setBadgeEnable(true)
            } else {
                if (badge1 != null) {
                    badge1!!.setBadgeEnable(false)
                }
            }

            if (data.data.likeMessageNum > 0) {
                if (badge2 == null) {
                    badge2 = BadgeHelper(context!!).setBadgeType(BadgeHelper.Type.TYPE_TEXT).setBadgeOverlap(true)
                }
                badge2!!.bindToTargetView(tv_like)
                badge2!!.setBadgeNumber(data.data.likeMessageNum)
                badge2!!.setBadgeEnable(true)
            } else {
                if (badge2 != null) {
                    badge2!!.setBadgeEnable(false)
                }
            }

            if (data.data.atMessageNum > 0) {
                if (badge3 == null) {
                    badge3 = BadgeHelper(context!!).setBadgeType(BadgeHelper.Type.TYPE_TEXT).setBadgeOverlap(true)
                }
                badge3!!.bindToTargetView(tv_at)
                badge3!!.setBadgeNumber(data.data.atMessageNum)
                badge3!!.setBadgeEnable(true)
            } else {
                if (badge3 != null) {
                    badge3!!.setBadgeEnable(false)
                }
            }

            if (data.data.commentMessageNum > 0) {
                if (badge4 == null) {
                    badge4 = BadgeHelper(context!!).setBadgeType(BadgeHelper.Type.TYPE_TEXT).setBadgeOverlap(true)
                }
                badge4!!.bindToTargetView(tv_comment)
                badge4!!.setBadgeNumber(data.data.commentMessageNum)
                badge4!!.setBadgeEnable(true)
            } else {
                if (badge4 != null) {
                    badge4!!.setBadgeEnable(false)
                }
            }

            if (data.data.systemMessage != null && data.data.systemMessage != "") {
                TagUtil.setTag(true)
                TagUtil.setContent(data.data.systemMessage)
                TagUtil.setTime(data.data.systemMessageCreateTime)
                TagUtil.setNum(data.data.systemMessageNum.toString())
            } else {
                TagUtil.setTag(false)
            }

            if (data.data.systemMessageNum > 0 || data.data.commentMessageNum > 0 || data.data.atMessageNum > 0 || data.data.likeMessageNum > 0 && data.data.fansMessageNum > 0) {
                (activity as MainActivity).setNews(true)
            } else {
                try {
                    if (JMessageClient.getAllUnReadMsgCount() != null && JMessageClient.getAllUnReadMsgCount() > 0) {
                        (activity as MainActivity).setNews(true)
                    } else {
                        (activity as MainActivity).setNews(false)
                    }
                } catch (e: Exception) {
                    (activity as MainActivity).setNews(false)
                }
            }

            try {
                if (fragment != null) {
//                fragment!!.setHasNotice(true)
                    if (data.data.systemMessage != null && data.data.systemMessage != "" && !user.getIsDel()) {
                        fragment!!.setNotice(data.data.systemMessage, data.data.systemMessageCreateTime, data.data.systemMessageNum.toString())
                    }

                } else {
                    initFragment()

                    if (data.data.systemMessage != null && data.data.systemMessage != "" && !user.getIsDel()) {
                        fragment!!.setNotice(data.data.systemMessage, data.data.systemMessageCreateTime, data.data.systemMessageNum.toString())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

//        var bage2=BadgeHelper(context!!).setBadgeType(BadgeHelper.Type.TYPE_TEXT).setBadgeOverlap(true)
//        bage2.bindToTargetView(tv_comment)
//        bage2.setBadgeNumber(23)
    }

    private var fragment :ConversationListFragment?=null
    private val presenter by lazy { NewsPresenter(this,this,activity!!) }
    private var token=""
    private var badge1:BadgeHelper?=null
    private var badge2:BadgeHelper?=null
    private var badge3:BadgeHelper?=null
    private var badge4:BadgeHelper?=null

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        tv_at.text="@我"

//        initFragment()
    }

    override fun setFragmentListener() {
        Click.viewClick(tv_fans).subscribe {
            intentUtils.intentNoticeFans()
        }

        Click.viewClick(tv_like).subscribe {
            intentUtils.intentNoticeLike()
        }

        Click.viewClick(tv_at).subscribe {
            intentUtils.intentNoticeAt()
        }

        Click.viewClick(tv_comment).subscribe {
            intentUtils.intentNoticeComment()
        }
    }

    override fun layoutID(): Int = R.layout.fragment_news

    //设置初始显示的Fragment
    fun initFragment() {
        fragment = ConversationListFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container_user, fragment!!)
        transaction.commitAllowingStateLoss()
//        transaction.show(fragment!!)
    }

    fun delSystem(){
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                presenter.delSystem(DelSystemBody(info.get(0).token))
            }else if (inf != null && inf.size > 0) {
                presenter.delSystem(DelSystemBody(inf.get(0).token))
            }
        }
    }

//    override fun onHiddenChanged(hidden: Boolean) {
//        super.onHiddenChanged(hidden)
//        if (!hidden){
//            Log.e("hidden","测试2")
//            try {
//                initFragment()
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//
//    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            Log.e("可见","测试3")
            var user = GreenDaoHelper.getDaoSessions().userDBDao
            var business= GreenDaoHelper.getDaoSessions().businessDBDao
            if (user != null||business!=null) {
                var info = user.loadAll()
                var inf = business.loadAll()
                if (info != null && info.size > 0) {
                    presenter.getNews(NewsBody(info.get(0).token))
                }else if (inf != null && inf.size > 0) {
                    presenter.getNews(NewsBody(inf.get(0).token))
                }
                try {
                    var list=ArrayList<FansBean.DataBean>()
                    var newList = ArrayList<FansBean.DataBean>()
                    var mCache= ACache.get(activity!!)
                    if (mCache.getAsString("NearUser") != null && !"".equals(mCache.getAsString("NearUser"))) {
                        list.addAll(JSONObject.parseArray(mCache.getAsString("NearUser"), FansBean.DataBean::class.java))
                    }
                    if (JMessageClient.getConversationList()!=null&& JMessageClient.getConversationList().size!=null&&JMessageClient.getConversationList().size>0){
                        JMessageClient.getConversationList().forEach {
                            Log.e("测试",JMessageClient.getConversationList().size.toString())
                            JMessageClient.getUserInfo((it.targetInfo as UserInfo).userName,(it.targetInfo as UserInfo).appKey,object :GetUserInfoCallback(){
                                override fun gotResult(p0: Int, p1: String?, p2: UserInfo) {
                                    if (p2 != null && p2.nickname != null && p2.nickname != "") {
                                        var userInfo = p2
                                        if (list.size > 0) {
                                            var flag = true
                                            list.forEach {
                                                if (it.id.toString() == userInfo.userName) {
                                                    flag = false
                                                }
                                            }
                                            if (flag) {
                                                Log.e("测试聊天对象", userInfo.userName)
                                                Log.e("测试聊天历史", it.id.toString())
                                                var bean = FansBean.DataBean()
                                                bean.id = userInfo.userName
                                                bean.avatar = userInfo.avatar
                                                bean.nickname = userInfo.nickname
                                                bean.isFlag = false
                                                newList.add(bean)
                                            }
                                        } else {
                                            Log.e("测试聊天对象", userInfo.userName)
                                            var bean = FansBean.DataBean()
                                            bean.id = userInfo.userName
                                            bean.avatar = userInfo.avatar
                                            bean.nickname = userInfo.nickname
                                            bean.isFlag = false
                                            newList.add(bean)
                                        }
                                    }
                                }
                            })

                        }

                        if (newList.size>0){
                            list.addAll(newList)
                        }

                        if (list.size>0) {
                            mCache.put("NearUser", Gson().toJson(list))
                        }
                    }


                }catch (e:Exception){}

            }

        }
    }
//
    override fun onResume() {
        super.onResume()
        Log.e("准备","测试2")

        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business = GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null || business != null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                presenter.getNews(NewsBody(info.get(0).token))
            } else if (inf != null && inf.size > 0) {
                presenter.getNews(NewsBody(inf.get(0).token))
            }
            var list = ArrayList<FansBean.DataBean>()
            var newList = ArrayList<FansBean.DataBean>()
            var mCache = ACache.get(activity!!)
            if (mCache.getAsString("NearUser") != null && !"".equals(mCache.getAsString("NearUser"))) {
                list.addAll(JSONObject.parseArray(mCache.getAsString("NearUser"), FansBean.DataBean::class.java))
            }
            try {
                if (JMessageClient.getConversationList() != null&& JMessageClient.getConversationList().size!=null && JMessageClient.getConversationList().size > 0) {
                    JMessageClient.getConversationList().forEach {
                        JMessageClient.getUserInfo((it.targetInfo as UserInfo).userName,(it.targetInfo as UserInfo).appKey,object :GetUserInfoCallback(){
                            override fun gotResult(p0: Int, p1: String?, p2: UserInfo) {
                                if (p2 != null && p2.nickname != null && p2.nickname != "") {
                                    var userInfo = p2
                                    if (list.size > 0) {
                                        var flag = true
                                        list.forEach {
                                            if (it.id.toString() == userInfo.userName) {
                                                flag = false
                                            }
                                        }
                                        if (flag) {
                                            Log.e("测试聊天对象", userInfo.userName)
                                            Log.e("测试聊天历史", it.id.toString())
                                            var bean = FansBean.DataBean()
                                            bean.id = userInfo.userName
                                            bean.avatar = userInfo.avatar
                                            bean.nickname = userInfo.nickname
                                            bean.isFlag = false
                                            newList.add(bean)
                                        }
                                    } else {
                                        Log.e("测试聊天对象", userInfo.userName)
                                        var bean = FansBean.DataBean()
                                        bean.id = userInfo.userName
                                        bean.avatar = userInfo.avatar
                                        bean.nickname = userInfo.nickname
                                        bean.isFlag = false
                                        newList.add(bean)
                                    }
                                }
                            }
                        })
                    }

                    if (newList.size>0){
                        list.addAll(newList)
                    }

                    if (list.size > 0) {
                        mCache.put("NearUser", Gson().toJson(list))
                    }
                }
            }catch (e:Exception){

            }

        }
    }


}
