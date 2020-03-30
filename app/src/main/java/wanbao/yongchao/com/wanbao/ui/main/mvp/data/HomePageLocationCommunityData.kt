package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.HomePageLocationCommunityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class HomePageLocationCommunityData(val community:HomePageLocationCommunity): BaseData<CommunityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getHomePageLocationCommunity(body: HomePageLocationCommunityBody){
        api(Api.getApi().getHomePageLocationCommunity(body)).build()
    }

    override fun onSucceedRequest(data: CommunityBean, what: Int) {
        community.getHomePageLocationCommunityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        community.getHomePageLocationCommunityError()
    }

    interface HomePageLocationCommunity{
        fun getHomePageLocationCommunityRequest(data: CommunityBean)
        fun getHomePageLocationCommunityError()
    }
}