package wanbao.yongchao.com.wanbao.ui.release.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ClockAddressBean

interface ReleaseAddressView:BaseView{
    fun getReleaseAddressRequest(data:ClockAddressBean)

    fun getSearchAddressRequest(data:ClockAddressBean)
}