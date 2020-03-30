package wanbao.yongchao.com.wanbao.ui.release.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ClockAddressBean
import java.math.BigDecimal

class ReleaseAddressAdapter(list: MutableList<ClockAddressBean.DataBean>):BaseQuickAdapter<ClockAddressBean.DataBean,BaseViewHolder>(R.layout.item_release_clock_address,list) {
    override fun convert(helper: BaseViewHolder, item: ClockAddressBean.DataBean) {
        helper.setText(R.id.tv_name,item.name)
                .setText(R.id.tv_address,item.address)

        if (item.clockInNum.toInt()>=200){
            helper.setVisible(R.id.tv_num,true)
            if (item.clockInNum.toInt()<10000){
                helper.setText(R.id.tv_num,"累计打卡：${item.clockInNum}次")
            }else{
                var num=BigDecimal(item.clockInNum).divide(BigDecimal(10000)).setScale(1,BigDecimal.ROUND_DOWN)
                helper.setText(R.id.tv_num,"累计打卡：${num}w次")
            }
        }else {
            helper.setVisible(R.id.tv_num,false)
        }

    }
}