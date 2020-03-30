package wanbao.yongchao.com.wanbao.ui.login.activity

import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.NetworkUtils
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.tencent.connect.common.UIListenerManager
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import kotlinx.android.synthetic.main.activity_login_phone.*
import org.json.JSONObject
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.CodeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.LoginThirdBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.UserBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.GetCodeBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.LoginThirdBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.GetCodePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.LoginThirdPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.GetCodeView
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.LoginThirdView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.lang.Exception
import java.util.regex.Pattern

class LoginPhoneActivity :BaseActivity(),GetCodeView,LoginThirdView{
    override fun getLoginThirdRequest(data: LoginThirdBean) {
        user.setWxCode("")
        if (data.data.isIsBind){
            intentUtils.intentLoginBind(token)
        }else{
            if (data.data.data.role==1){//用户
                try {
                    DBUtils.DelBusiness()
                }catch (e: Exception){

                }
                var user= UserBean.DataBeanX()
                user.data= UserBean.DataBeanX.DataBean()
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
                }catch (e: Exception){

                }
                Log.e("测试","商家登陆")
                var user= BusinessBean.DataBeanX()
                user.data= BusinessBean.DataBeanX.DataBean()
                user.token=data.data.token
                user.data.accountState=data.data.data.accountState
                user.data.authState=data.data.data.authState
                user.data.avatar=data.data.data.avatar
                user.data.nickname=data.data.data.nickname
                user.data.role=data.data.data.role
                user.data.id=data.data.data.id.toString()
                DBUtils.setBusinessDB(user)
            }
            intentUtils.intentMain()
        }
    }

    private val presenter by lazy { GetCodePresenter(this,this,this) }
    private val thirdPresenter by lazy { LoginThirdPresenter(this,this,this) }

    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private lateinit var mAccessToken: Oauth2AccessToken
    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private lateinit var mSsoHandler: SsoHandler

    private var mTencent: Tencent?=null


    //微信
    private var api: IWXAPI? = null
    private val APP_ID = "wx8730168ee272799a"



    override fun getGetCodeRequest(data: CodeBean) {

    }

//    val str="^(13[0-9]|14[5-9]|15[0-3,5-9]|16[2,5,6,7]|17[0-8]|18[0-9]|19[1,3,5,8,9])\\d{8}$"

    override fun openEventBus(): Boolean =false

    override fun getActivityLayout(): Int =R.layout.activity_login_phone

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        // 创建微博实例
        mSsoHandler = SsoHandler(this)
    }

    override fun clickListener() {
        edit_login_phone.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s.toString().length>=1){
                    login_phone_next.isClickable=true
                    login_phone_next.setBackgroundResource(R.drawable.login_phone_next_bg)
                    iv_login_phone_next.setImageResource(R.mipmap.signin_icon_next)
                    Click.viewClick(login_phone_next).subscribe {
                        if (NetworkUtils.isConnected()){
                            if (edit_login_phone.text.toString().length==11&&checkPhoneNum(edit_login_phone.text.toString())){
                                tv_phone_tips.visibility= View.GONE
                                presenter.getCode(GetCodeBody(edit_login_phone.text.toString(),"0"))
                                intentUtils.intentLoginCode(edit_login_phone.text.toString())
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
            intentUtils.intentMain()
        }



        Click.viewClick(login_wechat).subscribe {
//            intentUtils.intentLoginBind()
            if (isPackageInstalled("com.tencent.mm")) {
                val api = WXAPIFactory.createWXAPI(this, APP_ID)
                val req = SendAuth.Req()
                req.scope = "snsapi_userinfo"
                req.state = "wechat_sdk_demo_test"
                api.sendReq(req)
            }else{
                Toast.Tips("请安装微信后重试")
            }
        }

        Click.viewClick(login_weibo).subscribe {
            if (isPackageInstalled("com.sina.weibo")) {
                mSsoHandler.authorize(SelfWbAuthListener())
            }else{
                Toast.Tips("请安装微博后重试")
            }
        }

        Click.viewClick(login_qq).subscribe {
            if (isPackageInstalled("com.tencent.mobileqq")||isPackageInstalled("com.tencent.tim")) {
                if (mTencent == null) {
                    mTencent = Tencent.createInstance("101844107", applicationContext)
                }
                if (!mTencent!!.isSessionValid()) {
                    mTencent!!.login(this, "all", loginListener)
                }
            }else{
                Toast.Tips("请安装QQ后重试")
            }
        }


    }

    var token=""
    var expires_in=""
    var uniqueCode=""
    private var loginListener=object :IUiListener{
        override fun onComplete(p0: Any) {
            uniqueCode = (p0 as JSONObject).optString("openid")//QQ的openid
            try {
                token = (p0 as JSONObject).getString("access_token")
                expires_in = (p0 as JSONObject).getString("expires_in")
                var body=LoginThirdBody()
                body.thirdPartyType="1"
                body.thirdPartyCode=token
                body.qqOpenId=uniqueCode
                body.osType="1"
                thirdPresenter.getLoginThird(body)
            }catch (e:Exception){
                Log.e("测试","异常")
            }
        }

        override fun onCancel() {

        }

        override fun onError(e: UiError) {
            Log.e("onError:", "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail)
        }
    }

    override fun onResume() {
        super.onResume()
        if (user.getWxCode()!=""){
            token=user.getWxCode()
            var body=LoginThirdBody()
            body.thirdPartyType="2"
            body.thirdPartyCode=token
            body.osType="1"
            thirdPresenter.getLoginThird(body)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data)
        }

        if (mTencent!=null){
            UIListenerManager.getInstance().onActivityResult(requestCode, resultCode, data,loginListener)
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

    //判断包名是否存在
    fun isPackageInstalled(packageName: String): Boolean {
        return File("/data/data/$packageName").exists()
    }

    private inner class SelfWbAuthListener : com.sina.weibo.sdk.auth.WbAuthListener {
        override fun onSuccess(token: Oauth2AccessToken) {
            this@LoginPhoneActivity.token = token.token
            var body=LoginThirdBody()
            body.thirdPartyType="3"
            body.thirdPartyCode=token.token
            body.osType="1"
            thirdPresenter.getLoginThird(body)
//            this@LoginPhoneActivity.runOnUiThread(Runnable {
//                mAccessToken = token
//
//                if (mAccessToken.isSessionValid) {
//                    // update Token
//
//                    // save Token to SharedPreferences
//                    AccessTokenKeeper.writeAccessToken(this@LoginPhoneActivity, mAccessToken)
//
//                }
//            })
        }

        override fun cancel() {

        }

        override fun onFailure(errorMessage: WbConnectErrorMessage) {

        }
    }



}