package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.TopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.TopicBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class TopicData(val topicList:TopicList): BaseData<TopicBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getTopic(body: TopicBody){
        api(Api.getApi().getTopic(body)).build()
    }

    override fun onSucceedRequest(data: TopicBean, what: Int) {
        topicList.getTopicRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        topicList.getTopicError()
    }

    interface TopicList{
        fun getTopicRequest(data: TopicBean)
        fun getTopicError()
    }
}