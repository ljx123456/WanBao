package wanbao.yongchao.com.wanbao.ui.explore.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class ExploreActivityAdapter(list: MutableList<ExploreActivityBean.DataBean>):BaseQuickAdapter<ExploreActivityBean.DataBean,BaseViewHolder>(R.layout.item_explore_activity,list) {
    override fun convert(helper: BaseViewHolder, item: ExploreActivityBean.DataBean) {

        ImageLoad.setImage(item.previewImg,helper.getView(R.id.iv_content) as RoundedImageView)

        helper.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_content,item.previewContent)
//                .setText(R.id.tv_time,"活动时间："+item.startTime.replace("-","/").substring(0,item.startTime.indexOf(" "))+" - "+item.endTime.replace("-","/").substring(0,item.endTime.indexOf(" ")))


        if (item.wantAvatars!=null&&item.wantAvatars.size>0){
            helper.setVisible(R.id.layout_want,true)
                    .setText(R.id.tv_num,item.wantNum.toString()+"人想去")
            when(item.wantAvatars.size){
                1->{
                    helper.setVisible(R.id.iv_user1,true)
                            .setVisible(R.id.iv_user2,false)
                            .setVisible(R.id.iv_user3,false)
                            .setVisible(R.id.iv_user4,false)
                            .setVisible(R.id.iv_user5,false)

                    ImageLoad.setUserHead(item.wantAvatars[0],helper.getView(R.id.iv_user1))
                }
                2->{
                    helper.setVisible(R.id.iv_user1,true)
                            .setVisible(R.id.iv_user2,true)
                            .setVisible(R.id.iv_user3,false)
                            .setVisible(R.id.iv_user4,false)
                            .setVisible(R.id.iv_user5,false)

                    ImageLoad.setUserHead(item.wantAvatars[0],helper.getView(R.id.iv_user1))
                    ImageLoad.setUserHead(item.wantAvatars[1],helper.getView(R.id.iv_user2))
                }
                3->{
                    helper.setVisible(R.id.iv_user1,true)
                            .setVisible(R.id.iv_user2,true)
                            .setVisible(R.id.iv_user3,true)
                            .setVisible(R.id.iv_user4,false)
                            .setVisible(R.id.iv_user5,false)

                    ImageLoad.setUserHead(item.wantAvatars[0],helper.getView(R.id.iv_user1))
                    ImageLoad.setUserHead(item.wantAvatars[1],helper.getView(R.id.iv_user2))
                    ImageLoad.setUserHead(item.wantAvatars[2],helper.getView(R.id.iv_user3))
                }
                4->{
                    helper.setVisible(R.id.iv_user1,true)
                            .setVisible(R.id.iv_user2,true)
                            .setVisible(R.id.iv_user3,true)
                            .setVisible(R.id.iv_user4,true)
                            .setVisible(R.id.iv_user5,false)

                    ImageLoad.setUserHead(item.wantAvatars[0],helper.getView(R.id.iv_user1))
                    ImageLoad.setUserHead(item.wantAvatars[1],helper.getView(R.id.iv_user2))
                    ImageLoad.setUserHead(item.wantAvatars[2],helper.getView(R.id.iv_user3))
                    ImageLoad.setUserHead(item.wantAvatars[3],helper.getView(R.id.iv_user4))
                }
                5->{
                    helper.setVisible(R.id.iv_user1,true)
                            .setVisible(R.id.iv_user2,true)
                            .setVisible(R.id.iv_user3,true)
                            .setVisible(R.id.iv_user4,true)
                            .setVisible(R.id.iv_user5,true)

                    ImageLoad.setUserHead(item.wantAvatars[0],helper.getView(R.id.iv_user1))
                    ImageLoad.setUserHead(item.wantAvatars[1],helper.getView(R.id.iv_user2))
                    ImageLoad.setUserHead(item.wantAvatars[2],helper.getView(R.id.iv_user3))
                    ImageLoad.setUserHead(item.wantAvatars[3],helper.getView(R.id.iv_user4))
                    ImageLoad.setUserHead(item.wantAvatars[4],helper.getView(R.id.iv_user5))
                }
            }

        }else{
            helper.setVisible(R.id.layout_want,false)
//            helper.setVisible(R.id.iv_user1,false)
//                    .setVisible(R.id.iv_user2,false)
//                    .setVisible(R.id.iv_user3,false)
//                    .setVisible(R.id.iv_user4,false)
//                    .setVisible(R.id.iv_user5,false)
        }

        if (item.sort!=null&&item.sort!=0){
            helper.setGone(R.id.iv_sort,true)
            when(item.sort){
                1->{
                    helper.setImageResource(R.id.iv_sort,R.mipmap.explorepage_no1)
                }
                2->{
                    helper.setImageResource(R.id.iv_sort,R.mipmap.explorepage_no2)
                }
                3->{
                    helper.setImageResource(R.id.iv_sort,R.mipmap.explorepage_no3)
                }
            }
        }else{
            helper.setGone(R.id.iv_sort,false)
        }

    }
}