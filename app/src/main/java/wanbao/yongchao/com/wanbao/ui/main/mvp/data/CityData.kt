package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CityData(val city:City): BaseData<CityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCity(body: CityBody){
        api(Api.getApi().getCity(body)).build()
    }

    override fun onSucceedRequest(data: CityBean, what: Int) {
        city.getCityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        city.getCityError()
    }

    interface City{
        fun getCityRequest(data: CityBean)
        fun getCityError()
    }
}