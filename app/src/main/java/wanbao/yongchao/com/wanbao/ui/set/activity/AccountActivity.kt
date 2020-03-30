package wanbao.yongchao.com.wanbao.ui.set.activity

import android.content.DialogInterface
import android.content.Intent
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_acount.*
import org.json.JSONObject
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.LoginThirdBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.UserInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.UserInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.MineUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.MineUserView
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UnbindBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UnbindBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.presenter.AccountPresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.presenter.ThirdBindPresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.AccountView
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.ThirdBindView
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.lang.Exception

class AccountActivity:BaseActivity(), MineUserView,AccountView ,ThirdBindView{
    override fun getThirdBindRequest() {
        user.setWxCode("")
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business = GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null || business != null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                token = info.get(0).token
                presenter.getUserInfo(UserInfoBody(token))
            } else if (inf != null && inf.size > 0) {
                token = inf.get(0).token
                presenter.getUserInfo(UserInfoBody(token))
            }
        }
    }

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
    private var flag=false

    override fun getUnbindRequest(data: UnbindBean) {
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business = GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null || business != null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                token = info.get(0).token
                presenter.getUserInfo(UserInfoBody(token))
            } else if (inf != null && inf.size > 0) {
                token = inf.get(0).token
                presenter.getUserInfo(UserInfoBody(token))
            }
        }
    }

    override fun getUserInfoRequest(data: UserInfoBean) {
        tv_id.text=data.data.id
        phone=data.data.account.replaceRange(3,7,"****")
        tv_phone.text=phone
        if (data.data.wxOpenId!=null&&data.data.wxOpenId!=""){
            tv_wechat.text="已绑定"
            Click.viewClick(layout_wechat).subscribe {
                if (data.data.wxOpenId!=null&&data.data.wxOpenId!=""){
                    ShowDialog.showCustomDialogNoTitle(this,"是否取消该绑定账号？","是","否",object:DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    dialog.dismiss()
                                    unbindPresenter.getUnbind(UnbindBody("2"))
                                }
                                DialogInterface.BUTTON_NEGATIVE -> {
                                    dialog.dismiss()
                                }
                            }
                        }
                    })
                }else{

                }
            }
        }else{
            tv_wechat.text=""
            Click.viewClick(layout_wechat).subscribe {
                if (isPackageInstalled("com.tencent.mm")) {
                    flag = true
                    if (api == null) {
                        api = WXAPIFactory.createWXAPI(this, APP_ID)
                    }
                    val req = SendAuth.Req()
                    req.scope = "snsapi_userinfo"
                    req.state = "wechat_sdk_demo_test"
                    api!!.sendReq(req)
                }else{
                    Toast.Tips("请安装微信后重试")
                }
            }
        }

        if (data.data.qqOpenId!=null&&data.data.qqOpenId!=""){
            tv_QQ.text="已绑定"
            Click.viewClick(layout_QQ).subscribe {
                if (data.data.qqOpenId!=null&&data.data.qqOpenId!=""){
                    ShowDialog.showCustomDialogNoTitle(this,"是否取消该绑定账号？","是","否",object:DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    dialog.dismiss()
                                    unbindPresenter.getUnbind(UnbindBody("1"))
                                }
                                DialogInterface.BUTTON_NEGATIVE -> {
                                    dialog.dismiss()
                                }
                            }
                        }
                    })
                }else{

                }
            }
        }else{
            tv_QQ.text=""
            Click.viewClick(layout_QQ).subscribe {
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

        if (data.data.weiBoId!=null&&data.data.weiBoId!=""){
            tv_weibo.text="已绑定"
            Click.viewClick(layout_weibo).subscribe {
                if (data.data.weiBoId!=null&&data.data.weiBoId!=""){
                    ShowDialog.showCustomDialogNoTitle(this,"是否取消该绑定账号？","是","否",object:DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    dialog.dismiss()
                                    unbindPresenter.getUnbind(UnbindBody("3"))
                                }
                                DialogInterface.BUTTON_NEGATIVE -> {
                                    dialog.dismiss()
                                }
                            }
                        }
                    })
                }else{

                }
            }
        }else{
            tv_weibo.text=""
            Click.viewClick(layout_weibo).subscribe {
                if (isPackageInstalled("com.sina.weibo")) {
                    mSsoHandler.authorize(SelfWbAuthListener())
                }else{
                    Toast.Tips("请安装微博后重试")
                }
            }
        }

        Click.viewClick(layout_account).subscribe {
            ShowDialog.showCustomDialog(this,"更换已绑定的手机号？","当前绑定的手机号为 ${phone}","更换","取消",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            dialog.dismiss()
                            intentUtils.intentChangePhone(token)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
            })
        }








    }

    override fun getUserInfoError() {

    }

    var qq_token=""
    var expires_in=""
    var uniqueCode=""
    private var loginListener=object : IUiListener {
        override fun onComplete(p0: Any) {
            uniqueCode = (p0 as JSONObject).optString("openid")//QQ的openid
            try {
                qq_token = (p0 as JSONObject).getString("access_token")
                expires_in = (p0 as JSONObject).getString("expires_in")
                var body= LoginThirdBody()
                body.thirdPartyType="1"
                body.thirdPartyCode=qq_token
                body.qqOpenId=uniqueCode
                body.osType="1"
                bindPresenter.getThirdBind(body)
            }catch (e: Exception){
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

    private inner class SelfWbAuthListener : com.sina.weibo.sdk.auth.WbAuthListener {
        override fun onSuccess(token: Oauth2AccessToken) {

            var body=LoginThirdBody()
            body.thirdPartyType="3"
            body.thirdPartyCode=token.token
            body.osType="1"
            bindPresenter.getThirdBind(body)
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

    private val presenter by lazy { MineUserPresenter(this,this,this) }
    private val unbindPresenter by lazy { AccountPresenter(this,this,this) }
    private val bindPresenter by lazy { ThirdBindPresenter(this,this,this) }

    private var phone=""
    private var token=""

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_acount

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        // 创建微博实例
        mSsoHandler = SsoHandler(this)
    }

    override fun clickListener() {

        Click.viewClick(back).subscribe {
            finish()
        }



    }

    override fun onResume() {
        super.onResume()
        if (flag){
            flag=false
            if (user.getWxCode()!=""){
                var body=LoginThirdBody()
                body.thirdPartyType="2"
                body.thirdPartyCode=user.getWxCode()
                body.osType="1"
                bindPresenter.getThirdBind(body)
            }
        }else {
            var user = GreenDaoHelper.getDaoSessions().userDBDao
            var business = GreenDaoHelper.getDaoSessions().businessDBDao
            if (user != null || business != null) {
                var info = user.loadAll()
                var inf = business.loadAll()
                if (info != null && info.size > 0) {
                    token = info.get(0).token
                    presenter.getUserInfo(UserInfoBody(token))
                } else if (inf != null && inf.size > 0) {
                    token = inf.get(0).token
                    presenter.getUserInfo(UserInfoBody(token))
                }
            }
        }

    }

    //判断包名是否存在
    fun isPackageInstalled(packageName: String): Boolean {
        return File("/data/data/$packageName").exists()
    }
}