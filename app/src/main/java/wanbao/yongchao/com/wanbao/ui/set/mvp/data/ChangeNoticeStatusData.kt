package wanbao.yongchao.com.wanbao.ui.set.mvp.data

import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.ChangePhoneBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.ChangeNoticeStatusBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ChangeNoticeStatusData(val change:ChangeNoticeStatus): BaseData<ChangePhoneBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getChangeNoticeStatus(body: ChangeNoticeStatusBody){
        api(Api.getApi().getChangeNoticeStatus(body)).build()
    }

    override fun onSucceedRequest(data: ChangePhoneBean, what: Int) {
        change.getChangeNoticeStatusRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        change.getChangeNoticeStatusError()
    }

    interface ChangeNoticeStatus{
        fun getChangeNoticeStatusRequest(data: ChangePhoneBean)
        fun getChangeNoticeStatusError()
    }
}