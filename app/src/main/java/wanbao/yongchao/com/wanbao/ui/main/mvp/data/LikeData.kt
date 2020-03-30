package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.LikeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class LikeData(val like:Like): BaseData<CommunityDelBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getLike(body: LikeBody){
        api(Api.getApi().getLike(body)).build()
    }

    override fun onSucceedRequest(data: CommunityDelBean, what: Int) {
        like.getLikeRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        like.getLikeError()
    }

    interface Like{
        fun getLikeRequest(data: CommunityDelBean)
        fun getLikeError()
    }
}