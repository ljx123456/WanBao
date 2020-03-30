package wanbao.yongchao.com.wanbao.ui.news.mvp.data

import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeLikeBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class NoticeLikeData(val news:NoticeLike): BaseData<NoticeLikeBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getNoticeLike(body: NoticeBody){
        api(Api.getApi().getNoticeLike(body)).build()
    }

    override fun onSucceedRequest(data: NoticeLikeBean, what: Int) {
        news.getNoticeLikeRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        news.getNoticeLikeError()
    }

    interface NoticeLike{
        fun getNoticeLikeRequest(data: NoticeLikeBean)
        fun getNoticeLikeError()
    }
}