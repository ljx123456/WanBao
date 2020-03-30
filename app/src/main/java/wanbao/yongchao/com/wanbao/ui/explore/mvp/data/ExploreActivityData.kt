package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreActivityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ExploreActivityData(val activity:ExploreActivity): BaseData<ExploreActivityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getExploreActivity(body: ExploreActivityBody){
        api(Api.getApi().getExploreActivity(body)).build()
    }

    override fun onSucceedRequest(data: ExploreActivityBean, what: Int) {
        activity.getExploreActivityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        activity.getExploreActivityError()
    }

    interface ExploreActivity{
        fun getExploreActivityRequest(data: ExploreActivityBean)
        fun getExploreActivityError()
    }
}