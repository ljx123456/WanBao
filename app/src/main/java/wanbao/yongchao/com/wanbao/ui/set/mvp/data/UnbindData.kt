package wanbao.yongchao.com.wanbao.ui.set.mvp.data

import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UnbindBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UnbindBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class UnbindData(val change:Unbind): BaseData<UnbindBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getUnbind(body: UnbindBody){
        api(Api.getApi().getUnbind(body)).build()
    }

    override fun onSucceedRequest(data: UnbindBean, what: Int) {
        change.getUnbindRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        change.getUnbindError()
    }

    interface Unbind{
        fun getUnbindRequest(data: UnbindBean)
        fun getUnbindError()
    }
}