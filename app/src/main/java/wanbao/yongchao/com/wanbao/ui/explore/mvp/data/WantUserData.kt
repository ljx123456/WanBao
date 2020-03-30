package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantUserBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WantUserBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class WantUserData(val want:WantUser): BaseData<WantUserBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getWantUser(body: WantUserBody){
        api(Api.getApi().getWantUser(body)).build()
    }

    override fun onSucceedRequest(data: WantUserBean, what: Int) {
        want.getWantUserRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        want.getWantUserError()
    }

    interface WantUser{
        fun getWantUserRequest(data: WantUserBean)
        fun getWantUserError()
    }
}