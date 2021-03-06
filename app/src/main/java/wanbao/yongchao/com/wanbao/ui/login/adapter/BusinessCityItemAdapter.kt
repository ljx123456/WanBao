package wanbao.yongchao.com.wanbao.ui.login.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.login.utils.city.CitiesBean

class BusinessCityItemAdapter(list: MutableList<CitiesBean.CityBean>): BaseQuickAdapter<CitiesBean.CityBean, BaseViewHolder>(R.layout.item_business_area_item,list){
    override fun convert(helper: BaseViewHolder, item: CitiesBean.CityBean) {
        helper.setText(R.id.tv_item_area,item.name)
    }

}