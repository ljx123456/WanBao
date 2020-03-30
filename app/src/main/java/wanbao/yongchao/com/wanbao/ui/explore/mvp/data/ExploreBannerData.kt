package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreBannerBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreBannerBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ExploreBannerData(val banner:ExploreBanner): BaseData<ExploreBannerBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getExploreBanner(body: ExploreBannerBody){
        api(Api.getApi().getExploreBanner(body)).build()
    }

    override fun onSucceedRequest(data: ExploreBannerBean, what: Int) {
        banner.getExploreBannerRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        banner.getExploreBannerError()
    }

    interface ExploreBanner{
        fun getExploreBannerRequest(data: ExploreBannerBean)
        fun getExploreBannerError()
    }
}