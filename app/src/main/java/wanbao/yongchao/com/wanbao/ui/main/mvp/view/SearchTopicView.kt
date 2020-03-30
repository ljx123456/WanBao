package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchTopicBean

interface SearchTopicView:BaseView{
    fun getSearchTopicRequest(data:SearchTopicBean)
}