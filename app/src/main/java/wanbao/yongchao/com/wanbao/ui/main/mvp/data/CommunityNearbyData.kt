package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunityNearbyData(val nearby:CommunityNearby): BaseData<CommunityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommunityNearby(body: CommunityBody){
        api(Api.getApi().getCommunityNearby(body)).build()
    }

    override fun onSucceedRequest(data: CommunityBean, what: Int) {
        nearby.getCommunityNearbyRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        nearby.getCommunityNearbyError()
    }

    interface CommunityNearby{
        fun getCommunityNearbyRequest(data: CommunityBean)
        fun getCommunityNearbyError()
    }
}