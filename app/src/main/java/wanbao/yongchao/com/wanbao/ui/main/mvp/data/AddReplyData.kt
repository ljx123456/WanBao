package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddReplyBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.AddReplyBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class AddReplyData(val add:AddReply): BaseData<AddReplyBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getAddReply(body: AddReplyBody){
        api(Api.getApi().getAddReply(body)).build()
    }

    override fun onSucceedRequest(data: AddReplyBean, what: Int) {
        add.getAddReplyRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        add.getAddReplyError()
    }

    interface AddReply{
        fun getAddReplyRequest(data: AddReplyBean)
        fun getAddReplyError()
    }
}