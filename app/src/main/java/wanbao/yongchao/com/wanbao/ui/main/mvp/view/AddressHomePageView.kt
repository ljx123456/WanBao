package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddressHomePageBean

interface AddressHomePageView:BaseView{
    fun getAddressHomePageRequest(data:AddressHomePageBean)

    fun getAddressHomePageError()
}