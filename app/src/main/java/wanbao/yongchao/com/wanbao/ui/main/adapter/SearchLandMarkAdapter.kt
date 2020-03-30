package wanbao.yongchao.com.wanbao.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchLandMarkBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class SearchLandMarkAdapter(list: MutableList<SearchLandMarkBean.DataBean>):BaseQuickAdapter<SearchLandMarkBean.DataBean,BaseViewHolder>(R.layout.item_search_landmark,list){
    override fun convert(helper: BaseViewHolder, item: SearchLandMarkBean.DataBean) {

        helper.setText(R.id.tv_name,item.cityMapName)
        ImageLoad.setImage(item.avatar,helper.getView(R.id.iv_content) as RoundedImageView)

    }

}