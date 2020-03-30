package wanbao.yongchao.com.wanbao.ui.explore.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WebBannerBean

interface WebBannerView:BaseView{
    fun getWebBannerRequest(data:WebBannerBean)
}