package wanbao.yongchao.com.wanbao.ui.explore.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotShopBean

class SearchHotShopAdapter(list: MutableList<SearchHotShopBean.DataBean>):BaseQuickAdapter<SearchHotShopBean.DataBean,BaseViewHolder>(R.layout.item_search_topic,list){
    override fun convert(helper: BaseViewHolder, item: SearchHotShopBean.DataBean) {

        helper.setText(R.id.tv_topic,item.businessName)
                .setText(R.id.tv_num,item.clockNum.toString()+"次打卡")

    }
}