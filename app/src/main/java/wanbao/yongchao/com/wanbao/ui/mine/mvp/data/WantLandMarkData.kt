package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchLandMarkBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.WantActivitiesBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class WantLandMarkData(val want:WantLandMark): BaseData<SearchLandMarkBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getWantLandMark(body: WantActivitiesBody){
        api(Api.getApi().getWantLandMark(body)).build()
    }

    override fun onSucceedRequest(data: SearchLandMarkBean, what: Int) {
        want.getWantLandMarkRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        want.getWantLandMarkError()
    }

    interface WantLandMark{
        fun getWantLandMarkRequest(data: SearchLandMarkBean)
        fun getWantLandMarkError()
    }
}