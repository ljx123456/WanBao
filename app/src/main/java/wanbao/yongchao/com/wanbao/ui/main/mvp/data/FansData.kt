package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.FansBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class FansData(val fans:FansList): BaseData<FansBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getFans(body: FansBody){
        api(Api.getApi().getFans(body)).build()
    }

    override fun onSucceedRequest(data: FansBean, what: Int) {
        fans.getFansRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        fans.getFansError()
    }

    interface FansList{
        fun getFansRequest(data: FansBean)
        fun getFansError()
    }
}