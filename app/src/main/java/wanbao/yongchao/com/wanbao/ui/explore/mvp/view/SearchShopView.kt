package wanbao.yongchao.com.wanbao.ui.explore.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotShopBean

interface SearchShopView:BaseView{
    fun getSearchHotShopRequest(data:SearchHotShopBean)

    fun getSearchShopRequest(data:ExploreShopBean)

}