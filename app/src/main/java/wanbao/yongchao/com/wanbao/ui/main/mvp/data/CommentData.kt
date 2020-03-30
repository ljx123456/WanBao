package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommentBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommentData(val comment:Comment): BaseData<CommentBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getComment(body: CommentBody){
        api(Api.getApi().getComment(body)).build()
    }

    override fun onSucceedRequest(data: CommentBean, what: Int) {
        comment.getCommentRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        comment.getCommentError()
    }

    interface Comment{
        fun getCommentRequest(data: CommentBean)
        fun getCommentError()
    }
}