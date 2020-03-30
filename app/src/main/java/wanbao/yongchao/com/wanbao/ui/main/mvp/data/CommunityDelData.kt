package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityDelBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunityDelData(val del:CommunityDel): BaseData<CommunityDelBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommunityDel(body: CommunityDelBody){
        api(Api.getApi().getCommunityDel(body)).build()
    }

    override fun onSucceedRequest(data: CommunityDelBean, what: Int) {
        del.getCommunityDelRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        del.getCommunityDelError()
    }

    interface CommunityDel{
        fun getCommunityDelRequest(data: CommunityDelBean)
        fun getCommunityDelError()
    }
}