package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityCommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityCommentBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunityCommentData(val comment:CommunityComment): BaseData<CommunityCommentBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommunityComment(body: CommunityCommentBody){
        api(Api.getApi().getCommunityComment(body)).build()
    }

    override fun onSucceedRequest(data: CommunityCommentBean, what: Int) {
        comment.getCommunityCommentRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        comment.getCommunityCommentError()
    }

    interface CommunityComment{
        fun getCommunityCommentRequest(data: CommunityCommentBean)
        fun getCommunityCommentError()
    }
}