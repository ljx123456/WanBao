package wanbao.yongchao.com.wanbao.ui.login.mvp.data

import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.LoginCodeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.LoginCodeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class LoginCodeData(val login:LoginCode): BaseData<LoginCodeBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getLoginCode(body: LoginCodeBody){
        api(Api.getApi().getLoginCode(body)).build()
    }

    override fun onSucceedRequest(data: LoginCodeBean, what: Int) {
        login.getLoginCodeRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        login.getLoginCodeError()
    }

    interface LoginCode{
        fun getLoginCodeRequest(data: LoginCodeBean)
        fun getLoginCodeError()
    }
}