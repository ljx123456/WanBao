package wanbao.yongchao.com.wanbao.utils.http


/**
 * Created by Administrator on 2017/12/18 0018.
 */
object Api {

    fun getApi(): ApiService {
        return  RetrofitClient().initRetrofitClient()!!.create(ApiService::class.java)
    }

    object ApiServiceHolder {
        val API_SERVICE: ApiService =  RetrofitClient().initRetrofitClient()!!.create(ApiService::class.java)
    }

}