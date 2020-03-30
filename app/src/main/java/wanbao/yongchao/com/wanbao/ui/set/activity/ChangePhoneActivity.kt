package wanbao.yongchao.com.wanbao.ui.set.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.NetworkUtils
import kotlinx.android.synthetic.main.activity_change_phone.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.CodeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.GetCodeBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.GetCodePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.GetCodeView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import java.util.regex.Pattern

class ChangePhoneActivity: BaseActivity(), GetCodeView {

    private val presenter by lazy { GetCodePresenter(this,this,this) }

    override fun getGetCodeRequest(data: CodeBean) {
        intentUtils.intentChangePhoneCode(edit_login_phone.text.toString(),intent.getStringExtra("token"))
    }

//    val str="^(13[0-9]|14[5-9]|15[0-3,5-9]|16[2,5,6,7]|17[0-8]|18[0-9]|19[1,3,5,8,9])\\d{8}$"

    override fun openEventBus(): Boolean =false

    override fun getActivityLayout(): Int = R.layout.activity_change_phone

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

    }

    override fun clickListener() {
        edit_login_phone.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s.toString().length>=1){
                    login_phone_next.isClickable=true
                    login_phone_next.setBackgroundResource(R.drawable.login_phone_next_bg)
                    iv_login_phone_next.setImageResource(R.mipmap.signin_icon_next)
                    Click.viewClick(login_phone_next).subscribe {
                        if (NetworkUtils.isConnected()){
                            if (edit_login_phone.text.toString().length==11&&checkPhoneNum(edit_login_phone.text.toString())){
                                tv_phone_tips.visibility= View.GONE
                                var body=GetCodeBody()
                                body.account=edit_login_phone.text.toString()
                                body.type="1"
                                body.token=intent.getStringExtra("token")
                                presenter.getCode(body)
                            }else{
                                tv_phone_tips.visibility= View.VISIBLE
                                tv_phone_tips.setText("请输入正确的手机号")
                            }
                        }else{
                            tv_phone_tips.visibility= View.VISIBLE
                            tv_phone_tips.setText("无法连接网络，请检查当前网络状况")
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

        Click.viewClick(login_phone_close).subscribe {
            finish()
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