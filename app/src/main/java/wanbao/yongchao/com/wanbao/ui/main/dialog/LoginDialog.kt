package wanbao.yongchao.com.wanbao.ui.main.dialog

import kotlinx.android.synthetic.main.dialog_login_tips.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click

class LoginDialog:BaseDialogFragment() {
    override fun setLayoutID(): Int = R.layout.dialog_login_tips

    override fun isFullScreen(): Boolean = false

    override fun setLayoutData() {

    }

    override fun clickListener() {
        Click.viewClick(tv_over).subscribe {
            dismiss()
        }

        Click.viewClick(tv_login).subscribe {
            intentUtils.intentLogin()
            dismiss()
        }
    }
}