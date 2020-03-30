package wanbao.yongchao.com.wanbao.ui.set.mvp.data

import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UpdateBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo

class UpdateData(val update: Update) : BaseData<UpdateBean>() {
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getUpdate(body: UpdateBody) {
        api(Api.getApi().getUpdate(body)).build()
    }

    override fun onSucceedRequest(data: UpdateBean, what: Int) {
        update.getUpdateRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
//        Toast.Tips(msg)
        update.getUpdateError(flag,msg)
    }

    interface Update {
        fun getUpdateRequest(data: UpdateBean)
        fun getUpdateError(code:Int,msg:String)
    }
}