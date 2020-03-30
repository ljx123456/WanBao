package  wanbao.yongchao.com.wanbao.utils.http

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Administrator on 2017/12/18 0018.
 */
class BaseInterceptor : Interceptor {

    var headers: HashMap<String, String>? = java.util.HashMap()

    fun addHeads(key: String, v: String) {
        headers!!.put(key, v)
    }


    override fun intercept(chain: Interceptor.Chain?): Response {
        val builder = chain!!.request().newBuilder()
        if (headers != null && headers!!.size > 0) {
            val keys = headers!!.keys
            for (headerKey in keys) {
                builder.addHeader(headerKey, headers!![headerKey]).build()
            }
        }
        return chain.proceed(builder.build())
    }
}