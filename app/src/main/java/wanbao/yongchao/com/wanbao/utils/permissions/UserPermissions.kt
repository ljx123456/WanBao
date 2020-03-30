package wanbao.yongchao.com.wanbao.utils.permissions

import android.Manifest
import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.mylhyl.acp.Acp
import com.mylhyl.acp.AcpListener
import com.mylhyl.acp.AcpOptions
import com.tbruyelle.rxpermissions2.RxPermissions
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.utils.Toast

/**
 * Created by Administrator on 2018/3/12.
 */
object UserPermissions {

    //内存卡读写权限
    fun userSD(context: Context, face: MemoryReadPermissionsFace, what: Int = 0) {
        var rxPermissions = RxPermissions(context as Activity)
        rxPermissions.request(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE).subscribe { aBoolean ->
            if (aBoolean) face.requestPermissionsFaceSucceed(context, what) else ShowDialog.showCustomDialogNoTitle(context,"由于无法获取设备信息的权限,应用无法运行，请前往设置中心应用权限页设置","去设置","取消",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            dialog.dismiss()
                            gotoSet(context)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
            })
        }
    }

    //相机权限
    fun userCamera(context: Context, face: MemoryReadPermissionsFace, what: Int = 0) {
        var rxPermissions = RxPermissions(context as Activity)
        rxPermissions.request(CAMERA).subscribe { aBoolean ->
            if (aBoolean) face.requestPermissionsFaceSucceed(context, what) else Toast.Tips("请打开APP所需权限→设置→应用→权限管理")
        }
    }

    fun userLocation(context: Context, face: MemoryReadPermissionsFace, what: Int = 0) {
        var rxPermissions = RxPermissions(context as Activity)
        rxPermissions.request(ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION).subscribe { aBoolean ->
            if (aBoolean) face.requestPermissionsFaceSucceed(context, what) else face.requestPermissionsFaceError()
        }
    }

    fun gotoSet(context: Context){
        var intent = Intent()
        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.setData(Uri.parse("package:" + (context as Activity).getPackageName()))
        (context as Activity).startActivity(intent)
    }

    fun userRecord(): Boolean {
        var type = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Acp.getInstance(getContext()).request(AcpOptions.Builder()
                    .setPermissions(
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA)
                    .build(),
                    object : AcpListener {
                        override fun onGranted() {
                            type = true
                        }

                        override fun onDenied(permissions: List<String>) {
                            Toast.Tips("请打开APP所需权限→设置→应用→权限管理")
                            type = false
                        }
                    })
        } else {
            type = true
        }
        return type
    }


    interface MemoryReadPermissionsFace {
        fun requestPermissionsFaceSucceed(context: Context, what: Int)
        fun requestPermissionsFaceError()
    }
}