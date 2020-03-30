package wanbao.yongchao.com.wanbao.ui.set.activity

import android.content.DialogInterface
import kotlinx.android.synthetic.main.activity_change_phone_code.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.dialog.AgreementDialog
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.CodeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.LoginCodeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.UserBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.GetCodeBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.LoginCodeBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.GetCodePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.LoginCodePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.GetCodeView
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.LoginCodeView
import wanbao.yongchao.com.wanbao.ui.login.utils.CodeTime
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.ChangePhoneBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.ChangePhoneBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.presenter.ChangePhonePresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.ChangePhoneView
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.lang.Exception

class ChangePhoneCodeActivity: BaseActivity(), GetCodeView ,ChangePhoneView{
    override fun getChangePhoneRequest(data: ChangePhoneBean) {
        Toast.Tips("手机更换成功")
        intentUtils.intentAccount()
    }


    override fun getGetCodeRequest(data: CodeBean) {

    }

    private val codeTime = CodeTime()
    private val codePresenter by lazy { GetCodePresenter(this,this,this) }
    private val presenter by lazy { ChangePhonePresenter(this,this,this) }


    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_change_phone_code

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        codeTime.codeCountTimer(login_code)
        if (intent!=null&&intent.getStringExtra("phone")!=null){
            login_code_title.text="验证码已发送至："+intent.getStringExtra("phone")
        }
    }

    override fun clickListener() {
        Click.viewClick(login_code_back).subscribe { finish() }

        pc_login.finish ={
            pc_login.clear()
            presenter.getChangePhone(ChangePhoneBody(intent.getStringExtra("phone"),it))
        }

        Click.viewClick(login_code).subscribe {
            var body=GetCodeBody()
            body.account=intent.getStringExtra("phone")
            body.type="1"
            body.token=intent.getStringExtra("token")
            codePresenter.getCode(body)
            codeTime.codeCountTimer(login_code)
        }
    }
}