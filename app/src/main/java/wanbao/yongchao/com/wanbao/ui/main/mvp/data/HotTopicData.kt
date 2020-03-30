package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.HotTopicBean
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class HotTopicData(val hot:HotTopic): BaseData<HotTopicBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getHotTopic(){
        api(Api.getApi().getHotTopic()).build()
    }

    override fun onSucceedRequest(data: HotTopicBean, what: Int) {
        hot.getHotTopicRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        hot.getHotTopicError()
    }

    interface HotTopic{
        fun getHotTopicRequest(data: HotTopicBean)
        fun getHotTopicError()
    }
}