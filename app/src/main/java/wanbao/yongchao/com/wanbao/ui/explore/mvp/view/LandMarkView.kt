package wanbao.yongchao.com.wanbao.ui.explore.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreLandMarkBean

interface LandMarkView:BaseView{
    fun getLandMarkRequest(data:ExploreLandMarkBean)
}