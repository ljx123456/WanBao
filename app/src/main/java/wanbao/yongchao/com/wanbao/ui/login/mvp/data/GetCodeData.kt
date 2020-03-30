package wanbao.yongchao.com.wanbao.ui.login.mvp.data

import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.CodeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.GetCodeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class GetCodeData(val getcode:GetCode):BaseData<CodeBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCode(body:GetCodeBody){
        api(Api.getApi().getByCode(body)).build()
    }

    override fun onSucceedRequest(data: CodeBean, what: Int) {
        getcode.getCodeRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        getcode.getCodeError()
    }

    interface GetCode{
        fun getCodeRequest(data: CodeBean)
        fun getCodeError()
    }
}