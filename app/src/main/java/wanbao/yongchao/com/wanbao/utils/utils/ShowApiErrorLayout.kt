package wanbao.yongchao.com.wanbao.utils.utils

import android.view.View

/**
 * Created by Administrator on 2018/8/16 0016.
 */
object ShowApiErrorLayout {


    //请求接口失败后操作
    fun showApiErrorLayout(viewGroup: View, errorLayout: View) {
        viewGroup.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
    }


    //接口请求成功后调用
    fun showApiSucceedLayout(viewGroup: View, errorLayout: View) {
        viewGroup.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE
    }
    
}