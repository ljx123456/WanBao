package wanbao.yongchao.com.wanbao.ui.explore.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import java.math.BigDecimal

class ExploreShopAdapter(list: MutableList<ExploreShopBean.DataBean>):BaseQuickAdapter<ExploreShopBean.DataBean,BaseViewHolder>(R.layout.item_explore_shop,list) {
    override fun convert(helper: BaseViewHolder, item: ExploreShopBean.DataBean) {
        ImageLoad.setImage(item.avatar,helper.getView(R.id.item_image))
        helper.setText(R.id.tv_name,item.businessName)
                .setText(R.id.tv_type,item.cityInfo)
                .setText(R.id.tv_distance,item.km)
//        if (item.km<1000){
//            helper.setText(R.id.tv_distance,item.km.toString()+"m")
//        }else{
//            helper.setText(R.id.tv_distance,BigDecimal(item.km).divide(BigDecimal(1000),1,BigDecimal.ROUND_DOWN).toString()+"km")
//        }
    }
}