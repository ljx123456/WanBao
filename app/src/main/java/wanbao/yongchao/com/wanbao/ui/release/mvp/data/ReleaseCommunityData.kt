package wanbao.yongchao.com.wanbao.ui.release.mvp.data

import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.ReleaseCommunityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ReleaseCommunityData(val release:ReleaseCommunity): BaseData<ReleaseBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getReleaseCommunity(body: ReleaseCommunityBody){
        api(Api.getApi().getReleaseCommunity(body)).build()
    }

    override fun onSucceedRequest(data: ReleaseBean, what: Int) {
        release.getReleaseCommunityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        release.getReleaseCommunityError()
    }

    interface ReleaseCommunity{
        fun getReleaseCommunityRequest(data: ReleaseBean)
        fun getReleaseCommunityError()
    }
}