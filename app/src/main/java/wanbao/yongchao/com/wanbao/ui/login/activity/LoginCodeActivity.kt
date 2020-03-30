package wanbao.yongchao.com.wanbao.ui.login.activity

import android.content.DialogInterface
import android.util.Log
import kotlinx.android.synthetic.main.activity_login_code.*
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
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.lang.Exception

class LoginCodeActivity :BaseActivity(),GetCodeView,LoginCodeView{
    override fun getLoginCodeRequest(data: LoginCodeBean) {
        if (data.data!=null){
            if (data.data.isIsRegistry){
                user.setAccount(intent.getStringExtra("phone"))
                var dialog= AgreementDialog(this@LoginCodeActivity)
                dialog.setOnClickBottomListener(object : AgreementDialog.OnClickBottomListener{
                    override fun onPositiveClick() {
                        dialog.dismiss()
                        intentUtils.intentRegisterIdentity()
                    }

                    override fun onNegtiveClick() {
                        dialog.dismiss()
                        finish()
                    }

                    override fun onAgreement1Click() {
                        intentUtils.intentAgreement("用户协议")
                    }

                    override fun onAgreement2Click() {
                        intentUtils.intentAgreement("隐私协议")
                    }
                }).show()
            }else{
                if (data.data.data.role==1){//用户
                    try {
                        DBUtils.DelBusiness()
                    }catch (e:Exception){

                    }
                    var user=UserBean.DataBeanX()
                    user.data=UserBean.DataBeanX.DataBean()
                    user.token=data.data.token
                    user.data.accountState=data.data.data.accountState
                    user.data.authState=data.data.data.authState
                    user.data.avatar=data.data.data.avatar
                    user.data.nickname=data.data.data.nickname
                    user.data.role=data.data.data.role
                    user.data.id=data.data.data.id.toString()
                    DBUtils.setUserDB(user)
                }else{//商家
                    try {
                        DBUtils.DelUser()
                    }catch (e:Exception){

                    }
                    Log.e("测试","商家登陆")
                    var user=BusinessBean.DataBeanX()
                    user.data=BusinessBean.DataBeanX.DataBean()
                    user.token=data.data.token
                    user.data.accountState=data.data.data.accountState
                    user.data.authState=data.data.data.authState
                    user.data.avatar=data.data.data.avatar
                    user.data.nickname=data.data.data.nickname
                    user.data.role=data.data.data.role
                    user.data.id=data.data.data.id.toString()
                    DBUtils.setBusinessDB(user)
                }
                user.setChange(true)
                intentUtils.intentMain()
            }
        }
    }

    override fun getLoginCodeError() {

    }

    override fun getGetCodeRequest(data: CodeBean) {

    }

    private val codeTime = CodeTime()
    private val codePresenter by lazy { GetCodePresenter(this,this,this) }
    private val loginPresenter by lazy { LoginCodePresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_login_code

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
        Click.viewClick(login_code_close).subscribe {
            ShowDialog.showCustomDialogNoTitle(this,"退出将放弃注册\n是否确定？","放弃注册","取消",object :DialogInterface.OnClickListener{
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
//        pc_login.setOnInputListener(object : PhoneCode.OnInputListener{
//            override fun onSucess(code: String?) {
//                //todo
//                pc_login.clean()
//
//            }
//
//            override fun onInput() {
//            }
//        })
        pc_login.finish ={
//            Toast.Tips(it)
            pc_login.clear()
            loginPresenter.getLogin(LoginCodeBody(intent.getStringExtra("phone"),"0",it))

        }

        Click.viewClick(login_code).subscribe {
            codePresenter.getCode(GetCodeBody(intent.getStringExtra("phone"),"0"))
            codeTime.codeCountTimer(login_code)
        }
    }
}