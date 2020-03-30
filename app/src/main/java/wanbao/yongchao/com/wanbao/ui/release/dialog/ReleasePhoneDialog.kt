package wanbao.yongchao.com.wanbao.ui.release.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.dialog_release_phone.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

@SuppressLint("ValidFragment")
class ReleasePhoneDialog(val phone:Phone):BaseDialogFragment() {
    override fun setLayoutID(): Int = R.layout.dialog_release_phone

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        Handler().postDelayed(object :Runnable{
            override fun run() {
                edit_phone1.requestFocus()
                val inManager = edit_phone1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
            }

        },200)
    }

    override fun clickListener() {
        Click.viewClick(dialogOver).subscribe {
            dismiss()
        }

        Click.viewClick(tv_cancel).subscribe {
            dismiss()
        }

        Click.viewClick(iv_add).subscribe {
            edit_phone2.visibility= View.VISIBLE
            edit_phone2.requestFocus()
            iv_add.visibility=View.GONE
        }

        Click.viewClick(tv_sure).subscribe {
            if ((edit_phone1.text!=null&&edit_phone1.text.toString().isNotEmpty())||(edit_phone2.text!=null&&edit_phone2.text.toString().isNotEmpty())){
                var list=ArrayList<String>()
                if (edit_phone1.text!=null&&edit_phone1.text.toString().isNotEmpty()){
                    list.add(edit_phone1.text.toString())
                }
                if (edit_phone2.text!=null&&edit_phone2.text.toString().isNotEmpty()){
                    list.add(edit_phone2.text.toString())
                }
                phone.setPhone(list)
                dismiss()
            }else{
                Toast.Tips("请填写联系电话")
            }
        }

    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(edit_phone1.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
    }

    interface Phone{
        fun setPhone(list:ArrayList<String>)
    }
}