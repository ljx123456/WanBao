package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.UserHomePageBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UserHomePageBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class UserHomePageData(val homepage:UserHomePage): BaseData<UserHomePageBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getUserHomePage(body: UserHomePageBody){
        api(Api.getApi().getUserHomePage(body)).build()
    }

    override fun onSucceedRequest(data: UserHomePageBean, what: Int) {
        homepage.getUserHomePageRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        homepage.getUserHomePageError()
    }

    interface UserHomePage{
        fun getUserHomePageRequest(data: UserHomePageBean)
        fun getUserHomePageError()
    }
}