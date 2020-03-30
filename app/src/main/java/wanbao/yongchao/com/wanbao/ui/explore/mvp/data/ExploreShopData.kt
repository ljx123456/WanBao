package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreShopBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ExploreShopData(val shop:ExploreShop): BaseData<ExploreShopBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getExploreShop(body: ExploreShopBody){
        api(Api.getApi().getExploreShop(body)).build()
    }

    override fun onSucceedRequest(data: ExploreShopBean, what: Int) {
        shop.getExploreShopRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        shop.getExploreShopError()
    }

    interface ExploreShop{
        fun getExploreShopRequest(data: ExploreShopBean)
        fun getExploreShopError()
    }
}