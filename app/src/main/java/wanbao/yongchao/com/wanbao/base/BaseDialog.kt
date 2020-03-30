package wanbao.yongchao.com.wanbao.base

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager

/**
 * Created by Administrator on 2018/2/23 0023.
 */
open abstract class BaseDialog : Dialog {

    private var dialogView: View? = null

    constructor(context: Context?, themeResId: Int) : super(context, themeResId) {
        initDialogLayout()
    }

    private fun initDialogLayout() {
        dialogView = layoutInflater.inflate(setDialogLayout(), null)
        setContentView()
        initDialogWindow()
        setDialogViewData()
        setOnClickListener()
        setCancelable(setDialogCancelable())
    }


    private fun initDialogWindow() {
        val window = window
        window.setGravity(setDialogGravity())
        val lp = window.attributes
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
    }

    abstract fun setContentView()

    abstract fun setDialogLayout(): Int

    abstract fun setDialogGravity(): Int

    abstract fun setDialogCancelable(): Boolean

    abstract fun setDialogViewData()

    abstract fun setOnClickListener()

}