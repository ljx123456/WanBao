package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean

interface AtDialogView:BaseView{
    fun getSearchUserRequest(data:FansBean)
    fun getSearchFansRequest(data:FansBean)
}