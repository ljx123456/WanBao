package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ActivitiesDetailsBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ActivitiesDetailsBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ActivitiesDetailsData(val details:ActivitiesDetails): BaseData<ActivitiesDetailsBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getActivitiesDetails(body: ActivitiesDetailsBody){
        api(Api.getApi().getActivitiesDetails(body)).build()
    }

    override fun onSucceedRequest(data: ActivitiesDetailsBean, what: Int) {
        details.getActivitiesDetailsRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        details.getActivitiesDetailsError()
    }

    interface ActivitiesDetails{
        fun getActivitiesDetailsRequest(data: ActivitiesDetailsBean)
        fun getActivitiesDetailsError()
    }
}