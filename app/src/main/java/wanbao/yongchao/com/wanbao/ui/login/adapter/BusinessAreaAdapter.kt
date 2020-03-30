package wanbao.yongchao.com.wanbao.ui.login.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.login.utils.city.AreaModel

class BusinessAreaAdapter(list:MutableList<AreaModel>): BaseQuickAdapter<AreaModel, BaseViewHolder>(R.layout.item_business_area,list){

    private var callBack:CallBack?=null

    override fun convert(helper: BaseViewHolder, item: AreaModel) {
        helper.setText(R.id.tv_key,item.key)
        var v=helper.getView<RecyclerView>(R.id.recy_item_business_area)
        var adapter=BusinessAreaItemAdapter(item.list)
        var m= LinearLayoutManager(mContext)
        m.orientation= LinearLayout.VERTICAL
        v.layoutManager=m
        v.adapter=adapter

        adapter.setOnItemClickListener { Adapter, view, position ->
            //            ly.visibility=View.VISIBLE
//            tv.visibility=View.VISIBLE
//            tv.text=adapter.data[position].name
            callBack!!.chooseArea(adapter.data[position].name)
        }
    }

    public fun setCallBack(callBack: CallBack){
        this.callBack=callBack
    }

    interface CallBack{
        fun chooseArea(str:String)
    }

}