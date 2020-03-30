package wanbao.yongchao.com.wanbao.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.*
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN



/**
 * Created by Administrator on 2018/5/10 0010.
 */
open abstract class BaseDialogFragment : DialogFragment() {

    private lateinit var layoutView: View
    private var location = Gravity.CENTER


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutView = inflater.inflate(setLayoutID(), container, false)
        setLayoutData()
        clickListener()
        return layoutView
    }

    override fun getView(): View? {
        return layoutView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        if (isFullScreen()) dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onActivityCreated(savedInstanceState)
        if (isFullScreen()) {
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
            dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
//            dialog.window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)//全屏遮罩但是会有界面重新绘制，出现抖一下
        } else {
            if (location == Gravity.BOTTOM) {
                dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                dialog.window.setFlags(WindowManager.LayoutParams.MATCH_PARENT, FLAG_FULLSCREEN)
                dialog.window.setGravity(location)
            } else if (location == Gravity.CENTER) {
                dialog.window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
                dialog.window.setGravity(location)
            } else {
                dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            }

        }

    }

    fun setLocation(loc: Int) {
        this.location = loc
    }


    abstract fun setLayoutID(): Int

    abstract fun isFullScreen(): Boolean

    abstract fun setLayoutData()

    abstract fun clickListener()


    fun showDialog(activity: BaseActivity, tag: String = "") {
        show(activity.supportFragmentManager, tag)
    }

    fun showDialog(context: Context, tag: String = "") {
        try {
            show((context as BaseActivity).supportFragmentManager, tag)
        }catch (e:Exception){
//            show((context as com.example.shadow.heartrecreation.ui.main.base.BaseActivity).supportFragmentManager, tag)
        }

    }

    fun showDialog(fragmentManager: FragmentManager?, tag: String = "") {
        show(fragmentManager, tag)
    }


}