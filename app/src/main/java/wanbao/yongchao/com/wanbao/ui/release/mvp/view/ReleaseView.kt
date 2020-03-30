package wanbao.yongchao.com.wanbao.ui.release.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ReleaseBean

interface ReleaseView:BaseView{
    fun getReleaseCommunityRequest(data: ReleaseBean)
    fun getReleaseCommunityError()
}