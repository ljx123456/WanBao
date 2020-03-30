package wanbao.yongchao.com.wanbao.ui.explore.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean

interface ShopFragmentView:BaseView{
    fun getShopRequest(data:ExploreShopBean)
}