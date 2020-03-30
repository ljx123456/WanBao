package wanbao.yongchao.com.wanbao.base

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
/**
 * Created by Administrator on 2018/11/15 0015.
 */
object BaseContext {

    private lateinit var context: Context

    fun initContext(context: Context) {
        this.context = context
    }

    fun getContext(): Context = context
}