package wanbao.yongchao.com.wanbao.ui.news.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeAtBean

interface NoticeAtView: BaseView {
    fun NoticeAtRequest(data: NoticeAtBean)
    fun getDelNoticeRequset()
}