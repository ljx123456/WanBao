package wanbao.yongchao.com.wanbao.ui.news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeLikeBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class NoticeLikeAdapter(list: MutableList<NoticeLikeBean.DataBean>):BaseQuickAdapter<NoticeLikeBean.DataBean,BaseViewHolder>(R.layout.item_notice_like,list) {
    override fun convert(helper: BaseViewHolder, item: NoticeLikeBean.DataBean) {
        ImageLoad.setUserHead(item.user.avatar,helper.getView(R.id.iv_header))
        if (item.type==2){//2 点赞（动态），3 点赞（评论）
            helper.setText(R.id.tv_tips,"赞了你发布的动态")
            if (item.dynamic!=null&&item.dynamic.images!=null&&item.dynamic.images.size>0){
                helper.setVisible(R.id.iv_other,true)
                ImageLoad.setImage(item.dynamic.images[0],helper.getView(R.id.iv_other))
            }else{
                helper.setVisible(R.id.iv_other,false)
            }
        }else{
            helper.setVisible(R.id.iv_other,false)
                    .setText(R.id.tv_tips,"赞了你发表的评论")
        }

        helper.setText(R.id.tv_name,item.user.nickname)
                .setText(R.id.tv_time,item.formatTime)

    }
}