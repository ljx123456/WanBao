package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommentDelBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommentDelData(val del:CommentDel): BaseData<CommentDelBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getCommentDel(body: CommentDelBody){
        api(Api.getApi().getCommentDel(body)).build()
    }

    override fun onSucceedRequest(data:CommentDelBean, what: Int) {
        del.getCommentDelRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        del.getCommentDelError()
    }

    interface CommentDel{
        fun getCommentDelRequest(data: CommentDelBean)
        fun getCommentDelError()
    }
}