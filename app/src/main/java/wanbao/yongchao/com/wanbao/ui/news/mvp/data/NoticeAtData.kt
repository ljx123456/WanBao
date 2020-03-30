package wanbao.yongchao.com.wanbao.ui.news.mvp.data

import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeAtBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class NoticeAtData(val news:NoticeAt): BaseData<NoticeAtBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getNoticeAt(body: NoticeBody){
        api(Api.getApi().getNoticeAt(body)).build()
    }

    override fun onSucceedRequest(data: NoticeAtBean, what: Int) {
        news.getNoticeAtRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        news.getNoticeAtError()
    }

    interface NoticeAt{
        fun getNoticeAtRequest(data: NoticeAtBean)
        fun getNoticeAtError()
    }
}