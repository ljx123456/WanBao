package wanbao.yongchao.com.wanbao.ui.explore.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.WantUserBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class WantAdapter(list: MutableList<WantUserBean.DataBean>):BaseQuickAdapter<WantUserBean.DataBean,BaseViewHolder>(R.layout.item_community_like,list){
    override fun convert(helper: BaseViewHolder, item: WantUserBean.DataBean) {
        ImageLoad.setUserHead(item.avatar,helper.getView(R.id.item_likeHeader) as RoundedImageView)
        helper.setText(R.id.item_likeName,item.nickname)
        if (item.signature!=null&&item.signature!=""){
            helper.setText(R.id.item_likeContent,item.signature)
        }else{
            helper.setText(R.id.item_likeContent,"暂无简介")
        }
    }
}