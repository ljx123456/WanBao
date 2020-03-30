package wanbao.yongchao.com.wanbao.ui.news.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeCommentBean


interface NoticeCommentView:BaseView{
    fun NoticeCommentRequest(data:NoticeCommentBean)
    fun getDelNoticeRequset()
}