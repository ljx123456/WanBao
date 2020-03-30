package wanbao.yongchao.com.wanbao.ui.set.mvp.data

import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.NoticeStatusBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.NoticeStatusBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class NoticeStatusData(val notice:NoticeStatus): BaseData<NoticeStatusBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getNoticeStatus(body: NoticeStatusBody){
        api(Api.getApi().getNoticeStatus(body)).build()
    }

    override fun onSucceedRequest(data: NoticeStatusBean, what: Int) {
        notice.getNoticeStatusRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        notice.getNoticeStatusError()
    }

    interface NoticeStatus{
        fun getNoticeStatusRequest(data: NoticeStatusBean)
        fun getNoticeStatusError()
    }
}