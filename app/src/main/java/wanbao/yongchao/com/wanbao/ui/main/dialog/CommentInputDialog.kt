package wanbao.yongchao.com.wanbao.ui.main.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.dialog_comment_input.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.utils.utils.AtColorUtil
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllSpan

@SuppressLint("ValidFragment")
class CommentInputDialog(val send:Send):BaseDialogFragment(),AtDialog.AtList{
    override fun atList(list: ArrayList<FansBean.DataBean>) {
        user=""
        list.forEach {
//            user=user+"@${it.nickname} &h;"
            user=user+"@${it.nickname} "
        }
        Log.e("测试edit",edit.text.toString())
//        text=text+user
        edit.setText(edit.text.toString()+user)
    }

    var user=""
//    var text=""


    override fun setLayoutID(): Int = R.layout.dialog_comment_input

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
//        getActivity()!!.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        var mentionList=edit.getMentionList(true)
        edit.setMentionTextColor(Color.parseColor("#FCC725"))
        edit.setPattern("@[\\u4e00-\\u9fa5_a-zA-Z0-9]{2,16}+ ")
        edit.setOnMentionInputListener {
            mentionList.forEach {
                Log.e("测试edit",it)
            }
        }
//        edit.insert()

    }

    override fun clickListener() {
        Click.viewClick(over_edit).subscribe {
            edit.setText("")
            val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
            dismiss()
        }

        Click.viewClick(iv_send).subscribe {
            if (edit.text!=null&&edit.text.toString().isNotEmpty()){
                if (edit.text.toString().length<=140) {
                    Log.e("测试edit发送", edit.text.toString())
                    var mentionList = edit.getMentionList(true)
                    var str = edit.text.toString()
                    if (mentionList.size > 0) {
                        mentionList.forEach {
                            str = str.replace(it, "${it}&h;")
                        }
                    }
                    Log.e("测试edit发送后", str)
                    send.send(str)
                    edit.setText("")
                    val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
                    dismiss()
                }
            }else{
                Toast.Tips("请输入内容")
            }
        }

        Click.viewClick(iv_at).subscribe {
            var dialog=AtDialog()
            dialog.setAtDialog(this)
//            if (user==""){
//                text=edit.text.toString()
//            }
            val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
            dialog.show(childFragmentManager,"")
        }

        edit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (edit.text!=null){
                    if (edit.text.toString().length<=140){
                        tv_size.setTextColor(Color.parseColor("#40ffffff"))
                        tv_size.text=edit.text.toString().length.toString()+"/140"
                    }else{
                        tv_size.setTextColor(Color.parseColor("#FC4625"))
                        tv_size.text=edit.text.toString().length.toString()+"/140"
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })




    }

    fun setHint(str:String){
//            this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
//        var imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT)
        Handler().postDelayed(object :Runnable{
            override fun run() {
                edit.hint=str
                edit.requestFocus()
                var imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT)
            }

        },200)

    }

    interface Send{
        fun send(str:String)
    }
}