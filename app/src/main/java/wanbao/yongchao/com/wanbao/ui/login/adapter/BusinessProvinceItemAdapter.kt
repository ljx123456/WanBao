package wanbao.yongchao.com.wanbao.ui.login.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.login.utils.city.CitiesBean
import wanbao.yongchao.com.wanbao.ui.login.utils.city.ProvinceModel

class BusinessProvinceItemAdapter(list: MutableList<CitiesBean>):BaseQuickAdapter<CitiesBean,BaseViewHolder>(R.layout.item_business_area_item,list){
    override fun convert(helper: BaseViewHolder, item: CitiesBean) {
        helper.setText(R.id.tv_item_area,item.name)
    }

}