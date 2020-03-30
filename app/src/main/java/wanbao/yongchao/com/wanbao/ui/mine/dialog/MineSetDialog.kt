package wanbao.yongchao.com.wanbao.ui.mine.dialog

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.dialog_mine_set.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class MineSetDialog(val dialog:SetDialog):BaseDialogFragment() {
    override fun setLayoutID(): Int = R.layout.dialog_mine_set

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {

    }

    override fun clickListener() {
        Click.viewClick(tv_data).subscribe {
            dialog.setData()
            dismiss()
        }

        Click.viewClick(tv_collect).subscribe {
            dialog.setCollect()
            dismiss()
        }

        Click.viewClick(tv_want).subscribe {
            dialog.setWant()
            dismiss()
        }

        Click.viewClick(layout_more).subscribe {
            dialog.setMoreSet()
            dismiss()
        }

        Click.viewClick(iv_set).subscribe { dismiss() }

        Click.viewClick(dialogOver).subscribe { dismiss() }
    }

    interface SetDialog{
        fun setData()
        fun setCollect()
        fun setWant()
        fun setMoreSet()
    }
}