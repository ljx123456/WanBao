package wanbao.yongchao.com.wanbao.ui.main.mvp.data


import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDetailsBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityDetailsBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunityDetailsData(val details:CommunityDetails): BaseData<CommunityDetailsBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommunityDetails(body: CommunityDetailsBody){
        api(Api.getApi().getCommunityDetails(body)).build()
    }

    override fun onSucceedRequest(data: CommunityDetailsBean, what: Int) {
        details.getCommunityDetailsRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        details.getCommunityDetailsError()
    }

    interface CommunityDetails{
        fun getCommunityDetailsRequest(data: CommunityDetailsBean)
        fun getCommunityDetailsError()
    }
}