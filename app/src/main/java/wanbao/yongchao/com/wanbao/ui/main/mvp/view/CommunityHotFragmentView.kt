package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.TopicBean

interface CommunityHotFragmentView:BaseView{
    fun getTopicRequest(data:TopicBean)
    fun getTopicError()

    fun getCommunityHotRequest(data:CommunityBean)
    fun getCommunityHotError()

    fun getCommunitySquareRequest(data:CommunityBean)
    fun getCommunitySquareError()

    fun getCommunityNearbyRequest(data:CommunityBean)
    fun getCommunityNearbyError()

    fun getCommunityCityRequest(data:CommunityBean)
    fun getCommunityCityError()

    fun getCommunityFocusRequest(data:CommunityBean)
    fun getCommunityFocusError()

    fun getCommunityDelRequest()

    fun getFocusRequest()

    fun getUnFocusRequest()

    fun getLikeRequest()

    fun getCollectRequest()
}