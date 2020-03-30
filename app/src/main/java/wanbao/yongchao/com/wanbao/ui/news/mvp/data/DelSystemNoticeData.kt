package wanbao.yongchao.com.wanbao.ui.news.mvp.data

import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.DelSystemBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelSystemBody
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NewsBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class DelSystemNoticeData(val news:DelSystem): BaseData<DelSystemBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getDelSystem(body: DelSystemBody){
        api(Api.getApi().getDelSystem(body)).build()
    }

    override fun onSucceedRequest(data: DelSystemBean, what: Int) {
        news.getDelSystemRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        news.getDelSystemError()
    }

    interface DelSystem{
        fun getDelSystemRequest(data: DelSystemBean)
        fun getDelSystemError()
    }
}