package wanbao.yongchao.com.wanbao.ui.set.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class BlacklistAdapter(list: MutableList<FansBean.DataBean>): BaseQuickAdapter<FansBean.DataBean, BaseViewHolder>(R.layout.item_mine_fans,list){
    override fun convert(helper: BaseViewHolder, item: FansBean.DataBean) {
        ImageLoad.setUserHead(item.avatar,helper.getView(R.id.iv_header))

        helper.setText(R.id.tv_name,item.nickname)
//                .setText(R.id.tv_Id,"ID："+item.user_id)
        if (item.role==1){//用户
            if (item.signature!=null&&item.signature!=""){
                helper.setText(R.id.tv_content,item.signature)
            }else{
                helper.setText(R.id.tv_content,"暂无简介")
            }
        }else{
            helper.setText(R.id.tv_content,item.address)
        }

        if (!item.isFlag){
            helper.setBackgroundRes(R.id.tv_focus, R.drawable.tv_followed_bg)
                    .setText(R.id.tv_focus,"解除")
                    .setTextColor(R.id.tv_focus, Color.parseColor("#FCC725"))
        }else{
            helper.setBackgroundRes(R.id.tv_focus, R.drawable.tv_follow_bg)
                    .setText(R.id.tv_focus,"拉黑")
                    .setTextColor(R.id.tv_focus, Color.parseColor("#000000"))
        }

        helper.addOnClickListener(R.id.tv_focus)
    }
}