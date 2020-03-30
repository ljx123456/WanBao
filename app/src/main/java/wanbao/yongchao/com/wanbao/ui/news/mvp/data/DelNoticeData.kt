package wanbao.yongchao.com.wanbao.ui.news.mvp.data

import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.DelNoticeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.DelNoticeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class DelNoticeData(val news:DelNotice): BaseData<DelNoticeBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getDelNotice(body: DelNoticeBody){
        api(Api.getApi().getDelNotice(body)).build()
    }

    override fun onSucceedRequest(data: DelNoticeBean, what: Int) {
        news.getDelNoticeRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        news.getDelNoticeError()
    }

    interface DelNotice{
        fun getDelNoticeRequest(data: DelNoticeBean)
        fun getDelNoticeError()
    }
}