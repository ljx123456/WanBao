package wanbao.yongchao.com.wanbao.ui.mine.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthTypeBean

interface AuthTypeView:BaseView{
    fun getAuthTypeRequest(data:AuthTypeBean)
}