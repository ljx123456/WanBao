package wanbao.yongchao.com.wanbao.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by Administrator on 2016/9/6.
 */
public class ShowDialog {

    /**
     * * 显示自定义dialog
     * "知道了" 只有一行的提示信息
     *
     * @param context
     * @param title
     * @param message
     * @param negativeButtonText
     * @param listener
     */
    public static void showCustomDialog(Context context, String title, String message, String negativeButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setNegativeButton(negativeButtonText, listener).create().show();
    }

    /**
     * * 显示自定义dialog不显示头部
     * "知道了" 只有一行的提示信息
     *
     * @param context
     * @param message
     * @param negativeButtonText
     * @param listener
     */
    public static void showCustomDialog(Context context, String message, String negativeButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setMessage(message).setNegativeButton(negativeButtonText, listener).create().show();
    }

    /**
     * 不显示头部
     */
    public static void showCustomDialogs(Context context, String message, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setMessage(message).setPositiveButton(positiveButtonText, listener).setNegativeButton(negativeButtonText, listener).create().show();
    }

    public static void showCustomDialogNoTitle(Context context, String message, String positiveButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setMessage(message).setPositiveButton(positiveButtonText, listener).create().show();
    }

    public static void showCustomDialogNoTitle(Context context, String message, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setMessage(message).setPositiveButton(positiveButtonText, listener).setNegativeButton(negativeButtonText, listener).create().show();
    }


    /**
     * 显示自定义dialog（删除地址）
     * "是 否"  两个按钮的提示信息
     *
     * @param context
     * @param title
     * @param message
     * @param positiveButtonText
     * @param negativeButtonText
     * @param listener
     */
    public static CustomDialog.Builder showCustomDialog(Context context, int id, int position, String title, String message, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setId(id).setPosition(position).setTitle(title).setMessage(message).setPositiveButton(positiveButtonText, listener).setNegativeButton(negativeButtonText, listener);
        return builder;
    }

    /**
     * 显示自定义dialog
     * "是 否"  两个按钮的提示信息
     *
     * @param context
     * @param title
     * @param message
     * @param positiveButtonText
     * @param negativeButtonText
     * @param listener
     * @return
     */
    public static void showCustomDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(positiveButtonText, listener).setNegativeButton(negativeButtonText, listener).create().show();
    }

    public static void showCustomDialog(Context context, String title, String message, String positiveButtonText, String zhongjian, String negativeButtonText, DialogInterface.OnClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(positiveButtonText, listener).setNegativeButton(negativeButtonText, listener).create().show();
    }
}
