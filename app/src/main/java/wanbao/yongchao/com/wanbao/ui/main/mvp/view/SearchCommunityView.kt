package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean

interface SearchCommunityView:BaseView{
    fun getSearchCommunityRequest(data:CommunityBean)

    fun getCommunityDelRequest()

    fun getFocusRequest()

    fun getUnFocusRequest()

    fun getLikeRequest()

    fun getCollectRequest()
}