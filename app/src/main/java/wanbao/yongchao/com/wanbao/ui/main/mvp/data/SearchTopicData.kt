package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchTopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchTopicData(val search:SearchTopic): BaseData<SearchTopicBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchTopic(body: SearchBody){
        api(Api.getApi().getSearchTopic(body)).build()
    }

    override fun onSucceedRequest(data: SearchTopicBean, what: Int) {
        search.getSearchTopicRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        search.getSearchTopicError()
    }

    interface SearchTopic{
        fun getSearchTopicRequest(data: SearchTopicBean)
        fun getSearchTopicError()
    }
}