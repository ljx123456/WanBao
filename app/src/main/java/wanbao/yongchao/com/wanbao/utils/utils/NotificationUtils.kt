package wanbao.yongchao.com.wanbao.utils.utils

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.support.v4.app.NotificationCompat

/**
 * Created by Administrator on 2018/2/1 0001.
 */
object NotificationUtils  {

    private var title = "pyx_utils"
    private var logo = android.R.drawable.ic_btn_speak_now


    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        NotificationUtils.title = title
    }

    fun getLogo(): Int {
        return logo
    }

    fun setLogo(logo: Int) {
        NotificationUtils.logo = logo
    }


    fun notification(mContext: Context, content: String) {
        val mNotificationManager = mContext.getSystemService(NOTIFICATION_SERVICE) as? NotificationManager
        val mBuilder = NotificationCompat.Builder(mContext)
        mBuilder.setContentTitle(title)
        mBuilder.setContentText(content)
        mBuilder.setTicker(title)
        mBuilder.setWhen(System.currentTimeMillis())

        mBuilder.setOngoing(false)
        mBuilder.setAutoCancel(true)
        mBuilder.setSmallIcon(logo)
        mBuilder.setDefaults(Notification.DEFAULT_ALL)
        mNotificationManager?.notify(1, mBuilder.build())
    }
}