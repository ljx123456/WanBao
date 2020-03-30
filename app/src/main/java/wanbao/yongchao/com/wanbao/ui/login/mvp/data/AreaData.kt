package wanbao.yongchao.com.wanbao.ui.login.mvp.data

import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.AreaBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.AreaBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class AreaData(val area:Area): BaseData<AreaBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getArea(body: AreaBody){
        api(Api.getApi().getArea(body)).build()
    }

    override fun onSucceedRequest(data: AreaBean, what: Int) {
        area.getAreaRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        area.getAreaError()
    }

    interface Area{
        fun getAreaRequest(data: AreaBean)
        fun getAreaError()
    }
}