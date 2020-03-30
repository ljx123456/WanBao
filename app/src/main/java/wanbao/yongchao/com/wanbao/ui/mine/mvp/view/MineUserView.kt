package wanbao.yongchao.com.wanbao.ui.mine.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.UserInfoBean

interface MineUserView:BaseView{
    fun getUserInfoRequest(data:UserInfoBean)
    fun getUserInfoError()
}