package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotActivitiesBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.SearchHotActivitiesBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchHotActivitiesData(val hot:SearchHotActivities): BaseData<SearchHotActivitiesBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchHotActivities(body: SearchHotActivitiesBody){
        api(Api.getApi().getSearchHotActivities(body)).build()
    }

    override fun onSucceedRequest(data: SearchHotActivitiesBean, what: Int) {
        hot.getSearchHotActivitiesRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        hot.getSearchHotActivitiesError()
    }

    interface SearchHotActivities{
        fun getSearchHotActivitiesRequest(data: SearchHotActivitiesBean)
        fun getSearchHotActivitiesError()
    }
}