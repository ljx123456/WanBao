package wanbao.yongchao.com.wanbao.ui.news.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeBean

interface NoticeView:BaseView{
    fun getNoticeRequest(data:NoticeBean)
}