package wanbao.yongchao.com.wanbao.ui.set.mvp.data

import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UnbindBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.SuggestBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SuggestData(val change:Suggest): BaseData<UnbindBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getSuggest(body: SuggestBody){
        api(Api.getApi().getSuggest(body)).build()
    }

    override fun onSucceedRequest(data: UnbindBean, what: Int) {
        change.getSuggestRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        change.getSuggestError()
    }

    interface Suggest{
        fun getSuggestRequest(data: UnbindBean)
        fun getSuggestError()
    }
}