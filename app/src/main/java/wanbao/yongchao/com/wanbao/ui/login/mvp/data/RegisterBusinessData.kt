package wanbao.yongchao.com.wanbao.ui.login.mvp.data

import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.BusinessBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class RegisterBusinessData(val business:RegisterBusiness): BaseData<BusinessBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getRegisterBusiness(body: BusinessBody){
        api(Api.getApi().getRegisterBusiness(body)).build()
    }

    override fun onSucceedRequest(data: BusinessBean, what: Int) {
        business.getRegisterBusinessRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        business.getRegisterBusinessError()
    }

    interface RegisterBusiness{
        fun getRegisterBusinessRequest(data: BusinessBean)
        fun getRegisterBusinessError()
    }
}