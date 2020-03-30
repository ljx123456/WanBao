package wanbao.yongchao.com.wanbao.ui.explore.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean

interface ExploreActivityFragmentView: BaseView {
    fun getExploreActivityRequest(data: ExploreActivityBean)
}