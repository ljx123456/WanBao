package wanbao.yongchao.com.wanbao.ui.login.activity

import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_login_bind_phone.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.CodeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.GetCodeBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.GetCodePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.GetCodeView
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import java.util.regex.Pattern

class LoginBindPhoneActivity : BaseActivity(), GetCodeView {
    override fun getGetCodeRequest(data: CodeBean) {

    }

    //    val str="^(13[0-9]|14[5-9]|15[0-3,5-9]|16[2,5,6,7]|17[0-8]|18[0-9]|19[1,3,5,8,9])\\d{8}$"
    private var code=""
    private val presenter by lazy { GetCodePresenter(this,this,this) }


    override fun openEventBus(): Boolean =false

    override fun getActivityLayout(): Int = R.layout.activity_login_bind_phone

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        tv_bind_title.text="应《中国人民共和国网络安全法》要求\n为了更好地保障您的账号安全，请绑定手机号"
        if (intent.getStringExtra("code")!=null&&intent.getStringExtra("code")!=""){
            code=intent.getStringExtra("code")
        }
    }

    override fun clickListener() {
        edit_login_phone.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s.toString().length>=1){
                    login_phone_next.isClickable=true
                    login_phone_next.setBackgroundResource(R.drawable.login_phone_next_bg)
                    iv_login_phone_next.setImageResource(R.mipmap.signin_icon_next)
                    Click.viewClick(login_phone_next).subscribe {
                        if (code!="") {
                            if (edit_login_phone.text.toString().length == 11 && checkPhoneNum(edit_login_phone.text.toString())) {
                                presenter.getCode(GetCodeBody(edit_login_phone.text.toString(),"0",code))
                                intentUtils.intentLoginCode(edit_login_phone.text.toString(),code)
                                tv_phone_tips.visibility = View.GONE
                            } else {
                                tv_phone_tips.visibility = View.VISIBLE
                            }
                        }
                    }
                }else{
                    login_phone_next.isClickable=false
                    login_phone_next.setBackgroundResource(R.drawable.btn_next_bg)
                    iv_login_phone_next.setImageResource(R.mipmap.signin_icon_next_default)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        Click.viewClick(login_bind_back).subscribe {
            finish()
        }

        Click.viewClick(login_phone_close).subscribe {
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




    }

    /***
     * 手机号码检测
     */
    fun checkPhoneNum(num: String): Boolean{
        val regExp = "^(13[0-9]|14[5-9]|15[0-3,5-9]|16[2,5,6,7]|17[0-8]|18[0-9]|19[1,3,5,8,9])\\d{8}\$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(num)
        return m.matches()
    }

}