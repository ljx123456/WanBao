package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthTypeBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthTypeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class AuthTypeData(val type:AuthType): BaseData<AuthTypeBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getAuthType(body: AuthTypeBody){
        api(Api.getApi().getAuthType(body)).build()
    }

    override fun onSucceedRequest(data: AuthTypeBean, what: Int) {
        type.getAuthTypeRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        type.getAuthTypeError()
    }

    interface AuthType{
        fun getAuthTypeRequest(data: AuthTypeBean)
        fun getAuthTypeError()
    }
}