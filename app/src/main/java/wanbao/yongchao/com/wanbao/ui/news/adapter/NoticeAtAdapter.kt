package wanbao.yongchao.com.wanbao.ui.news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeAtBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class NoticeAtAdapter(list: MutableList<NoticeAtBean.DataBean>):BaseQuickAdapter<NoticeAtBean.DataBean,BaseViewHolder>(R.layout.item_notice_like,list) {
    override fun convert(helper: BaseViewHolder, item: NoticeAtBean.DataBean) {
        ImageLoad.setUserHead(item.user.avatar,helper.getView(R.id.iv_header))
        helper.setText(R.id.tv_name,item.user.nickname)
                .setText(R.id.tv_time,item.formatTime)

        if (item.type==4){
            if (item.dynamic!=null&&item.dynamic.state==2){
                helper.setText(R.id.tv_tips,item.dynamic.content)
            }else{
                helper.setText(R.id.tv_tips,"该动态已删除")
            }
            if (item.dynamic!=null&&item.dynamic.images!=null&&item.dynamic.images.size>0){
                helper.setVisible(R.id.iv_other,true)
                ImageLoad.setImage(item.dynamic.images[0],helper.getView(R.id.iv_other))
            }else{
                helper.setVisible(R.id.iv_other,false)
            }
        }else{

            if (item.type==5){
                if (item.comment!=null&&item.comment.state==2){
                    helper.setText(R.id.tv_tips,item.comment.content)
                }else{
                    helper.setText(R.id.tv_tips,"该评论已删除")
                }
            }else{
                if (item.comment!=null&&item.comment.state==2){
                    helper.setText(R.id.tv_tips,item.comment.content)
                }else{
                    helper.setText(R.id.tv_tips,"该回复已删除")
                }
            }

            helper.setVisible(R.id.iv_other,false)
        }
    }
}