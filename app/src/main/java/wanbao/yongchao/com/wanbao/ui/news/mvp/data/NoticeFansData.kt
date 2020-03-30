package wanbao.yongchao.com.wanbao.ui.news.mvp.data

import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeFansBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NoticeBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class NoticeFansData(val news:NoticeFans): BaseData<NoticeFansBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getNoticeFans(body: NoticeBody){
        api(Api.getApi().getNoticeFans(body)).build()
    }

    override fun onSucceedRequest(data: NoticeFansBean, what: Int) {
        news.getNoticeFansRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        news.getNoticeFansError()
    }

    interface NoticeFans{
        fun getNoticeFansRequest(data: NoticeFansBean)
        fun getNoticeFansError()
    }
}