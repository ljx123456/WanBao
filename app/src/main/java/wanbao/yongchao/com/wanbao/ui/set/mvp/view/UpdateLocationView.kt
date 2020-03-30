package wanbao.yongchao.com.wanbao.ui.set.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateLocationBean

interface UpdateLocationView:BaseView{
    fun getUpdateLocationRequest(data:UpdateLocationBean)
}