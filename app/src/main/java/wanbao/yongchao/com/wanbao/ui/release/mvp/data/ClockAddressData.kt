package wanbao.yongchao.com.wanbao.ui.release.mvp.data

import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ClockAddressBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.ClockAddressBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ClockAddressData(val clock:ClockAddress): BaseData<ClockAddressBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getClockAddress(body: ClockAddressBody){
        api(Api.getApi().getClockAddress(body)).build()
    }

    override fun onSucceedRequest(data: ClockAddressBean, what: Int) {
        clock.getClockAddressRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        clock.getClockAddressError()
    }

    interface ClockAddress{
        fun getClockAddressRequest(data: ClockAddressBean)
        fun getClockAddressError()
    }
}