package wanbao.yongchao.com.wanbao.ui.login.mvp.data

import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.UserBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.RegisterUserBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class RegisterUserData(val user:RegisterUser): BaseData<UserBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCode(body: RegisterUserBody){
        api(Api.getApi().getRegisterUser(body)).build()
    }

    override fun onSucceedRequest(data: UserBean, what: Int) {
        user.getRegisterUserRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        user.getRegisterUserError()
    }

    interface RegisterUser{
        fun getRegisterUserRequest(data: UserBean)
        fun getRegisterUserError()
    }
}