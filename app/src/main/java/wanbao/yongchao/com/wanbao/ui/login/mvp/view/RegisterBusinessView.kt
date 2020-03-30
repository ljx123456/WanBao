package wanbao.yongchao.com.wanbao.ui.login.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessBean

interface RegisterBusinessView:BaseView{

    fun getRegisterBusinessRequest(data:BusinessBean)

    fun getRegisterBusinessError()
}