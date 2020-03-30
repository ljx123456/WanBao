package wanbao.yongchao.com.wanbao.ui.news.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NewsBean

interface NewsView:BaseView{
    fun getNewsRequest(data:NewsBean)

    fun delSystemNotice()
}