package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WebBannerBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WebBannerBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class WebBannerData(val web:WebBanner): BaseData<WebBannerBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getWebBanner(body: WebBannerBody){
        api(Api.getApi().getWebBanner(body)).build()
    }

    override fun onSucceedRequest(data: WebBannerBean, what: Int) {
        web.getWebBannerRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        web.getWebBannerError()
    }

    interface WebBanner{
        fun getWebBannerRequest(data: WebBannerBean)
        fun getWebBannerError()
    }
}