package wanbao.yongchao.com.wanbao.utils.utils

import android.app.Activity
import android.app.AppOpsManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.getRootDirectory
import com.blankj.utilcode.util.LogUtils
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.net.Uri.fromParts
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import java.lang.Exception


class MiuiUtils {
    fun MiuiUtils() {
        throw  UnsupportedOperationException("u can't instantiate me...");
    }

    private val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
    private val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage"
    val REQUEST_CODE_SERVICE_SMS = 100

    private val CHECK_OP_NO_THROW = "checkOpNoThrow";
    private val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    fun isNotificationEnabled(context: Context): Boolean {
        var mAppOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        var appInfo = context.applicationInfo
        var pkg = context.applicationContext.packageName
        var uid = appInfo.uid
        var appOpsClass: Class<*>? = null
        try {
            appOpsClass = Class.forName(AppOpsManager::class.java.name)
            val checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW,
                    Integer.TYPE, Integer.TYPE, String::class.java)
            val opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
            val value = opPostNotificationValue.get(Int::class.java) as Int
            return checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) as Int == AppOpsManager.MODE_ALLOWED
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun isMIUI(): Boolean {
        val device = Build.MANUFACTURER
        LogUtils.v("Build.MANUFACTURER = $device")
        if (device == "Xiaomi") {
            val prop = Properties()
            try {
                prop.load(FileInputStream(File(Environment
                        .getRootDirectory(), "build.prop")))
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }
            return (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null)
        } else {
            return false
        }
    }


    fun goPermissionSettings(context: Activity) {
        var intent: Intent
        try {//MIUI8/9
            intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
//            intent.setClassName("com.miui.securitycenter", "com.android.systemui/.recents.RecentsActivity")
            intent.putExtra("extra_pkgname", context.getPackageName())
            context.startActivityForResult(intent, REQUEST_CODE_SERVICE_SMS)
        } catch (e: ActivityNotFoundException) {
            try {//MIUI5/6
                intent = Intent("miui.intent.action.APP_PERM_EDITOR")
                intent.setClassName("com.miui.securitycenter",
                        "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
                intent.putExtra("extra_pkgname", context.getPackageName())
                context.startActivityForResult(intent, REQUEST_CODE_SERVICE_SMS)
            } catch (e1: ActivityNotFoundException) {
                //应用信息界面
                intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.getPackageName(),
                        null)
                intent.setData(uri)
                context.startActivityForResult(intent, REQUEST_CODE_SERVICE_SMS)
            }
        }
    }
}