package wanbao.yongchao.com.wanbao.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchTopicBean

class SearchTopicAdapter(list: MutableList<SearchTopicBean.DataBean>):BaseQuickAdapter<SearchTopicBean.DataBean,BaseViewHolder>(R.layout.item_search_topic,list){
    override fun convert(helper: BaseViewHolder, item: SearchTopicBean.DataBean) {

        helper.setText(R.id.tv_topic,item.name)
                .setText(R.id.tv_num,item.dynamicNum.toString()+"次参与")

    }

}