package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.UserInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.UserInfoBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class UserInfoData(val info:UserInfo): BaseData<UserInfoBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getUserInfo(body: UserInfoBody){
        api(Api.getApi().getUserInfo(body)).build()
    }

    override fun onSucceedRequest(data: UserInfoBean, what: Int) {
        info.getUserInfoRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        info.getUserInfoError()
    }

    interface UserInfo{
        fun getUserInfoRequest(data: UserInfoBean)
        fun getUserInfoError()
    }
}