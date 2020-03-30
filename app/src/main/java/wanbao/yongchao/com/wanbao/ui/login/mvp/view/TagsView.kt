package wanbao.yongchao.com.wanbao.ui.login.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean

interface TagsView:BaseView{
    fun getTagsRequest(data:TagsBean)
}