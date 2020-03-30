package wanbao.yongchao.com.wanbao.ui.set.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.NoticeStatusBean

interface NoticeStatusView:BaseView{
    fun getNoticeStatusRequest(data:NoticeStatusBean)

    fun getChangeNoticeStatusRequest()

    fun getChangeNoticeStatusError()
}