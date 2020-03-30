package wanbao.yongchao.com.wanbao.ui.news.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeCommentBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class NoticeCommentAdapter(list: MutableList<NoticeCommentBean.DataBean>):BaseQuickAdapter<NoticeCommentBean.DataBean,BaseViewHolder>(R.layout.item_notice_like,list){
    override fun convert(helper: BaseViewHolder, item: NoticeCommentBean.DataBean) {
        ImageLoad.setUserHead(item.user.avatar,helper.getView(R.id.iv_header))
        helper.setText(R.id.tv_name,item.user.nickname)
                .setText(R.id.tv_time,item.formatTime)

        if (item.dynamic!=null){
            if (item.dynamic.state==2){
                if (item.comment!=null&&item.comment.state==2){
                    var str="<font color='#d9ffffff'>${item.comment.content}</font>"
                    helper.setText(R.id.tv_tips,Html.fromHtml(str))
                }else{
                    helper.setText(R.id.tv_tips,"该评论已删除")
                }
            }else{
                helper.setText(R.id.tv_tips,"该动态已删除")
            }
        }else if (item.information!=null){
            if (item.information.state==2){
                if (item.comment!=null&&item.comment.state==2){
                    var str="<font color='#d9ffffff'>${item.comment.content}</font>"
                    helper.setText(R.id.tv_tips,Html.fromHtml(str))
                }else{
                    helper.setText(R.id.tv_tips,"该评论已删除")
                }
            }else{
                helper.setText(R.id.tv_tips,"该活动已删除")
            }
        } else{
            if (item.comment!=null&&item.comment.state==2){
                var str="回复了你的评论：<font color='#d9ffffff'>${item.comment.content}</font>"
                helper.setText(R.id.tv_tips,Html.fromHtml(str))
            }else{
                helper.setText(R.id.tv_tips,"该评论已删除")
            }
        }




    }
}