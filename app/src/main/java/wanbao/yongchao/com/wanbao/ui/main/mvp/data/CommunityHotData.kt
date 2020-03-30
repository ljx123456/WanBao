package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunityHotData(val hot:CommunityHot): BaseData<CommunityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommunityHot(body: CommunityBody){
        api(Api.getApi().getCommunityHot(body)).build()
    }

    override fun onSucceedRequest(data: CommunityBean, what: Int) {
        hot.getCommunityHotRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        hot.getCommunityHotError()
    }

    interface CommunityHot{
        fun getCommunityHotRequest(data: CommunityBean)
        fun getCommunityHotError()
    }
}