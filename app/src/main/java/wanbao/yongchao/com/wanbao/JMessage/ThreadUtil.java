package wanbao.yongchao.com.wanbao.JMessage;

import android.os.Handler;

/**
 * Created by ${chenyn} on 2017/3/10.
 */

public class ThreadUtil {
    static Handler mHandler = new Handler();

    public static void runInThread(Runnable task) {
        new Thread(task).start();
    }

    public static void runInUiThread(Runnable task) {

        try {
            mHandler.post(task);
        }catch (Exception e){

        }

    }
}