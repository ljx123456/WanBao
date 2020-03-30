package wanbao.yongchao.com.wanbao.ui.release.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseYeBean

interface ReleaseYeView:BaseView{
    fun getReleaseYeRequest(data:ReleaseYeBean)
    fun getReleaseYeError()
}