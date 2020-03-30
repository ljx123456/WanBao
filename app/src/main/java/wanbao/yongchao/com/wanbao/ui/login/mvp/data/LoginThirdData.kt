package wanbao.yongchao.com.wanbao.ui.login.mvp.data

import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.LoginThirdBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.LoginThirdBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class LoginThirdData(val login:LoginThird): BaseData<LoginThirdBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getLoginThird(body: LoginThirdBody){
        api(Api.getApi().getLoginThird(body)).build()
    }

    override fun onSucceedRequest(data: LoginThirdBean, what: Int) {
        login.getLoginThirdRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        login.getLoginThirdError()
    }

    interface LoginThird{
        fun getLoginThirdRequest(data: LoginThirdBean)
        fun getLoginThirdError()
    }
}