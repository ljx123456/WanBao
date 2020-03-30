package wanbao.yongchao.com.wanbao.base

import android.content.Context
import wanbao.yongchao.com.wanbao.utils.dialog.LoadDialog


/**
 * Created by Administrator on 2017/12/18 0018.
 */
interface BaseView {

    fun showLoading(context: Context) {
        LoadDialog.show(context)
    }

    fun dismissLoading(context: Context) {
        LoadDialog.dismiss(context)
    }


}