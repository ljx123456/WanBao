package wanbao.yongchao.com.wanbao.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad

class SearchUserAdapter(list: MutableList<FansBean.DataBean>):BaseQuickAdapter<FansBean.DataBean,BaseViewHolder>(R.layout.item_search_user,list){
    override fun convert(helper: BaseViewHolder, item: FansBean.DataBean) {
        ImageLoad.setUserHead(item.avatar,helper.getView(R.id.iv_header))

        helper.setText(R.id.tv_name,item.nickname)
                .setText(R.id.tv_Id,"ID："+item.id)

        if (item.role==1){//用户
            if (item.signature!=null&&item.signature!=""){
                helper.setText(R.id.tv_content,item.signature)
            }else{
                helper.setText(R.id.tv_content,"暂无简介")
            }
        }else{
            helper.setText(R.id.tv_content,item.address)
        }
    }
}