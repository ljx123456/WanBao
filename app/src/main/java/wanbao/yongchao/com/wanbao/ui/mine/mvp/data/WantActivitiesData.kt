package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.WantActivitiesBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class WantActivitiesData(val want:WantActivities): BaseData<ExploreActivityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getWantActivities(body: WantActivitiesBody){
        api(Api.getApi().getWantActivities(body)).build()
    }

    override fun onSucceedRequest(data: ExploreActivityBean, what: Int) {
        want.getWantActivitiesRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        want.getWantActivitiesError()
    }

    interface WantActivities{
        fun getWantActivitiesRequest(data: ExploreActivityBean)
        fun getWantActivitiesError()
    }
}