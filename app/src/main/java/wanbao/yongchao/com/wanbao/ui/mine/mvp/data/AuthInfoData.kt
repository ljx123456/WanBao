package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthInfoBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class AuthInfoData(val edit:AuthInfo): BaseData<AuthInfoBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getAuthInfo(body: AuthInfoBody){
        api(Api.getApi().getAuthInfo(body)).build()
    }

    override fun onSucceedRequest(data: AuthInfoBean, what: Int) {
        edit.getAuthInfoRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        edit.getAuthInfoError()
    }

    interface AuthInfo{
        fun getAuthInfoRequest(data: AuthInfoBean)
        fun getAuthInfoError()
    }
}