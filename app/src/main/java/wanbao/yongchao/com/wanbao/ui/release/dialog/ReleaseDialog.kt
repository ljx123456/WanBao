package wanbao.yongchao.com.wanbao.ui.release.dialog

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_release.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.mask.FastBlur
import wanbao.yongchao.com.wanbao.utils.utils.Click

class ReleaseDialog: BaseDialogFragment() {
    override fun setLayoutID(): Int = R.layout.dialog_release

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
//        var h=Handler().postDelayed(object :Runnable{
//            override fun run() {
//                applyBlur()
//            }
//        },500)
        var mCache = ACache.get(activity!!)
        mCache.getAsString("HistoryRelease")
        if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
            ShowDialog.showCustomDialogNoTitle(activity!!,"当前有未发布的动态草稿，是否编辑重发？","去编辑","放弃草稿",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            dialog.dismiss()
                            intentUtils.intentRelease()
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                            var mCache = ACache.get(activity!!)
                            mCache.put("HistoryRelease", "")
                        }
                    }
                }
            })
        }
    }

    override fun clickListener() {
        Click.viewClick(close).subscribe {
            dismiss()
        }

        Click.viewClick(tv_release_text).subscribe {
            dismiss()
            intentUtils.intentRelease()
        }
    }

    fun applyBlur() {
        val view = activity!!.getWindow().getDecorView()
        view.setDrawingCacheEnabled(true)
        view.buildDrawingCache(true)
        /**
         * 获取当前窗口快照，相当于截屏
         */
        var bmp1 = view.getDrawingCache()
//        final Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg)
        var height = getOtherHeight()
        /**
         * 除去状态栏和标题栏
         */
        var bmp2 = Bitmap.createBitmap(bmp1, 0, height,bmp1.getWidth(), bmp1.getHeight() - height)
        blur(bmp2,content)
    }

    @SuppressLint("NewApi")
    fun blur(bkg:Bitmap ,view: View ) {
        var startMs = System.currentTimeMillis()
        var scaleFactor = 8f//图片缩放比例；
        var radius = 20f//模糊程度

        var overlay = Bitmap.createBitmap(
                 (view.getMeasuredWidth() / scaleFactor).toInt(), (view.getMeasuredHeight() / scaleFactor).toInt(), Bitmap.Config.ARGB_8888)
        var canvas = Canvas(overlay)
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()/ scaleFactor)
        canvas.scale(1 / scaleFactor, 1 / scaleFactor)
        var paint = Paint()
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0f, 0f, paint)


        overlay = FastBlur.doBlur(overlay, radius.toInt(), true)
        view.setBackground(BitmapDrawable(getResources(), overlay))
        /**
         * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，可是将scaleFactor设置大一些。
         */
        Log.i("jerome", "blur time:" + (System.currentTimeMillis() - startMs))
    }

    /**
     * 获取系统状态栏和软件标题栏，部分软件没有标题栏，看自己软件的配置；
     * @return
     */
    fun getOtherHeight() :Int{
        val frame =  Rect()
        activity!!.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame)
        var statusBarHeight = frame.top
        var contentTop = activity!!.getWindow().findViewById<View>(Window.ID_ANDROID_CONTENT).getTop()
        var titleBarHeight = contentTop - statusBarHeight
        return statusBarHeight + titleBarHeight
    }

}