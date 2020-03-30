package wanbao.yongchao.com.wanbao.ui.login.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessTypeBean

class BusinessTypeAdapter(list: MutableList<BusinessTypeBean>):BaseQuickAdapter<BusinessTypeBean,BaseViewHolder>(R.layout.item_business_type,list){
    override fun convert(helper: BaseViewHolder, item: BusinessTypeBean) {
        helper.setText(R.id.item_business_type_name,item.businessTypeName)

        if (item.isFlag){
            helper.setBackgroundColor(R.id.item_business_type_name,Color.parseColor("#323236"))
                    .setTextColor(R.id.item_business_type_name,Color.parseColor("#FCC725"))
        }else{
            helper.setBackgroundColor(R.id.item_business_type_name,Color.parseColor("#29292C"))
                    .setTextColor(R.id.item_business_type_name,Color.parseColor("#A6FFFFFF"))
        }

    }
}