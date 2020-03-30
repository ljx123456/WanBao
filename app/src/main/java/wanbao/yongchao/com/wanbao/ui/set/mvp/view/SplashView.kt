package wanbao.yongchao.com.wanbao.ui.set.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateBean

interface SplashView : BaseView {
    fun getUpdateRequest(data: UpdateBean)
    fun getUpdateError(code:Int,message:String)
}