package wanbao.yongchao.com.wanbao.ui.mine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthTypeBean

class AuthBloggerTypeAdapter(list: MutableList<AuthTypeBean.DataBean>):BaseQuickAdapter<AuthTypeBean.DataBean,BaseViewHolder>(R.layout.item_auth_type,list) {
    override fun convert(helper: BaseViewHolder, item: AuthTypeBean.DataBean) {
        helper.setText(R.id.tv_type,item.description)
    }
}