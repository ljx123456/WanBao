package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.LikeCommunityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class LikeCommunityData(val like:LikeCommunity): BaseData<CommunityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getLikeCommunity(body: LikeCommunityBody){
        api(Api.getApi().getLikeCommunity(body)).build()
    }

    override fun onSucceedRequest(data: CommunityBean, what: Int) {
        like.getLikeCommunityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        like.getLikeCommunityError()
    }

    interface LikeCommunity{
        fun getLikeCommunityRequest(data: CommunityBean)
        fun getLikeCommunityError()
    }
}