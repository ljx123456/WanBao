package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentDetailsBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommentDetailsBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommentDetailsData(val comment:CommentDetails): BaseData<CommentDetailsBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommentDetails(body: CommentDetailsBody){
        api(Api.getApi().getCommentDetails(body)).build()
    }

    override fun onSucceedRequest(data: CommentDetailsBean, what: Int) {
        comment.getCommentDetailsRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        comment.getCommentDetailsError()
    }

    interface CommentDetails{
        fun getCommentDetailsRequest(data: CommentDetailsBean)
        fun getCommentDetailsError()
    }
}