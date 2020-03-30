package wanbao.yongchao.com.wanbao.ui.login.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.LoginCodeBean

interface LoginCodeView:BaseView{
    fun getLoginCodeRequest(data:LoginCodeBean)
    fun getLoginCodeError()
}