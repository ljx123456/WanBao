package wanbao.yongchao.com.wanbao.utils.utils

import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext


/**
 * Created by Administrator on 2018/2/1 0001.
 */
object Toast {

    fun Tips(msg: String) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 150)
        ToastUtils.setBgColor(getContext().resources.getColor(R.color.toastBg))
        ToastUtils.setMsgColor(getContext().resources.getColor(R.color.toastColor))
        ToastUtils.showShort(msg)
    }

    fun LongTips(msg: String) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 150)
        ToastUtils.setBgColor(getContext().resources.getColor(R.color.toastBg))
        ToastUtils.setMsgColor(getContext().resources.getColor(R.color.toastColor))
        ToastUtils.showLong(msg)
    }
}