package wanbao.yongchao.com.wanbao.ui.set.mvp.data

import wanbao.yongchao.com.wanbao.ui.login.mvp.body.LoginThirdBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.LogoutBean
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ThirdBindData(val change:ThirdBind): BaseData<LogoutBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getThirdBind(body: LoginThirdBody){
        api(Api.getApi().getThirdBind(body)).build()
    }

    override fun onSucceedRequest(data: LogoutBean, what: Int) {
        change.getThirdBindRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        change.getThirdBindError()
    }

    interface ThirdBind{
        fun getThirdBindRequest(data: LogoutBean)
        fun getThirdBindError()
    }
}