package wanbao.yongchao.com.wanbao.jpush

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.LogUtils

import org.json.JSONObject
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONException
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.main.activity.MainActivity


class JpushReceiver : BroadcastReceiver() {
    private val TAG = "JpushReceiver"
    internal var notifyId = 100
    var text = ""
    fun setTag(context: Context) {
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business=GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var data = user.loadAll()
            var dat=business.loadAll()
            if (data != null && data.size >= 1 && data.get(0).token != null) {
                JPushInterface.setLatestNotificationNumber(context, 20)// 初始化 JPush
                JPushInterface.setAlias(context, data[0].userId) { i, s, set ->
                    if (i == 0) {
                        Log.d("123456", "成功")
                    } else if (i == 6002) {
                        setTag(context)
                        Log.d("123456", "JPush失败")
                    } else {
                        Log.d("123456", (i).toString() + "JPush")
                    }
                }
            }else if (dat != null && dat.size >= 1 && dat.get(0).token != null) {
                JPushInterface.setLatestNotificationNumber(context, 20)// 初始化 JPush
                JPushInterface.setAlias(context, dat[0].businessId) { i, s, set ->
                    if (i == 0) {
                        Log.d("123456", "成功")
                    } else if (i == 6002) {
                        setTag(context)
                        Log.d("123456", "JPush失败")
                    } else {
                        Log.d("123456", (i).toString() + "JPush")
                    }
                }
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        mNotificationManager = context!!.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        EventBus.getDefault().register(this)
        val bundle = intent!!.getExtras()

//        Log.d(TAG, "onReceive - " + intent.action + ", extras: " + printBundle(bundle))
        //自定义消息
        if (JPushInterface.ACTION_REGISTRATION_ID == intent.action) {
            setTag(context)
//            var userId = JPushInterface.getRegistrationID(context)
//            Log.d(TAG, "JPush用户注册成功")
//            JPushInterface.setLatestNotificationNumber(context, 20)// 初始化 JPush
//            JPushInterface.setAlias(context, userId) { i, s, set ->
//                if (i == 0) {
//                    Log.d("123456", "成功")
//                    Log.d("123456", "JPush成功" + userId)
////                    setRegistrationID(applicationContext, userId)
//                } else if (i == 6002) {
//
//                    Log.d("123456", "JPush失败")
//                } else {
//                    Log.d("123456", (i).toString() + "JPush")
//                }
//            }
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
            Log.d(TAG, "接受到推送下来的自定义消息")
            processCustomMessage(context, bundle)
            EventBus.getDefault().postSticky("消息")
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action) {
            Log.d(TAG, "接受到推送下来的通知")
            printBundle(bundle)
            EventBus.getDefault().postSticky("消息")
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
            Log.e("测试", "用户点击打开了通知")
//            var data = Gson().fromJson(text, JpushModel::class.java)
//            when (data.type) {
//                var intent=Inten
//            }
//            var intent = Intent(context, MainActivity::class.java)
//            context.startActivity(intent)
//            val resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
//            if (resolveInfo != null) {
//
//            } else {
//                context.startActivity(intent)
//            }
            openNotification(context!!, bundle)
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.action!!)
        }
    }

    fun getDefalutIntent(context: Context, flags: Int): PendingIntent {
        return PendingIntent.getActivity(context, 1, Intent(), flags)
    }

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel("123456", "晚豹", NotificationManager.IMPORTANCE_DEFAULT)
        channel.setShowBadge(true)
        val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    var mNotificationManager: NotificationManager? = null
    internal var mBuilder: NotificationCompat.Builder? = null

    private fun processCustomMessage(context: Context, bundle: Bundle) {
//        var data = Gson().fromJson(bundle!!.getString(JPushInterface.EXTRA_EXTRA), JpushModel::class.java)
//        LogUtils.a(Gson().toJson(data))
        receivingNotification(context,bundle)

    }


    fun notification(mContext: Context, data: String) {
        val notificationUtils = NotificationUtils(mContext)
        notificationUtils.sendNotification(data)
    }

    private fun receivingNotification(context: Context, bundle: Bundle) {
        val title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)
//        Log.d(TAG, " title : " + title!!)
        val message = bundle.getString(JPushInterface.EXTRA_MESSAGE)
//        Log.d(TAG, "message : " + message!!)
        val extras = bundle.getString(JPushInterface.EXTRA_EXTRA)
        var content=""
        try {
            var json=JSONObject(message)
            var js=JSONObject(extras)
            Log.e("测试消息adad",json.toString())
            Log.e("测试消息啊大大",js.toString())
            content=json.getString("content")
            Log.e("测试消息content",content)
        }catch (e:JSONException){
            content=message
        }
        Log.e("测试消息",content)

//        Log.d(TAG, "extras : " + extras!!)
        notification(context!!, content)
    }

    private fun openNotification(context: Context, bundle: Bundle) {
        val extras = bundle.getString(JPushInterface.EXTRA_MESSAGE)
        var myValue = ""
        Log.e("测试","1233点击打开通知")
        try {
            val extrasJson = JSONObject(extras)
            myValue = extrasJson.optString("myKey")
        } catch (e: Exception) {
            Log.w(TAG, "Unexpected: extras is not a valid json", e)
//            return
        }

        Log.e("测试","xuyao点击打开通知")
            val mIntent = Intent(context, MainActivity::class.java)
            mIntent.putExtras(bundle)
            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(mIntent)

    }

    // 打印所有的 intent extra 数据
    private fun printBundle(bundle: Bundle) {
        val sb = StringBuilder()
        for (key in bundle.keySet()) {
            if (key == JPushInterface.EXTRA_ALERT) {
                text = bundle.get(JPushInterface.EXTRA_EXTRA).toString()
                LogUtils.a(text)
//                var data = Gson().fromJson(text, JpushModel::class.java)
//                LogUtils.a(Gson().toJson(data))
//                if (data.type.equals("4")) {
//                    EventBus.getDefault().post("xianshi")
//                }
            }
        }

//            if (key == JPushInterface.EXTRA_NOTIFICATION_ID) {
//                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key))
//                LogUtils.a("收到的数据" + bundle.getInt(key))
//            } else if (key == JPushInterface.EXTRA_CONNECTION_CHANGE) {
//                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key))
//            } else if (key == JPushInterface.EXTRA_EXTRA) {
//
//                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
//                    Log.i(TAG, "This message has no Extra data")
//                    continue
//                }

//        LogUtils.a(bundle.get(JPushInterface.ACTION_REGISTRATION_ID))
//        LogUtils.a(bundle.get(JPushInterface.ACTION_MESSAGE_RECEIVED))
//        LogUtils.a(bundle.get(JPushInterface.ACTION_NOTIFICATION_RECEIVED))
//        LogUtils.a(bundle.get(JPushInterface.ACTION_NOTIFICATION_OPENED))
//        LogUtils.a(bundle.get(JPushInterface.ACTION_NOTIFICATION_CLICK_ACTION))
//        LogUtils.a(bundle.get(JPushInterface.ACTION_NOTIFICATION_RECEIVED_PROXY))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_REGISTRATION_ID))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_NOTIFICATION_DEVELOPER_ARG0))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_NOTIFICATION_TITLE))//比心娱乐
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_PUSH_ID))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_MSG_ID))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_NOTI_TYPE))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_ALERT))//又有客户发布了匹配你技能的需求，快去看看吧~
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_ALERT_TYPE))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_MESSAGE))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_CONTENT_TYPE))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_TITLE))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_INBOX))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_BIG_TEXT))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_BIG_PIC_PATH))
//  LogUtils.a(bundle.get(JPushInterface.EXTRA_NOTI_PRIORITY))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_NOTI_CATEGORY))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_NOTIFICATION_ID))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_ACTIVITY_PARAM))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_RICHPUSH_FILE_PATH))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_RICHPUSH_FILE_TYPE))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_RICHPUSH_HTML_PATH))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_RICHPUSH_HTML_RES))
//        LogUtils.a(bundle.get(JPushInterface.EXTRA_STATUS))
//                try {
//                    val json = JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))
//                    val it = json.keys()
//
//                    while (it.hasNext()) {
//                        val myKey = it.next().toString()
//                        sb.append("\nkey:" + key + ", value: [" +
//                                myKey + " - " + json.optString(myKey) + "]")
//                    }
//                } catch (e: JSONException) {
//                    Log.e(TAG, "Get message extra JSON error!")
//                }
//
//            } else {
//                sb.append("\nkey:" + key + ", value:" + bundle.getString(key))
//            }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun getEvent(s:String) {
        Log.e("测试eventbus",s)
    }

}