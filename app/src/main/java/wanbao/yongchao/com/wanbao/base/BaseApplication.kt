package wanbao.yongchao.com.wanbao.base

import android.app.Application
import android.os.StrictMode
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.liulishuo.filedownloader.FileDownloader
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * Created by Administrator on 2017/12/18 0018.
 */
open abstract class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initCreate()
        initLibrary()
        initDataSave()

        Utils.init(this)
        FileDownloader.setupOnApplicationOnCreate(this)
//        Glide.get(this).register(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(OkHttpClient()))

//        Glide.get(this).register(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(OkHttpClient()))

//        Glide.get(this).register(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())

        // android 7.0系统解决拍照的问题
        var builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
    }

    abstract fun initCreate()
    abstract fun initLibrary()
    abstract fun initDataSave()

}