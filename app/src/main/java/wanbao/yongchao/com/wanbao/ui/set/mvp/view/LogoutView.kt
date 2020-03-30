package wanbao.yongchao.com.wanbao.ui.set.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.LogoutBean

interface LogoutView:BaseView{
    fun getLogoutRequest(data:LogoutBean)
}