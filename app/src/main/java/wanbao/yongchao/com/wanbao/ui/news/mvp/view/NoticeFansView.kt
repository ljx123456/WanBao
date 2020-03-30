package wanbao.yongchao.com.wanbao.ui.news.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeFansBean

interface NoticeFansView:BaseView{
    fun getNoticeFansRequest(data:NoticeFansBean)

    fun getDelNoticeRequset()
}