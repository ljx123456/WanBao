package wanbao.yongchao.com.wanbao.utils.dialog

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.blankj.utilcode.util.ToastUtils
import com.mylhyl.acp.Acp
import com.mylhyl.acp.AcpListener
import com.mylhyl.acp.AcpOptions

object showPhoneDialog {

    fun showDialog(mActivity: Activity, phoneNum: String) {
        ShowDialog.showCustomDialogNoTitle(mActivity, "${phoneNum}", "拨打电话", "取消", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        CallPhone(mActivity, phoneNum)
                        dialog!!.dismiss()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog!!.dismiss()
                    }
                }
            }
        })
    }

    fun CallPhone(mActivity: Activity, phoneNum: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Acp.getInstance(mActivity).request(AcpOptions.Builder()
                    .setPermissions(Manifest.permission.CALL_PHONE)
                    .build(),
                    object : AcpListener {
                        override fun onGranted() {
                            val intent = Intent(Intent.ACTION_DIAL)
                            val data = Uri.parse("tel:$phoneNum")
                            intent.setData(data)
                            mActivity.startActivity(intent)
                        }

                        override fun onDenied(permissions: List<String>) {
                            ToastUtils.showShort("获取系统权限失败，请手动开启")
                        }
                    })
        } else {
            val intent = Intent(Intent.ACTION_DIAL)
            val data = Uri.parse("tel:$phoneNum")
            intent.setData(data)
            mActivity.startActivity(intent)
        }
    }
}