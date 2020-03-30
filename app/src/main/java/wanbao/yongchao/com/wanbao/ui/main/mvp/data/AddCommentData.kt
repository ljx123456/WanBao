package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddCommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.AddCommentBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class AddCommentData(val add:AddComment): BaseData<AddCommentBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getAddComment(body: AddCommentBody){
        api(Api.getApi().getAddComment(body)).build()
    }

    override fun onSucceedRequest(data: AddCommentBean, what: Int) {
        add.getAddCommentRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        add.getAddCommentError()
    }

    interface AddComment{
        fun getAddCommentRequest(data: AddCommentBean)
        fun getAddCommentError()
    }
}