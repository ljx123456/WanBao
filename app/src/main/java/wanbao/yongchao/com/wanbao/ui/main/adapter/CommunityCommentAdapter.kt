package wanbao.yongchao.com.wanbao.ui.main.adapter

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityCommentBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.AtColorUtil
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllSpan
import java.math.BigDecimal

class CommunityCommentAdapter(list: MutableList<CommunityCommentBean.DataBean.CommentsBean>):BaseQuickAdapter<CommunityCommentBean.DataBean.CommentsBean,BaseViewHolder>(R.layout.item_comment,list){
    override fun convert(helper: BaseViewHolder, item: CommunityCommentBean.DataBean.CommentsBean) {
        helper.setText(R.id.item_commentName,item.nickname)
                .setText(R.id.item_commentTime,item.createTime)

        helper.getView<TextView>(R.id.item_commentContent).setMovementMethod(LinkMovementMethod.getInstance())
        helper.setText(R.id.item_commentContent,AtColorUtil.getShowAllText(item.content,object :ShowAllSpan.OnAllSpanClickListener{
            override fun onClick(widget: View?) {

            }

            override fun onClickAt(widget: View?, name: String) {
//                Toast.Tips(name)
                intentUtils.intentUserHomePageForName(name)
            }
        }))


        if (item.hasReply==0){
            helper.setGone(R.id.item_commentMore,false)
        }else{
            helper.setGone(R.id.item_commentMore,true)
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
        var b=BigDecimal(item.likeNum)
        if (item.likeNum>=1000){
            helper.setText(R.id.item_commentLike,b.divide(BigDecimal("1000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"k")
        }else if (item.likeNum>=10000){
            helper.setText(R.id.item_commentLike,b.divide(BigDecimal("10000")).setScale(1,BigDecimal.ROUND_DOWN).toString()+"w")
        }else{
            helper.setText(R.id.item_commentLike,b.toString())
        }

        ImageLoad.setUserHead(item.avatar,helper.getView(R.id.item_commentHeader) as RoundedImageView)

        if (item.bean!=null&&item.bean.avatar.isNotEmpty()){
            helper.setGone(R.id.item_commentLayout,true)
                    .setText(R.id.item_commentAddName,item.bean.name)
                    .setText(R.id.item_commentAddContent,item.bean.content)
            ImageLoad.setUserHead(item.bean.avatar,helper.getView(R.id.item_commentAddHeader) as RoundedImageView)
        }else{
            helper.setGone(R.id.item_commentLayout,false)
        }

        helper.addOnClickListener(R.id.item_commentHeader)
                .addOnClickListener(R.id.item_commentName)
                .addOnClickListener(R.id.item_commentLike)
                .addOnClickListener(R.id.item_commentContent)
                .addOnClickListener(R.id.item_commentMore)
                .addOnClickListener(R.id.item_commentAddLike)

    }
}