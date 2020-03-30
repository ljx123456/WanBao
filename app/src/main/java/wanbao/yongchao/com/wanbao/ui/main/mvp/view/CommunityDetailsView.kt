package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDetailsBean

interface CommunityDetailsView:BaseView{
    fun getCommunityDetailsRequest(data: CommunityDetailsBean)
    fun getCommunityDetailsError()

    fun getCommunityDelRequest()

    fun getFocusRequest()

    fun getUnFocusRequest()

    fun getLikeRequest()

    fun getCollectRequest()
}