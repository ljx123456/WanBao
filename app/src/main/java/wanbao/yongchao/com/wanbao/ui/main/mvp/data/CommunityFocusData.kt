package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunityFocusData(val focus:CommunityFocus): BaseData<CommunityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommunityFocus(body: CommunityBody){
        api(Api.getApi().getCommunityFocus(body)).build()
    }

    override fun onSucceedRequest(data: CommunityBean, what: Int) {
        focus.getCommunityFocusRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        focus.getCommunityFocusError()
    }

    interface CommunityFocus{
        fun getCommunityFocusRequest(data: CommunityBean)
        fun getCommunityFocusError()
    }
}