package wanbao.yongchao.com.wanbao.ui.news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeBean

class NoticeAdapter(list: MutableList<NoticeBean.DataBean>):BaseQuickAdapter<NoticeBean.DataBean,BaseViewHolder>(R.layout.item_news_notice,list) {
    override fun convert(helper: BaseViewHolder, item: NoticeBean.DataBean) {
        helper.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_content,item.content)
                .setText(R.id.tv_time,item.formatTime)
        if (item.jumpType==0){//跳转类型：0 不跳转，1 个人中心，2 活动，3 认证
            helper.setVisible(R.id.iv_more,false)
        }else{
            helper.setVisible(R.id.iv_more,true)
        }
    }
}