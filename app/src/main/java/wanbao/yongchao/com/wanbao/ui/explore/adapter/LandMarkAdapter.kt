package wanbao.yongchao.com.wanbao.ui.explore.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreLandMarkBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class LandMarkAdapter(list: MutableList<ExploreLandMarkBean.DataBean>): BaseQuickAdapter<ExploreLandMarkBean.DataBean, BaseViewHolder>(R.layout.item_landmark,list){
    override fun convert(helper: BaseViewHolder, item: ExploreLandMarkBean.DataBean) {

        helper.setText(R.id.tv_name,item.cityMapName)
                .setText(R.id.tv_num,item.wantUserNum.toString()+"人想去")
        ImageLoad.setImage(item.avatar,helper.getView(R.id.iv_content) as RoundedImageView)

    }

}