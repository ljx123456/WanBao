package wanbao.yongchao.com.wanbao.ui.set.mvp.data

import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateLocationBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UpdateLocationBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo

class UpdateLocationData(val update: UpdateLocation) : BaseData<UpdateLocationBean>() {
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getUpdateLocation(body: UpdateLocationBody) {
        api(Api.getApi().getUpdateLocation(body)).build()
    }

    override fun onSucceedRequest(data: UpdateLocationBean, what: Int) {
        update.getUpdateLocationRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
//        Toast.Tips(msg)
        update.getUpdateLocationError(flag,msg)
    }

    interface UpdateLocation {
        fun getUpdateLocationRequest(data: UpdateLocationBean)
        fun getUpdateLocationError(code:Int,msg:String)
    }
}