package wanbao.yongchao.com.wanbao.ui.explore.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreBannerBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreLandMarkBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean

interface ExploreView:BaseView{
    fun getExploreBannerRequest(data:ExploreBannerBean)

    fun getExploreLandMarkRequest(data:ExploreLandMarkBean)

    fun getExploreShopRequest(data:ExploreShopBean)

    fun getExploreBannerError()

    fun getExploreLandMarkError()

    fun getExploreShopError()


}