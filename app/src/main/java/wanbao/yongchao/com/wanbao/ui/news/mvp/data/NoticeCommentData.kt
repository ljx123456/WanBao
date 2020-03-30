package wanbao.yongchao.com.wanbao.ui.news.mvp.data

import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeCommentBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class NoticeCommentData(val news:NoticeComment): BaseData<NoticeCommentBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getNoticeComment(body: NoticeBody){
        api(Api.getApi().getNoticeComment(body)).build()
    }

    override fun onSucceedRequest(data: NoticeCommentBean, what: Int) {
        news.getNoticeCommentRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        news.getNoticeCommentError()
    }

    interface NoticeComment{
        fun getNoticeCommentRequest(data: NoticeCommentBean)
        fun getNoticeCommentError()
    }
}