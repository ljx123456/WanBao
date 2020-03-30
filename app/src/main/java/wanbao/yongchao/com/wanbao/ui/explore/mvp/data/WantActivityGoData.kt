package wanbao.yongchao.com.wanbao.ui.explore.mvp.data


import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantUserBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WantActivityGoBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class WantActivityGoData(val want:WantActivityGo): BaseData<WantUserBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getWantActivityGo(body: WantActivityGoBody){
        api(Api.getApi().getWantActivityGo(body)).build()
    }

    override fun onSucceedRequest(data: WantUserBean, what: Int) {
        want.getWantActivityGoRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        want.getWantActivityGoError()
    }

    interface WantActivityGo{
        fun getWantActivityGoRequest(data: WantUserBean)
        fun getWantActivityGoError()
    }
}