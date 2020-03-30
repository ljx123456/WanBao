package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.UserHomePageBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UserHomePageForNameBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class UserHomePageForNameData(val homepage:UserHomePageForName): BaseData<UserHomePageBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getUserHomePage(body: UserHomePageForNameBody){
        api(Api.getApi().getUserHomePageForName(body)).build()
    }

    override fun onSucceedRequest(data: UserHomePageBean, what: Int) {
        homepage.getUserHomePageForNameRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        homepage.getUserHomePageForNameError()
    }

    interface UserHomePageForName{
        fun getUserHomePageForNameRequest(data: UserHomePageBean)
        fun getUserHomePageForNameError()
    }
}