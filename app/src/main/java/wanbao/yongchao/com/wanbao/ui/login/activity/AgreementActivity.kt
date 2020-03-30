package wanbao.yongchao.com.wanbao.ui.login.activity

import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_agreement.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.utils.http.BaseUrl
import wanbao.yongchao.com.wanbao.utils.utils.Click

class AgreementActivity:BaseActivity(){
    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_agreement

    override fun setActivityTitle() {
        if (intent.getStringExtra("title")!=null){
            tv_title.text=intent.getStringExtra("title")
        }
    }

    override fun initActivityData() {
        wv_broker.webViewClient=object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                wv_broker.loadUrl(url)
                return true
            }
        }
        if (intent.getStringExtra("title")=="用户协议") {
            wv_broker.loadUrl(BaseUrl.HOST_URL+"agr/user")
        }else if (intent.getStringExtra("title")=="隐私协议"){
            wv_broker.loadUrl(BaseUrl.HOST_URL+"agr/privacy")
        }
        wv_broker.getSettings().setUseWideViewPort(true);//将图片调整到适合webView的大小
        wv_broker.getSettings().setLoadWithOverviewMode(true);//缩放至屏幕大小
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe { finish() }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (wv_broker.canGoBack()){
            wv_broker.goBack()
        }else{
            finish()
        }
    }
}