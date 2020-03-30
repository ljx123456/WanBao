package wanbao.yongchao.com.wanbao.ui.explore.activity

import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_banner_web.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WebBannerBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WebBannerBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.WebBannerPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.WebBannerView
import wanbao.yongchao.com.wanbao.utils.utils.Click

class WebBannerActivity:BaseActivity(),WebBannerView{
    override fun getWebBannerRequest(data: WebBannerBean) {
        if (data.data!=null){
            tv_title.text=data.data.name
            wv_broker.loadDataWithBaseURL(null,data.data.htmlContent,"text/html","UTF-8",null)
        }
    }

    private val presenter by lazy { WebBannerPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_banner_web

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        presenter.getWebBanner(WebBannerBody(intent.getStringExtra("id")))
        wv_broker.webViewClient=object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                wv_broker.loadUrl(url)
                return true
            }
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