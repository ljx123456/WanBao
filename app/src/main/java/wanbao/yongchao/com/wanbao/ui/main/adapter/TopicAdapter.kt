package wanbao.yongchao.com.wanbao.ui.main.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.TopicBean

class TopicAdapter(list:MutableList<TopicBean.DataBean>):BaseQuickAdapter<TopicBean.DataBean,BaseViewHolder>(R.layout.item_topic,list){
    override fun convert(helper: BaseViewHolder, item: TopicBean.DataBean) {
        helper.setText(R.id.tv_item_topic,item.name)
        if (item.isFlag){
            helper.setBackgroundRes(R.id.tv_item_topic,R.drawable.tv_topic_bg)
                    .setTextColor(R.id.tv_item_topic,Color.parseColor("#000000"))
        }else{
            helper.setBackgroundColor(R.id.tv_item_topic,Color.parseColor("#212123"))
                    .setTextColor(R.id.tv_item_topic,Color.parseColor("#FCC725"))
        }
    }

}