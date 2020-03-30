package wanbao.yongchao.com.wanbao.ui.release.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.dialog_release_ye_address.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService



@SuppressLint("ValidFragment")
class ReleaseYeAddressDialog(val address:Address):BaseDialogFragment() {
    override fun setLayoutID(): Int = R.layout.dialog_release_ye_address

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        Handler().postDelayed(object :Runnable{
            override fun run() {
                edit_address.requestFocus()
                val inManager = edit_address.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
            }

        },200)
    }

    override fun clickListener() {
        Click.viewClick(dialogOver).subscribe {
            dismiss()
        }
        edit_address.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s.toString().isNotEmpty()){
                    tv_sure.isEnabled=true
                    tv_sure.setTextColor(Color.parseColor("#fffcc725"))
                }else{
                    tv_sure.isEnabled=false
                    tv_sure.setTextColor(Color.parseColor("#73fcc725"))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        Click.viewClick(tv_cancel).subscribe {
            dismiss()
        }

        Click.viewClick(tv_sure).subscribe {
            dismiss()
            address.setAddress(edit_address.text.toString())
        }
    }


    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(edit_address.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
    }

    interface Address{
        fun setAddress(address:String)
    }
}