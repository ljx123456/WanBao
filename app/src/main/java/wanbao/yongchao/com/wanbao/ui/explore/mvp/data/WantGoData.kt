package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantGoBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WantGoBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class WantGoData(val want:WantGo): BaseData<WantGoBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getWantGo(body: WantGoBody){
        api(Api.getApi().getWantGo(body)).build()
    }

    override fun onSucceedRequest(data: WantGoBean, what: Int) {
        want.getWantGoRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        want.getWantGoError()
    }

    interface WantGo{
        fun getWantGoRequest(data: WantGoBean)
        fun getWantGoError()
    }
}