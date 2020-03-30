package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthRealNameBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthRealNameBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class AuthRealNameData(val auth:AuthRealName): BaseData<AuthRealNameBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getAuthRealName(body: AuthRealNameBody){
        api(Api.getApi().getAuthRealName(body)).build()
    }

    override fun onSucceedRequest(data: AuthRealNameBean, what: Int) {
        auth.getAuthRealNameRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        auth.getAuthRealNameError()
    }

    interface AuthRealName{
        fun getAuthRealNameRequest(data: AuthRealNameBean)
        fun getAuthRealNameError()
    }
}