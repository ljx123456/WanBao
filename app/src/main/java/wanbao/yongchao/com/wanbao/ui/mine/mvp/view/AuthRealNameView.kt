package wanbao.yongchao.com.wanbao.ui.mine.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthRealNameBean

interface AuthRealNameView:BaseView{
    fun getAuthRealNameRequest(data:AuthRealNameBean)
}