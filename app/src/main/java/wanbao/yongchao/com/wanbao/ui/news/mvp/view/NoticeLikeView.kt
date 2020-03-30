package wanbao.yongchao.com.wanbao.ui.news.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeLikeBean

interface NoticeLikeView:BaseView{
    fun NoticeLikeRequest(data:NoticeLikeBean)
    fun getDelNoticeRequset()
}