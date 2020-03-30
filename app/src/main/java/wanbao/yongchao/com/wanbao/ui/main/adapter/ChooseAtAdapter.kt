package wanbao.yongchao.com.wanbao.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean

class ChooseAtAdapter(list: MutableList<FansBean.DataBean>):BaseQuickAdapter<FansBean.DataBean,BaseViewHolder>(R.layout.item_choose_at,list) {
    override fun convert(helper: BaseViewHolder, item: FansBean.DataBean) {
        helper.setText(R.id.tv_choose_at,"@"+item.nickname)
    }
}