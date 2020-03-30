package wanbao.yongchao.com.wanbao.ui.mine.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchLandMarkBean

interface WantLandMarkView:BaseView{
    fun getLandMarkRequest(data:SearchLandMarkBean)
}