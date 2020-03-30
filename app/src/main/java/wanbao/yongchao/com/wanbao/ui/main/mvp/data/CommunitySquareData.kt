package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunitySquareData(val square:CommunitySquare): BaseData<CommunityBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommunitySquare(body: CommunityBody){
        api(Api.getApi().getCommunitySquare(body)).build()
    }

    override fun onSucceedRequest(data: CommunityBean, what: Int) {
        square.getCommunitySquareRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        square.getCommunitySquareError()
    }

    interface CommunitySquare{
        fun getCommunitySquareRequest(data: CommunityBean)
        fun getCommunitySquareError()
    }
}