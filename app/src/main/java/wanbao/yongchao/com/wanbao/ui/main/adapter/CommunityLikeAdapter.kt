package wanbao.yongchao.com.wanbao.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityLikeBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class CommunityLikeAdapter(list: MutableList<CommunityLikeBean.DataBean>):BaseQuickAdapter<CommunityLikeBean.DataBean,BaseViewHolder>(R.layout.item_community_like,list){
    override fun convert(helper: BaseViewHolder, item: CommunityLikeBean.DataBean) {
        ImageLoad.setUserHead(item.avatar,helper.getView(R.id.item_likeHeader) as RoundedImageView)
        helper.setText(R.id.item_likeName,item.nickname)
                .setText(R.id.item_likeTime,item.releaseTime)
                .setText(R.id.item_likeContent,item.signature)
    }
}