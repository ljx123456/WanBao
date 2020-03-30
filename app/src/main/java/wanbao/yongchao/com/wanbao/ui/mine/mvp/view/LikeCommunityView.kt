package wanbao.yongchao.com.wanbao.ui.mine.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean

interface LikeCommunityView:BaseView{
    fun getHomePageCommunityRequest(data:CommunityBean)

    fun getCommunityDelRequest()

    fun getFocusRequest()

    fun getUnFocusRequest()

    fun getLikeRequest()

    fun getCollectRequest()
}