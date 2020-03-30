package wanbao.yongchao.com.wanbao.ui.login.mvp.data

import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class TagsData(val tags:Tags): BaseData<TagsBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getTags(){
        api(Api.getApi().getTags()).build()
    }

    override fun onSucceedRequest(data: TagsBean, what: Int) {
        tags.getTagsRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        tags.getTagsError()
    }

    interface Tags{
        fun getTagsRequest(data: TagsBean)
        fun getTagsError()
    }
}