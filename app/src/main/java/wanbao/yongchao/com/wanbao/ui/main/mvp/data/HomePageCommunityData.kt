package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.HomePageCommunityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class HomePageCommunityData(val community:HomePageCommunity): BaseData<CommunityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getHomePageCommunity(body: HomePageCommunityBody){
        api(Api.getApi().getHomePageCommunity(body)).build()
    }

    override fun onSucceedRequest(data: CommunityBean, what: Int) {
        community.getHomePageCommunityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        community.getHomePageCommunityError()
    }

    interface HomePageCommunity{
        fun getHomePageCommunityRequest(data: CommunityBean)
        fun getHomePageCommunityError()
    }
}