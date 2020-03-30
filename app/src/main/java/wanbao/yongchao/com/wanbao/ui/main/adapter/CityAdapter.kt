package wanbao.yongchao.com.wanbao.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CityBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class CityAdapter(list: MutableList<CityBean.DataBean>):BaseQuickAdapter<CityBean.DataBean,BaseViewHolder>(R.layout.item_city,list) {
    override fun convert(helper: BaseViewHolder, item: CityBean.DataBean) {
        ImageLoad.setImage(item.image,helper.getView(R.id.iv_city))
    }
}