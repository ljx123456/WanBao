package wanbao.yongchao.com.wanbao.ui.login.adapter

import com.amap.api.services.core.PoiItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R

class BusinessMapSearchAdapter(list:MutableList<PoiItem>):BaseQuickAdapter<PoiItem,BaseViewHolder>(R.layout.item_map_search,list){
    override fun convert(helper: BaseViewHolder, item: PoiItem) {
        helper.setText(R.id.tv_search_title,item.title)
                .setText(R.id.tv_search_add,item.snippet)
//                .setText(R.id.tv_search_distance,"${item.distance}")
    }
}