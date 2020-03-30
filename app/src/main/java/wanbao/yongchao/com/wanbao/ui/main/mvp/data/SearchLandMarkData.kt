package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchLandMarkBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchLandMarkData(val search:SearchLandMark): BaseData<SearchLandMarkBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchLandMark(body: SearchBody){
        api(Api.getApi().getSearchLandMark(body)).build()
    }

    override fun onSucceedRequest(data: SearchLandMarkBean, what: Int) {
        search.getSearchLandMarkRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        search.getSearchLandMarkError()
    }

    interface SearchLandMark{
        fun getSearchLandMarkRequest(data: SearchLandMarkBean)
        fun getSearchLandMarkError()
    }
}