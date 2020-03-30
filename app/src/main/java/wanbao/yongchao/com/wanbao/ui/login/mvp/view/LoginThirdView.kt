package wanbao.yongchao.com.wanbao.ui.login.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.LoginThirdBean

interface LoginThirdView:BaseView{
    fun getLoginThirdRequest(data:LoginThirdBean)
}