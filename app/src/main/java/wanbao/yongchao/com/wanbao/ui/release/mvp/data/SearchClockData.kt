package wanbao.yongchao.com.wanbao.ui.release.mvp.data

import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ClockAddressBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.SearchClockBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchClockData(val search:SearchClock): BaseData<ClockAddressBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchAddress(body: SearchClockBody){
        api(Api.getApi().getSearchClock(body)).build()
    }

    override fun onSucceedRequest(data: ClockAddressBean, what: Int) {
        search.getSearchAddressRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        search.getSearchAddressError()
    }

    interface SearchClock{
        fun getSearchAddressRequest(data: ClockAddressBean)
        fun getSearchAddressError()
    }
}