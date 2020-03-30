package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityLikeBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityLikeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunityLikeData(val like:CommunityLike): BaseData<CommunityLikeBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommunityLike(body: CommunityLikeBody){
        api(Api.getApi().getCommunityLike(body)).build()
    }

    override fun onSucceedRequest(data: CommunityLikeBean, what: Int) {
        like.getCommunityLikeRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        like.getCommunityLikeError()
    }

    interface CommunityLike{
        fun getCommunityLikeRequest(data: CommunityLikeBean)
        fun getCommunityLikeError()
    }
}