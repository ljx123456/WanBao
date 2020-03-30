package wanbao.yongchao.com.wanbao.jpush;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import wanbao.yongchao.com.wanbao.R;
import wanbao.yongchao.com.wanbao.ui.main.activity.MainActivity;


public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    public NotificationUtils(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent ma = PendingIntent.getActivity(this, 0, intent, 0);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        Notification.Builder builder;
        if (resolveInfo != null) {
            builder = new Notification.Builder(getApplicationContext(), id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(ma)
                    .setSmallIcon(R.mipmap.ic_app)
                    .setAutoCancel(true);
        } else {
            builder = new Notification.Builder(getApplicationContext(), id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(ma)
                    .setSmallIcon(R.mipmap.ic_app)
                    .setAutoCancel(true);
        }

        return builder;
    }

    public NotificationCompat.Builder getNotification_25(String title, String content) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent ma = PendingIntent.getActivity(this, 0, intent, 0);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        NotificationCompat.Builder builder;
        if (resolveInfo != null) {
            builder = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(ma)
                    .setSmallIcon(R.mipmap.ic_app)
                    .setAutoCancel(true);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(R.mipmap.ic_app)
                    .setContentIntent(ma)
                    .setAutoCancel(true);
        }


        return builder;
    }

    public void sendNotification(String data) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            Notification notification = getChannelNotification
                    ("晚豹", data).build();
            getManager().notify(1, notification);
        } else {
            Notification notification = getNotification_25("晚豹", data).build();
            getManager().notify(1, notification);
        }
    }
}
