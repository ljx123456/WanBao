package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchLandMarkBean

interface SearchLandMarkView:BaseView{
    fun getSearchLandMark(data:SearchLandMarkBean)
}