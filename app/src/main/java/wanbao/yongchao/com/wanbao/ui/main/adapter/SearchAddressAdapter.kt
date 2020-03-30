package wanbao.yongchao.com.wanbao.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchAddressBean

class SearchAddressAdapter(list: MutableList<SearchAddressBean.DataBean>):BaseQuickAdapter<SearchAddressBean.DataBean,BaseViewHolder>(R.layout.item_search_address,list){
    override fun convert(helper: BaseViewHolder, item: SearchAddressBean.DataBean) {

        helper.setText(R.id.tv_name,item.cityMapName)
                .setText(R.id.tv_address,item.cityMapAddress)

    }
}