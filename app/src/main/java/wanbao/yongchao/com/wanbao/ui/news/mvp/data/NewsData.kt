package wanbao.yongchao.com.wanbao.ui.news.mvp.data

import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NewsBean
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.NewsBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class NewsData(val news:News): BaseData<NewsBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getNews(body: NewsBody){
        api(Api.getApi().getNews(body)).build()
    }

    override fun onSucceedRequest(data: NewsBean, what: Int) {
        news.getNewsRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        news.getNewsError()
    }

    interface News{
        fun getNewsRequest(data: NewsBean)
        fun getNewsError()
    }
}