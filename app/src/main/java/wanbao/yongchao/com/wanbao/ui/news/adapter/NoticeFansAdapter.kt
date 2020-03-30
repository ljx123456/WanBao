package wanbao.yongchao.com.wanbao.ui.news.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.NoticeFansBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class NoticeFansAdapter(list: MutableList<NoticeFansBean.DataBean>):BaseQuickAdapter<NoticeFansBean.DataBean,BaseViewHolder>(R.layout.item_notice_fans,list) {
    override fun convert(helper: BaseViewHolder, item: NoticeFansBean.DataBean) {
        helper.setText(R.id.tv_name,item.user.nickname)
                .setText(R.id.tv_time,item.formatTime)
                .addOnClickListener(R.id.tv_focus)
        ImageLoad.setUserHead(item.user.avatar,helper.getView(R.id.iv_header))
        var tv=helper.getView<TextView>(R.id.tv_focus)
        if (item.user.focusType!=null) {
            if (item.user.focusType == 1) {//关注类型：1 已关注，2 被关注，3 互关
                helper.setBackgroundRes(R.id.tv_focus,R.drawable.tv_followed_bg)
                        .setTextColor(R.id.tv_focus,Color.parseColor("#fcc725"))
                        .setText(R.id.tv_focus,"已关注")
            }else if (item.user.focusType==2){
                helper.setBackgroundRes(R.id.tv_focus,R.drawable.tv_follow_bg)
                        .setTextColor(R.id.tv_focus,Color.parseColor("#000000"))
                        .setText(R.id.tv_focus,"关注")
            }else if (item.user.focusType==3){
                helper.setBackgroundRes(R.id.tv_focus,R.drawable.tv_followed_bg)
                        .setTextColor(R.id.tv_focus,Color.parseColor("#fcc725"))
                        .setText(R.id.tv_focus,"相互关注")
            }else{
                helper.setBackgroundRes(R.id.tv_focus,R.drawable.tv_follow_bg)
                        .setTextColor(R.id.tv_focus,Color.parseColor("#000000"))
                        .setText(R.id.tv_focus,"关注")
            }
        }else{
            helper.setBackgroundRes(R.id.tv_focus,R.drawable.tv_follow_bg)
                    .setTextColor(R.id.tv_focus,Color.parseColor("#000000"))
                    .setText(R.id.tv_focus,"关注")
        }
    }
}