package  wanbao.yongchao.com.wanbao.utils.http


import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.CacheUtils
import com.blankj.utilcode.util.LogUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.ExceptionHandle
import wanbao.yongchao.com.wanbao.utils.utils.Toast

/**
 * Created by Administrator on 2017/12/18 0018.
 */
open abstract class BaseData<T> : Observer<T> {

    private var what = 0
    private val saveInfo by lazy { requestCache() }

    private var disposable: Disposable? = null
    private var observable: Observable<T>? = null


    open fun api(able: Observable<T>): BaseData<T> {
        observable = able
        return this
    }

    open fun setMsg(msg: Int): BaseData<T> {
        what = msg
        return this
    }

    open fun build(): BaseData<T> {
        observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(this)
        return this
    }


    open fun getDisposable(): Disposable? {
        return if (disposable != null) disposable!! else null
    }


    //请求成功
    override fun onNext(data: T) {
        onSucceedRequest(data, what)
    }


    //请求失败
    override fun onError(t: Throwable) {
        LogUtils.a(t.toString())
        if (saveInfo.requestDataCache) getSaveData(t) else setErrorRequest(t)
    }


    override fun onSubscribe(d: Disposable) {
        this.disposable = d
    }


    private fun getSaveData(t: Throwable) {
        val resultBean = CacheUtils.getInstance().getSerializable(saveInfo.requestDataCacheTag) as T
        if (resultBean != null) onSucceedRequest(resultBean, what) else setErrorRequest(t)
    }

    private fun setErrorRequest(t: Throwable) {
        val errorData = ExceptionHandle.exceptionMessage(t)
        when (errorData.code) {
//            -1104 -> {
////                Toast.Tips("登陆信息过期,请重新登录")
////                DelUser()
////                PicturesUtils.deleteAllPictures()
////                TagsUtils.deleteAllTags()
////                DBUtils.DelVideo()
////                ActivityUtils.finishAllActivities()
////                JPushInterface.deleteAlias(app,1)
////                intentLogin()
//            }
//            -3 -> {
////                Toast.Tips("登陆信息过期,请重新登录")
////                DelUser()
////                PicturesUtils.deleteAllPictures()
////                TagsUtils.deleteAllTags()
////                DBUtils.DelVideo()
////                ActivityUtils.finishAllActivities()
////                intentLogin()
//            }
//            -2 -> {
////                Toast.Tips("登陆信息过期,请重新登录")
////                DelUser()
////                PicturesUtils.deleteAllPictures()
////                TagsUtils.deleteAllTags()
////                DBUtils.DelVideo()
////                ActivityUtils.finishAllActivities()
////                intentLogin()
//            }
            -9999 ->{
//                Toast.Tips("服务繁忙,请稍后重试")
                onErrorRequest(errorData.code, "服务繁忙,请稍后重试", what)
            }
            -8889 ->{
//                Toast.Tips("网络异常,请重试")
                onErrorRequest(errorData.code, "网络异常,请重试", what)
            }
            -4->{
//                Toast.Tips("服务繁忙,请稍后重试")
                onErrorRequest(errorData.code, "服务繁忙,请稍后重试", what)
            }
            -1 -> {
                Toast.Tips("登陆信息过期,请重新登录")
                DBUtils.DelUser()
                DBUtils.DelBusiness()
                intentUtils.intentLogin()

//                Toast.Tips()
//                onErrorRequest(errorData.code, "数据异常，请重新选择后提交", what)
            }
            else -> {
                onErrorRequest(errorData.code, errorData.message, what)
            }
        }

    }


    override fun onComplete() {}

    protected abstract fun requestCache(): SaveInfo

    protected abstract fun onSucceedRequest(data: T, what: Int)

    protected abstract fun onErrorRequest(flag: Int, msg: String, what: Int)


}