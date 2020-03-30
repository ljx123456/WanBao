package wanbao.yongchao.com.wanbao.utils.share

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import wanbao.yongchao.com.wanbao.R
import com.tencent.connect.share.QQShare
import com.tencent.tauth.Tencent
import com.tencent.connect.share.QQShare.SHARE_TO_QQ_TYPE_DEFAULT
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File


object ShareUtil{

    private lateinit var mActivity: Activity
    private var textContent = ""
    private var title=""
    private var url=""
    private var img=""

    private var mTencent: Tencent?=null

    fun shareWx(activity: Activity,textContent: String,title :String,url:String){
        this.mActivity=activity
        this.textContent=textContent
        this.url=url
        if (isPackageInstalled("com.tencent.mm")) {
            wechatShare(true)
        }else{
            Toast.Tips("请安装微信后重试")
        }
    }

    fun shareWxCircle(activity: Activity,textContent: String,title :String,url:String){
        this.mActivity=activity
        this.textContent=textContent
        this.url=url
        if (isPackageInstalled("com.tencent.mm")) {
            wechatShare(false)
        }else{
            Toast.Tips("请安装微信后重试")
        }
    }

    //开始分享-微信
    fun wechatShare(type: Boolean) {//true 朋友圈
        val api = WXAPIFactory.createWXAPI(mActivity, null)
        api.registerApp("wx8730168ee272799a")
        var webpage = WXWebpageObject()
        webpage.webpageUrl = url
        var msg = WXMediaMessage(webpage)
        var thumb = BitmapFactory.decodeResource(mActivity.getResources(), R.mipmap.ic_app)
        if (thumb != null) {
            var mBp = Bitmap.createScaledBitmap(thumb, 120, 120, true)
            thumb.recycle()
            msg.thumbData = Util.bmpToByteArray(mBp, true)
        }
        var req = SendMessageToWX.Req()

//        req.transaction = SendMessageToWX.Req.buildTransaction("webpage")
        if (type) {
            msg.title = title
//            msg.description = textContent
            Log.e("测试分享", textContent)
            req.message = msg
            req.transaction ="${System.currentTimeMillis()}"//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
            req.scene = SendMessageToWX.Req.WXSceneTimeline
        }
        else {
            msg.title = title
            msg.description = textContent
            Log.e("测试分享",textContent)
            req.message = msg
            req.transaction ="${System.currentTimeMillis()}"//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
            req.scene = SendMessageToWX.Req.WXSceneSession
        }
        api.sendReq(req)
    }

    //开始分享-QQ
    fun QQShare(activity: Activity,title: String,content:String,url:String){
        if (isPackageInstalled("com.tencent.mobileqq")||isPackageInstalled("com.tencent.tim")) {
            this.mActivity = activity
            if (mTencent == null) {
                mTencent = Tencent.createInstance("101844107", mActivity)
            }
            val params = Bundle()
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title)
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  content)
//            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html")
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  url)
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://pic.bixinyule.com/FgrNYcoxmzvdef9Hy4F-GbOAp8TU")
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222")
            params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能")
            mTencent!!.shareToQQ(mActivity, params, object : IUiListener {
                override fun onComplete(p0: Any?) {

                }

                override fun onCancel() {
                    Toast.Tips("取消")
                }

                override fun onError(p0: UiError?) {
                    Toast.Tips("失败")
                }
            })
        }else{
            Toast.Tips("请安装QQ后重试")
        }
    }

    //判断包名是否存在
    fun isPackageInstalled(packageName: String): Boolean {
        return File("/data/data/$packageName").exists()
    }

}