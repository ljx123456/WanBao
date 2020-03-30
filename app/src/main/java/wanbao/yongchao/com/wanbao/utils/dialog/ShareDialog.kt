package wanbao.yongchao.com.wanbao.utils.dialog

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.dialog_share.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class ShareDialog(val share: Share) : BaseDialogFragment() {

    override fun setLayoutID(): Int = R.layout.dialog_share

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
//        setLocation(Gravity.BOTTOM)
    }

    override fun clickListener() {
        Click.viewClick(shareOff).subscribe { dismiss() }
        //微信分享
        Click.viewClick(shareWX).subscribe {
            share.setShareWX()
            dismiss()
        }
        //朋友圈分享
        Click.viewClick(sharePYQ).subscribe {
            share.setSharePYQ()
            dismiss()
        }

        //QQ分享
        Click.viewClick(shareQQ).subscribe {
            share.setShareQQ()
            dismiss()
        }
    }

    interface Share {
        fun setShareWX()
        fun setSharePYQ()
        fun setShareQQ()
    }
}