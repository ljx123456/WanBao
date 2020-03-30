package wanbao.yongchao.com.wanbao.ui.explore.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreShopBean
import wanbao.yongchao.com.wanbao.ui.image.ImageExploreInfo
import wanbao.yongchao.com.wanbao.ui.image.ImageInfo
import wanbao.yongchao.com.wanbao.ui.image.ImageShopInfo
import wanbao.yongchao.com.wanbao.ui.main.utils.shop_banner.BannerUtil
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import java.math.BigDecimal

class ShopAdapter(list: MutableList<ExploreShopBean.DataBean>):BaseQuickAdapter<ExploreShopBean.DataBean,BaseViewHolder>(R.layout.item_shop,list){
    override fun convert(helper: BaseViewHolder, item: ExploreShopBean.DataBean) {
        ImageLoad.setImage(item.avatar,helper.getView(R.id.iv_shop))
        helper.setText(R.id.tv_name,item.businessName)
                .setText(R.id.tv_type,item.cityInfo)
                .setText(R.id.tv_distance,item.km)
//                .addOnClickListener(R.id.shop_banner)

//        if (item.describe!=null&&item.describe!=""){
//            helper.setText(R.id.tv_content,item.describe)
//        }else{
//            helper.setText(R.id.tv_content,"该商家暂无简介信息…")
//        }

        if (item.clockUsers!=null&&item.clockUsers.size>0){
            helper.setGone(R.id.layout_want,true)
                    .setGone(R.id.tv_none_want,false)
                    .setText(R.id.tv_num,"累计"+item.clockNum.toString()+"次打卡")
            when(item.clockUsers.size){
                1->{
                    helper.setGone(R.id.iv_user1,true)
                            .setGone(R.id.iv_user2,false)
                            .setGone(R.id.iv_user3,false)
                            .setGone(R.id.iv_user4,false)
                            .setGone(R.id.iv_user5,false)

                    ImageLoad.setUserHead(item.clockUsers[0].avatar,helper.getView(R.id.iv_user1))
                }
                2->{
                    helper.setGone(R.id.iv_user1,true)
                            .setGone(R.id.iv_user2,true)
                            .setGone(R.id.iv_user3,false)
                            .setGone(R.id.iv_user4,false)
                            .setGone(R.id.iv_user5,false)

                    ImageLoad.setUserHead(item.clockUsers[0].avatar,helper.getView(R.id.iv_user1))
                    ImageLoad.setUserHead(item.clockUsers[1].avatar,helper.getView(R.id.iv_user2))
                }
                3->{
                    helper.setGone(R.id.iv_user1,true)
                            .setGone(R.id.iv_user2,true)
                            .setGone(R.id.iv_user3,true)
                            .setGone(R.id.iv_user4,false)
                            .setGone(R.id.iv_user5,false)

                    ImageLoad.setUserHead(item.clockUsers[0].avatar,helper.getView(R.id.iv_user1))
                    ImageLoad.setUserHead(item.clockUsers[1].avatar,helper.getView(R.id.iv_user2))
                    ImageLoad.setUserHead(item.clockUsers[2].avatar,helper.getView(R.id.iv_user3))
                }
                4->{
                    helper.setGone(R.id.iv_user1,true)
                            .setGone(R.id.iv_user2,true)
                            .setGone(R.id.iv_user3,true)
                            .setGone(R.id.iv_user4,true)
                            .setGone(R.id.iv_user5,false)

                    ImageLoad.setUserHead(item.clockUsers[0].avatar,helper.getView(R.id.iv_user1))
                    ImageLoad.setUserHead(item.clockUsers[1].avatar,helper.getView(R.id.iv_user2))
                    ImageLoad.setUserHead(item.clockUsers[2].avatar,helper.getView(R.id.iv_user3))
                    ImageLoad.setUserHead(item.clockUsers[3].avatar,helper.getView(R.id.iv_user4))
                }
                5->{
                    helper.setGone(R.id.iv_user1,true)
                            .setGone(R.id.iv_user2,true)
                            .setGone(R.id.iv_user3,true)
                            .setGone(R.id.iv_user4,true)
                            .setGone(R.id.iv_user5,true)

                    ImageLoad.setUserHead(item.clockUsers[0].avatar,helper.getView(R.id.iv_user1))
                    ImageLoad.setUserHead(item.clockUsers[1].avatar,helper.getView(R.id.iv_user2))
                    ImageLoad.setUserHead(item.clockUsers[2].avatar,helper.getView(R.id.iv_user3))
                    ImageLoad.setUserHead(item.clockUsers[3].avatar,helper.getView(R.id.iv_user4))
                    ImageLoad.setUserHead(item.clockUsers[4].avatar,helper.getView(R.id.iv_user5))
                }

            }


        }else{
            helper.setGone(R.id.layout_want,false)
                    .setGone(R.id.tv_none_want,true)
        }

        if (item.businessImages!=null&&item.businessImages.size>0){
            var imagelist = ArrayList<ImageShopInfo>()
            item.businessImages.forEach {
                imagelist.add(ImageShopInfo(it, false, 0,item.businessId))
            }
            BannerUtil.setBanner(helper.getView(R.id.shop_banner), imagelist)
        }else{
            var imagelist = ArrayList<ImageShopInfo>()
            imagelist.add(ImageShopInfo(item.avatar, false, 0,item.businessId))
            BannerUtil.setBanner(helper.getView(R.id.shop_banner), imagelist)
        }


//        if (item.km<1000){
//            helper.setText(R.id.tv_distance,item.km.toString()+"m")
//        }else{
//            helper.setText(R.id.tv_distance, BigDecimal(item.km).divide(BigDecimal(1000),1, BigDecimal.ROUND_DOWN).toString()+"km")
//        }
    }

}