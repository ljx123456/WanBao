package wanbao.yongchao.com.wanbao.utils.utils


import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import wanbao.yongchao.com.wanbao.utils.http.CodeException
import wanbao.yongchao.com.wanbao.utils.http.ErrorData
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLHandshakeException


/**
 * Created by Administrator on 2018/2/26 0026.
 */
object ExceptionHandle {


    fun exceptionMessage(e: Throwable): ErrorData {

        return if (e is CodeException) {
            ErrorData(e.code, e.msg)
        } else if (e is JsonParseException || e is JSONException) {
            ErrorData(0, "解析错误")
        } else if (e is ConnectException) {
            ErrorData(0, "连接失败，请重试")
        } else if (e is SSLHandshakeException) {
            ErrorData(0, "证书验证失败，请重试")
        } else if (e is HttpException) {
            ErrorData(0, "网络异常，请重试")
        } else if (e is TimeoutException) {
            ErrorData(0, "网络异常，请重试")
        }else if (e is SocketTimeoutException){
            ErrorData(0, "网络异常，请重试")
        }else if (e is SocketException){
            ErrorData(0, "网络异常，请重试")
        } else  {
            ErrorData(0, " 未知错误，请重试")
        }
    }
}