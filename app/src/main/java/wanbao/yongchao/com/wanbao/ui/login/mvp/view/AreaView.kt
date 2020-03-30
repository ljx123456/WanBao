package wanbao.yongchao.com.wanbao.ui.login.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.AreaBean

interface AreaView:BaseView{
    fun getAreaRequest(data:AreaBean)
    fun getAreaError()
}