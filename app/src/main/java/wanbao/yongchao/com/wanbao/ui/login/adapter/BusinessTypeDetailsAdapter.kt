package wanbao.yongchao.com.wanbao.ui.login.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessTypeDetailsBean

class BusinessTypeDetailsAdapter(list: MutableList<BusinessTypeDetailsBean>):BaseQuickAdapter<BusinessTypeDetailsBean,BaseViewHolder>(R.layout.item_business_type_details,list){
    override fun convert(helper: BaseViewHolder, item: BusinessTypeDetailsBean) {
        helper.setText(R.id.item_business_type_details_name,item.name)
    }

}