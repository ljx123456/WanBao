package wanbao.yongchao.com.wanbao.base

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import cn.jpush.android.api.BasicPushNotificationBuilder
import cn.jpush.android.api.JPushInterface
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.event.NotificationClickEvent
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.eventbus.EventBus
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.reactivex.internal.functions.Functions.emptyConsumer
import io.reactivex.plugins.RxJavaPlugins
import jiguang.chat.activity.ChatActivity
import jiguang.chat.application.JGApplication
import jiguang.chat.entity.Event
import jiguang.chat.entity.EventType
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper.initDatabase
import wanbao.yongchao.com.wanbao.qiniu.SendAppFile.initConfiguration

class AppProject:BaseApplication(){


    override fun initCreate() {
        //context初始化
        BaseContext.initContext(this)

        //RxJava异常处理
//        RxJavaPlugins.setErrorHandler {
//            Log.e("测试","onRxJavaErrorHandler ---->: $it")
//        }
        RxJavaPlugins.setErrorHandler(emptyConsumer())
        //初始化数据库
        initDatabase(this)

        //初始化极光
        initJPush(this)

        //初始化微博
        initWeibo()

        //注册到微信
        regToWx()

        initImageLoader()
    }

    override fun initLibrary() {
        //七牛云初始化
        initConfiguration()
    }

    override fun initDataSave() {

    }

    fun initJPush(context: Context) {
        JPushInterface.setDebugMode(true)
        JPushInterface.init(context)
        setStyleBasic(context)

        JMessageClient.setDebugMode(true)
        JMessageClient.init(context)
        JMessageClient.registerEventReceiver(this)
//        setTag(context)
    }

    /**
     * 激光推送设置通知提示方式 - 基础属性
     */
    private fun setStyleBasic(context: Context) {
//        val builder = BasicPushNotificationBuilder(context)
//        builder.statusBarDrawable = R.mipmap.ic_launcher
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
//        builder.notificationDefaults = Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS
//        JPushInterface.setPushNotificationBuilder(1, builder)

        val builder = BasicPushNotificationBuilder(context)
        builder.statusBarDrawable = R.mipmap.ic_app
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_SHOW_LIGHTS  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = (Notification.DEFAULT_SOUND
                or Notification.DEFAULT_VIBRATE
                or Notification.DEFAULT_LIGHTS)  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder)

    }

    private fun initImageLoader() {
        val configuration = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(configuration)

    }

    /**
     * 初始化微博
     */

    private fun initWeibo(){
        var authInfo=AuthInfo(this,"22909893","https://api.weibo.com/oauth2/default.html",null)
//        var mWBAPI=WBAPIFactory.createWBAPI(this)
//        mWBAPI.registerApp(this,authInfo)
        WbSdk.install(this,authInfo)
    }

    /**
     * 初始化微信
     */
    fun regToWx(){
        val APP_ID = "wx8730168ee272799a"
        var api: IWXAPI = WXAPIFactory.createWXAPI(this, APP_ID, true)
        api.registerApp(APP_ID)
    }

    public fun onEvent(event: NotificationClickEvent){
        var mUserInfo=event.message.fromUser
        var intent= Intent()
        intent.setClass(this, ChatActivity::class.java)
        //创建会话
        intent.putExtra(JGApplication.TARGET_ID, mUserInfo.getUserName())
        intent.putExtra(JGApplication.TARGET_APP_KEY, mUserInfo.getAppKey())
        var notename = mUserInfo.getNotename()
        if (TextUtils.isEmpty(notename)) {
            notename = mUserInfo.getNickname()
            if (TextUtils.isEmpty(notename)) {
                notename = mUserInfo.getUserName()
            }
        }
        intent.putExtra(JGApplication.CONV_TITLE, notename)
        var conv: Conversation? = JMessageClient.getSingleConversation(mUserInfo.getUserName(), mUserInfo.getAppKey())
        //如果会话为空，使用EventBus通知会话列表添加新会话
        if (conv == null) {
            conv = Conversation.createSingleConversation(mUserInfo.getUserName(), mUserInfo.getAppKey())
            EventBus.getDefault().post(Event.Builder()
                    .setType(EventType.createConversation)
                    .setConversation(conv)
                    .build())
        }
        startActivity(intent)
    }
}