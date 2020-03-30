package wanbao.yongchao.com.wanbao.ui.mine.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthInfoBean

interface UserAuthCenterView:BaseView{
    fun getAuthInfoRequest(data:AuthInfoBean)
}