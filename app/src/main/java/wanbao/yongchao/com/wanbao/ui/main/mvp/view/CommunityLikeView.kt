package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityLikeBean

interface CommunityLikeView:BaseView{
    fun getCommunityLikeRequest(data:CommunityLikeBean)
    fun getCommunityLikeError()
}