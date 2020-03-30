package wanbao.yongchao.com.wanbao.ui.explore.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ActivitiesDetailsBean

interface ActivitiesDetailsView:BaseView{
    fun getActivitiesDetailsRequest(data:ActivitiesDetailsBean)
    fun getActivitiesDetailsError()

    fun getWantGoRequest()

    fun getFocusRequest()

    fun getUnFocusRequest()
}