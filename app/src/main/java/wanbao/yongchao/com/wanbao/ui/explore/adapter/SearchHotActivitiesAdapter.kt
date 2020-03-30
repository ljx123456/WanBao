package wanbao.yongchao.com.wanbao.ui.explore.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.SearchHotActivitiesBean

class SearchHotActivitiesAdapter(list: MutableList<SearchHotActivitiesBean.DataBean>):BaseQuickAdapter<SearchHotActivitiesBean.DataBean,BaseViewHolder>(R.layout.item_search_topic,list){
    override fun convert(helper: BaseViewHolder, item: SearchHotActivitiesBean.DataBean) {

        helper.setText(R.id.tv_topic,item.title)
                .setText(R.id.tv_num,item.wantNum.toString()+"人标记想去")

    }
}