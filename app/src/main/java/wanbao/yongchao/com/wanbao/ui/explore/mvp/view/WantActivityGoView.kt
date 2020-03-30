package wanbao.yongchao.com.wanbao.ui.explore.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantGoBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantUserBean

interface WantActivityGoView:BaseView{
    fun getWantActivityGoRequest(data: WantUserBean)
}