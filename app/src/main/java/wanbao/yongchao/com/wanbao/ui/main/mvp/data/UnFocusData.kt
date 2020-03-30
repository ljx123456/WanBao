package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.UnFocusBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class UnFocusData(val focus:UnFocus): BaseData<CommunityDelBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getUnFocus(body: UnFocusBody){
        api(Api.getApi().getUnFocus(body)).build()
    }

    override fun onSucceedRequest(data: CommunityDelBean, what: Int) {
        focus.getUnFocusRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        focus.getUnFocusError()
    }

    interface UnFocus{
        fun getUnFocusRequest(data: CommunityDelBean)
        fun getUnFocusError()
    }
}