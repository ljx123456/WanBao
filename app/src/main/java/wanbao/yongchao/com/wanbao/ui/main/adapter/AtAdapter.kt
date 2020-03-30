package wanbao.yongchao.com.wanbao.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AtBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class AtAdapter(list: MutableList<FansBean.DataBean>):BaseQuickAdapter<FansBean.DataBean,BaseViewHolder>(R.layout.item_at,list){
    override fun convert(helper: BaseViewHolder, item: FansBean.DataBean) {
        if (item.avatar!=null&&item.avatar!="") {
            ImageLoad.setUserHead(item.avatar, helper.getView(R.id.iv_header))
        }
        helper.setText(R.id.tv_name,item.nickname)

        if (item.isFlag){
            helper.setImageResource(R.id.iv_choose,R.mipmap.actionsheets_button_checklist)
        }else{
            helper.setImageResource(R.id.iv_choose,R.drawable.drawable_choose_bg)
        }
    }

}