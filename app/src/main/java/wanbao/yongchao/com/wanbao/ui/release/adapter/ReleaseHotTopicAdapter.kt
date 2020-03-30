package wanbao.yongchao.com.wanbao.ui.release.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.HotTopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchTopicBean

class ReleaseHotTopicAdapter(list: MutableList<HotTopicBean.DataBean>): BaseQuickAdapter<HotTopicBean.DataBean, BaseViewHolder>(R.layout.item_release_hot_topic,list){
    override fun convert(helper: BaseViewHolder, item: HotTopicBean.DataBean) {

        helper.setText(R.id.tv_topic,item.name)

        if (item.dynamicNum>0){
            helper.setText(R.id.tv_num,item.dynamicNum.toString()+"次参与")
        }else{
            helper.setText(R.id.tv_num,"暂时无人参与")
        }

    }

}