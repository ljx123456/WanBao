package wanbao.yongchao.com.wanbao.ui.main.adapter

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.muzhi.camerasdk.library.utils.T
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentDetailsBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.AtColorUtil
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllSpan
import java.math.BigDecimal

class CommentDetailsAdapter(list: MutableList<CommentDetailsBean.DataBean.CommentsBean>):BaseQuickAdapter<CommentDetailsBean.DataBean.CommentsBean,BaseViewHolder>(R.layout.item_comment_details,list){
    override fun convert(helper: BaseViewHolder, item: CommentDetailsBean.DataBean.CommentsBean) {
        helper.setText(R.id.item_commentName,item.nickname)
                .setText(R.id.item_commentTime,item.createTime)
        ImageLoad.setUserHead(item.avatar,helper.getView(R.id.item_commentHeader))
        var num=""
        if (item.likeNum>=1000&&item.likeNum<10000){
           num =BigDecimal(item.likeNum).divide(BigDecimal("1000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"k"
        }else if (item.likeNum>=10000){
            num =BigDecimal(item.likeNum).divide(BigDecimal("10000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"w"
        }else{
            num =item.likeNum.toString()
        }
        if (item.isLike==0){
            var draw=mContext.resources.getDrawable(R.mipmap.dynamic_button_comment_like_nor)
            draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight())
            helper.getView<TextView>(R.id.item_commentLike).setCompoundDrawables(null,null,draw,null)
        }else{
            var draw=mContext.resources.getDrawable(R.mipmap.homepage_button_list_like_pre)
            draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight())
            helper.getView<TextView>(R.id.item_commentLike).setCompoundDrawables(null,null,draw,null)
        }

        if (item.toUser!=null&&item.toUser.avatar!=""){
            var str="回复@${item.toUser.nickname} &h;："+item.content
            helper.getView<TextView>(R.id.item_commentContent).setMovementMethod(LinkMovementMethod.getInstance())
            helper.setText(R.id.item_commentContent,AtColorUtil.getShowAllText(str,object :ShowAllSpan.OnAllSpanClickListener{
                override fun onClick(widget: View?) {

                }

                override fun onClickAt(widget: View?, name: String) {
                    intentUtils.intentUserHomePageForName(name)
                }
            }))
        }else{
            helper.getView<TextView>(R.id.item_commentContent).setMovementMethod(LinkMovementMethod.getInstance())
            helper.setText(R.id.item_commentContent,AtColorUtil.getShowAllText(item.content,object :ShowAllSpan.OnAllSpanClickListener{
                override fun onClick(widget: View?) {

                }

                override fun onClickAt(widget: View?, name: String) {
                    intentUtils.intentUserHomePageForName(name)
                }
            }))
        }

        helper.setText(R.id.item_commentLike,num)

        helper.addOnClickListener(R.id.item_commentHeader)
                .addOnClickListener(R.id.item_commentName)
                .addOnClickListener(R.id.item_commentLike)
                .addOnClickListener(R.id.item_commentContent)
    }
}