package wanbao.yongchao.com.wanbao.ui.main.mvp.data

import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityDelBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.ReportBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ReportData(val report:Report): BaseData<CommunityDelBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getReport(body: ReportBody){
        api(Api.getApi().getReport(body)).build()
    }

    override fun onSucceedRequest(data: CommunityDelBean, what: Int) {
        report.getReportRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        report.getReportError()
    }

    interface Report{
        fun getReportRequest(data: CommunityDelBean)
        fun getReportError()
    }
}