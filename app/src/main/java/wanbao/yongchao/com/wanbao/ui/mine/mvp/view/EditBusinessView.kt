package wanbao.yongchao.com.wanbao.ui.mine.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditBusinessBean

interface EditBusinessView:BaseView{
    fun getEditBusinessRequest(data:EditBusinessBean)
}