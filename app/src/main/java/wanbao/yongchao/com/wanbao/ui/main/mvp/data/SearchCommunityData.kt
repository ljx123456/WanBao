package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchCommunityData(val search:SearchCommunity): BaseData<CommunityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchCommunity(body: SearchBody){
        api(Api.getApi().getSearchCommunity(body)).build()
    }

    override fun onSucceedRequest(data: CommunityBean, what: Int) {
        search.getSearchCommunityRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        search.getSearchCommunityError()
    }

    interface SearchCommunity{
        fun getSearchCommunityRequest(data: CommunityBean)
        fun getSearchCommunityError()
    }
}