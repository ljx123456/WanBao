package wanbao.yongchao.com.wanbao.ui.main.dialog

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.dialog_call_phone.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.dialog.showPhoneDialog
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class CallPhoneDialog(val phones:ArrayList<String>):BaseDialogFragment() {
    override fun setLayoutID(): Int = R.layout.dialog_call_phone

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        if (phones.size==1){
            tv_phone1.text=phones[0]
            tv_phone2.visibility= View.GONE
        }else{
            tv_phone1.text=phones[0]
            tv_phone2.text=phones[1]
            tv_phone2.visibility= View.VISIBLE
        }
    }

    override fun clickListener() {
        Click.viewClick(dialogOver).subscribe {
            dismiss()
        }

        Click.viewClick(shareOff).subscribe {
            dismiss()
        }

        Click.viewClick(tv_phone1).subscribe {
            showPhoneDialog.showDialog(activity!!,phones[0])
        }

        Click.viewClick(tv_phone2).subscribe {
            if (phones.size==2) {
                showPhoneDialog.showDialog(activity!!, phones[1])
            }
        }
    }
}