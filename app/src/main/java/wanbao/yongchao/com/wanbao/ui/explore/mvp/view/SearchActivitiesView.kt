package wanbao.yongchao.com.wanbao.ui.explore.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotActivitiesBean

interface SearchActivitiesView:BaseView{

    fun getSearchHotActivitiesRequest(data:SearchHotActivitiesBean)

    fun getSearchActivitiesRequest(data:ExploreActivityBean)
}