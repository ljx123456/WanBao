package wanbao.yongchao.com.wanbao.ui.login.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.AreaBean

class ProvinceItemAdapter(list: MutableList<AreaBean.DataBean.CitiesBean>): BaseQuickAdapter<AreaBean.DataBean.CitiesBean, BaseViewHolder>(R.layout.item_business_area_item,list){
    override fun convert(helper: BaseViewHolder, item: AreaBean.DataBean.CitiesBean) {
        helper.setText(R.id.tv_item_area,item.name)
        if (item.flag){
            helper.setTextColor(R.id.tv_item_area,Color.parseColor("#d9FCC725"))
                    .setVisible(R.id.iv_choose,true)
        }else{
            helper.setTextColor(R.id.tv_item_area,Color.parseColor("#d9ffffff"))
                    .setVisible(R.id.iv_choose,false)
        }
    }

}