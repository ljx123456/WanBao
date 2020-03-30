package wanbao.yongchao.com.wanbao.ui.login.mvp.data



import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.QiniuTokenBean
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo

class QiniuTokenData(val qiniutoken: QiniuToken) : BaseData<QiniuTokenBean>() {
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getQiniuToken() {
        api(Api.getApi().getQiniuToken()).build()
    }

    override fun onSucceedRequest(data: QiniuTokenBean, what: Int) {
        qiniutoken.getQiniuTokenRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        qiniutoken.getQiniuTokenError()
    }

    interface QiniuToken {
        fun getQiniuTokenRequest(data: QiniuTokenBean)
        fun getQiniuTokenError()
    }
}