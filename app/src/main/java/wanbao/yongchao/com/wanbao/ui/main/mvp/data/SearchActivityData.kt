package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchActivityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchActivityData(val search:SearchActivity): BaseData<SearchActivityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchActivity(body: SearchBody){
        api(Api.getApi().getSearchActivity(body)).build()
    }

    override fun onSucceedRequest(data: SearchActivityBean, what: Int) {
        search.getSearchActivityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        search.getSearchActivityError()
    }

    interface SearchActivity{
        fun getSearchActivityRequest(data: SearchActivityBean)
        fun getSearchActivityError()
    }
}