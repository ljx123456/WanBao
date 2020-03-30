package wanbao.yongchao.com.wanbao.ui.set.mvp.data

import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.ChangePhoneBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.ChangePhoneBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ChangePhoneData(val change:ChangePhone): BaseData<ChangePhoneBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getChangePhone(body: ChangePhoneBody){
        api(Api.getApi().getChangePhone(body)).build()
    }

    override fun onSucceedRequest(data: ChangePhoneBean, what: Int) {
        change.getChangePhoneRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        change.getChangePhoneError()
    }

    interface ChangePhone{
        fun getChangePhoneRequest(data: ChangePhoneBean)
        fun getChangePhoneError()
    }
}