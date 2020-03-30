package wanbao.yongchao.com.wanbao.ui.release.mvp.data

import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseYeBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.ReleaseYeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ReleaseYeData(val release:ReleaseYe): BaseData<ReleaseYeBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getReleaseYe(body: ReleaseYeBody){
        api(Api.getApi().getReleaseYe(body)).build()
    }

    override fun onSucceedRequest(data: ReleaseYeBean, what: Int) {
        release.getReleaseYeRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        release.getReleaseYeError()
    }

    interface ReleaseYe{
        fun getReleaseYeRequest(data: ReleaseYeBean)
        fun getReleaseYeError()
    }
}