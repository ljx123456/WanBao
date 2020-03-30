package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FocusBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class FocusData(val focus:Focus): BaseData<CommunityDelBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getFocus(body: FocusBody){
        api(Api.getApi().getFocus(body)).build()
    }

    override fun onSucceedRequest(data: CommunityDelBean, what: Int) {
        focus.getFocusRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        focus.getFocusError()
    }

    interface Focus{
        fun getFocusRequest(data: CommunityDelBean)
        fun getFocusError()
    }
}