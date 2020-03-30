package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchAddressBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchAddressData(val search:SearchAddress): BaseData<SearchAddressBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchAddress(body: SearchBody){
        api(Api.getApi().getSearchAddress(body)).build()
    }

    override fun onSucceedRequest(data: SearchAddressBean, what: Int) {
        search.getSearchAddressRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        search.getSearchAddressError()
    }

    interface SearchAddress{
        fun getSearchAddressRequest(data: SearchAddressBean)
        fun getSearchAddressError()
    }
}