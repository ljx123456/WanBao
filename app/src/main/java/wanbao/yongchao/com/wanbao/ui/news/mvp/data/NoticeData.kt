package wanbao.yongchao.com.wanbao.ui.news.mvp.data

import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class NoticeData(val news:Notice): BaseData<NoticeBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getNotice(body: NoticeBody){
        api(Api.getApi().getNotice(body)).build()
    }

    override fun onSucceedRequest(data: NoticeBean, what: Int) {
        news.getNoticeRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        news.getNoticeError()
    }

    interface Notice{
        fun getNoticeRequest(data: NoticeBean)
        fun getNoticeError()
    }
}