package wanbao.yongchao.com.wanbao.ui.login.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.login.utils.city.CitiesBean

import wanbao.yongchao.com.wanbao.ui.login.utils.city.ProvinceModel

class BusinessProvinceAdapter(list:MutableList<ProvinceModel>):BaseQuickAdapter<ProvinceModel,BaseViewHolder>(R.layout.item_business_area,list){

    private var callBack:CallBack?=null

    override fun convert(helper: BaseViewHolder, item: ProvinceModel) {
        helper.setText(R.id.tv_key,item.key)
        var v=helper.getView<RecyclerView>(R.id.recy_item_business_area)
        var adapter=BusinessProvinceItemAdapter(item.list)
        var m=LinearLayoutManager(mContext)
        m.orientation=LinearLayout.VERTICAL
        v.layoutManager=m
        v.adapter=adapter

        adapter.setOnItemClickListener { Adapter, view, position ->
//            ly.visibility=View.VISIBLE
//            tv.visibility=View.VISIBLE
//            tv.text=adapter.data[position].name
            callBack!!.chooseProvince(adapter.data[position].name,adapter.data[position].city)
        }
    }

    public fun setCallBack(callBack: CallBack){
        this.callBack=callBack
    }

    interface CallBack{
        fun chooseProvince(str:String,list:ArrayList<CitiesBean.CityBean>)
    }

}