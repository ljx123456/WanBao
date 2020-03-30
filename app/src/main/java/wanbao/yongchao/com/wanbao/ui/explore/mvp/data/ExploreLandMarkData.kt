package wanbao.yongchao.com.wanbao.ui.explore.mvp.data

import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreLandMarkBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreLandMarkBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ExploreLandMarkData(val land:ExploreLandMark): BaseData<ExploreLandMarkBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getExploreLandMark(body: ExploreLandMarkBody){
        api(Api.getApi().getExploreLandMark(body)).build()
    }

    override fun onSucceedRequest(data: ExploreLandMarkBean, what: Int) {
        land.getExploreLandMarkRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        land.getExploreLandMarkError()
    }

    interface ExploreLandMark{
        fun getExploreLandMarkRequest(data: ExploreLandMarkBean)
        fun getExploreLandMarkError()
    }
}