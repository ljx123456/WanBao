package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.BusinessActivityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class BusinessActivityData(val activity:BusinessActivity): BaseData<ExploreActivityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getBusinessActivity(body: BusinessActivityBody){
        api(Api.getApi().getBusinessActivity(body)).build()
    }

    override fun onSucceedRequest(data: ExploreActivityBean, what: Int) {
        activity.getBusinessActivityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        activity.getBusinessActivityError()
    }

    interface BusinessActivity{
        fun getBusinessActivityRequest(data: ExploreActivityBean)
        fun getBusinessActivityError()
    }
}