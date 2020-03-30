package wanbao.yongchao.com.wanbao.ui.login.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.login.utils.city.CitiesBean
import wanbao.yongchao.com.wanbao.ui.login.utils.city.CityModel

class BusinessCityAdapter(list:MutableList<CityModel>): BaseQuickAdapter<CityModel, BaseViewHolder>(R.layout.item_business_area,list){

    private var callBack:CallBack?=null

    override fun convert(helper: BaseViewHolder, item: CityModel) {
        helper.setText(R.id.tv_key,item.key)
        var v=helper.getView<RecyclerView>(R.id.recy_item_business_area)
        var adapter=BusinessCityItemAdapter(item.list)
        var m= LinearLayoutManager(mContext)
        m.orientation= LinearLayout.VERTICAL
        v.layoutManager=m
        v.adapter=adapter

        adapter.setOnItemClickListener { Adapter, view, position ->
            //            ly.visibility=View.VISIBLE
//            tv.visibility=View.VISIBLE
//            tv.text=adapter.data[position].name
            callBack!!.chooseCity(adapter.data[position].name,adapter.data[position].area)
        }
    }

    public fun setCallBack(callBack: CallBack){
        this.callBack=callBack
    }

    interface CallBack{
        fun chooseCity(str:String,list:ArrayList<CitiesBean.CityBean.AreaBean>)
    }

}