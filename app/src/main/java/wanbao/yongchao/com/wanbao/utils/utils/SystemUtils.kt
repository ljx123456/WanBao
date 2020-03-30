package wanbao.yongchao.com.wanbao.utils.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*
//import cn.jzvd.JZUtils.getWindow
//import java.lang.reflect.Array.setInt
//import java.lang.reflect.AccessibleObject.setAccessible
////import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.getAttributes
//import cn.jzvd.JZUtils.getWindow
//import cn.jzvd.JZUtils.getWindow
import wanbao.yongchao.com.wanbao.base.BaseActivity


/**
 * Created by Administrator on 2018/5/14 0014.
 */
object SystemUtils {


    //全屏
    fun activityFullScreen(activity: BaseActivity) {
        activity.window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val decorView = activity.window.decorView
//            val option = ( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//            decorView.systemUiVisibility = option
//            activity.window.navigationBarColor = Color.TRANSPARENT
//            activity.window.statusBarColor = Color.TRANSPARENT
//        }
//        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    /**
     * 判断当前连接是否为wifi
     *
     * @param context
     * 上下文
     * @return true wifi false 非wifi
     */
    fun isWifiEnabled(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected)
                return networkInfo.type == ConnectivityManager.TYPE_WIFI
        }
        return false
    }


    //判断是否首次安装
    @SuppressLint("ApplySharedPref")
    fun isFirstStart(context: Context): Boolean {
        val preferences = context.getSharedPreferences("SHARE_APP_TAG", 0)
        val isFirst = preferences.getBoolean("FIRSTStart", true)
        return if (isFirst) {
            preferences.edit().putBoolean("FIRSTStart", false).commit()
            true
        } else {
            false
        }
    }

    //重启APP
    fun restartApp(context: Context) {
        Handler().postDelayed({
            ActivityUtils.finishAllActivities()
//            ActivityUtils.startActivity(SplashActivity::class.java)
            android.os.Process.killProcess(android.os.Process.myPid())
        }, 1000)

    }

    //判断魅族系统
    fun isFlyme():Boolean{
        val displayId = Build.DISPLAY
        if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
            val displayIdArray = displayId.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (temp in displayIdArray) {
                //版本号4以上，形如4.x.
                if (temp.matches("^[4-9]\\.(\\d+\\.)+\\S*".toRegex())) {
                    return true
                }
            }
        }
        return false
    }

    //魅族设置状态栏文字颜色
    fun setFlymeLightStatusBar(activity: Activity,dark:Boolean):Boolean{
        var result = false
        if (activity != null) {
            try {
                val lp = activity.window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                        .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (dark) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                activity.window.attributes = lp
                result = true
            } catch (e: Exception) {
            }

        }
        return result
    }

    //小米设置状态栏文字颜色
    fun setMiUiLightStatusBar(activity: Activity,dark:Boolean):Boolean{
        var result = false
        val window = activity.window
        if (window != null) {
            val clazz = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                }
                result = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && RomUtils.isMiUIV7OrAbove()) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    }
                }
            } catch (e: Exception) {

            }

        }
        return result
    }

    //原生设置状态栏文字颜色
    fun setAndroidLightStatusBar(activity: Activity,dark:Boolean){
        val decor = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    /**
     * 获取屏幕高度；
     */
    fun getScreenHeight(context:Context) :Int {
        return context.getApplicationContext().getResources()
                .getDisplayMetrics().heightPixels
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    fun  dip2px(context: Context, dipValue:Float) :Float{
        val scale = context.getResources().getDisplayMetrics().density;
        return  (dipValue * scale + 0.5f)
    }

    fun  px2dip(context: Context, dipValue:Float) :Float{
        val scale = context.getResources().getDisplayMetrics().density;
        return  (dipValue / scale + 0.5f)
    }




}