package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SearchUserData(val search:SearchUser): BaseData<FansBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSearchUser(body: SearchBody){
        api(Api.getApi().getSearchUser(body)).build()
    }

    override fun onSucceedRequest(data: FansBean, what: Int) {
        search.getSearchUserRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        search.getSearchUserError()
    }

    interface SearchUser{
        fun getSearchUserRequest(data: FansBean)
        fun getSearchUserError()
    }
}