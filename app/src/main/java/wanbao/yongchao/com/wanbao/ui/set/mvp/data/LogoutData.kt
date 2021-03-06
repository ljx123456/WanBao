package wanbao.yongchao.com.wanbao.ui.set.mvp.data

import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.LogoutBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.LogoutBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class LogoutData(val change:Logout): BaseData<LogoutBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getLogout(body: LogoutBody){
        api(Api.getApi().getLogout(body)).build()
    }

    override fun onSucceedRequest(data: LogoutBean, what: Int) {
        change.getLogoutRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        change.getLogoutError()
    }

    interface Logout{
        fun getLogoutRequest(data: LogoutBean)
        fun getLogoutError()
    }
}