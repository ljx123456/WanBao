package wanbao.yongchao.com.wanbao.ui.login.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.UserBean

interface RegisterUserView:BaseView{
    fun getRegisterUserRequest(data:UserBean)
    fun getRegisterUserError()
}