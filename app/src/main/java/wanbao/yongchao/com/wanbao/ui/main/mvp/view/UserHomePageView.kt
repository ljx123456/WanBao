package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.UserHomePageBean

interface UserHomePageView:BaseView{
    fun getUserHomePageRequest(data:UserHomePageBean)
    fun getUserHomePageError()

    fun getFocusRequest()

    fun getUnFocusRequest()

//    fun getUserHomePageForNameRequest(data:UserHomePageBean)
//    fun getUserHomePageForNameError()
}