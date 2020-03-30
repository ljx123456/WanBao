package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.SearchHotShopBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchHotShopData(val shop:SearchHotShop): BaseData<SearchHotShopBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchHotShop(body: SearchHotShopBody){
        api(Api.getApi().getSearchHotShop(body)).build()
    }

    override fun onSucceedRequest(data: SearchHotShopBean, what: Int) {
        shop.getSearchHotShopRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        shop.getSearchHotShopError()
    }

    interface SearchHotShop{
        fun getSearchHotShopRequest(data: SearchHotShopBean)
        fun getSearchHotShopError()
    }
}