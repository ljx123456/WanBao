package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchFansData(val search:SearchFans): BaseData<FansBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchFans(body: SearchBody){
        api(Api.getApi().getSearchUser(body)).build()
    }

    override fun onSucceedRequest(data: FansBean, what: Int) {
        search.getSearchFansRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        search.getSearchFansError()
    }

    interface SearchFans{
        fun getSearchFansRequest(data: FansBean)
        fun getSearchFansError()
    }
}