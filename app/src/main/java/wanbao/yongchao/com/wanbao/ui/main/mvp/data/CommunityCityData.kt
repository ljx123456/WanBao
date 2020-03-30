package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunityCityData(val city:CommunityCity): BaseData<CommunityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommunityCity(body: CommunityBody){
        api(Api.getApi().getCommunityCity(body)).build()
    }

    override fun onSucceedRequest(data: CommunityBean, what: Int) {
        city.getCommunityCityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        city.getCommunityCityError()
    }

    interface CommunityCity{
        fun getCommunityCityRequest(data: CommunityBean)
        fun getCommunityCityError()
    }
}