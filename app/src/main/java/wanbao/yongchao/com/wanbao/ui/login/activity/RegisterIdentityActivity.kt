package wanbao.yongchao.com.wanbao.ui.login.activity

import android.content.DialogInterface
import kotlinx.android.synthetic.main.activity_register_identity.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click

class RegisterIdentityActivity :BaseActivity(){
    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_register_identity

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        tv_desc.text="（注：同一手机号仅支持注册一种身份\n且注册后不支持修改）"
    }

    override fun clickListener() {
        Click.viewClick(register_identity_close).subscribe {
            ShowDialog.showCustomDialogNoTitle(this,"退出将放弃注册\n是否确定？","放弃注册","取消",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            dialog.dismiss()
                            intentUtils.intentMain()
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
            })
        }

        Click.viewClick(layout_business).subscribe {
            intentUtils.intentRegisterBusiness()
        }

        Click.viewClick(layout_user).subscribe {
            intentUtils.intentRegisterUser()
        }
    }
}