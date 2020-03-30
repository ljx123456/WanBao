package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddressHomePageBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.AddressHomePageBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class AddressHomePageData(val address:AddressHomePage): BaseData<AddressHomePageBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getAddressHomePage(body: AddressHomePageBody){
        api(Api.getApi().getAddressHomePage(body)).build()
    }

    override fun onSucceedRequest(data: AddressHomePageBean, what: Int) {
        address.getAddressHomePageRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        address.getAddressHomePageError()
    }

    interface AddressHomePage{
        fun getAddressHomePageRequest(data: AddressHomePageBean)
        fun getAddressHomePageError()
    }
}