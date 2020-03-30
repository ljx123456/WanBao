package wanbao.yongchao.com.wanbao.ui.mine.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean

interface WantActivitiesView:BaseView{
    fun getWantActivitiesRequest(data:ExploreActivityBean)
}