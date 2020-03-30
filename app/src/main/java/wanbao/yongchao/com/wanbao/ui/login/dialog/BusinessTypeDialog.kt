package wanbao.yongchao.com.wanbao.ui.login.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_business_type.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.ui.login.adapter.BusinessTypeAdapter
import wanbao.yongchao.com.wanbao.ui.login.adapter.BusinessTypeDetailsAdapter
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessTypeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessTypeDetailsBean
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

@SuppressLint("ValidFragment")
class BusinessTypeDialog(context:Context,var edit:TextView):BaseDialogFragment(){

    var types=ArrayList<BusinessTypeBean>()
    var types1=ArrayList<BusinessTypeDetailsBean>()
    var types2=ArrayList<BusinessTypeDetailsBean>()
    var types3=ArrayList<BusinessTypeDetailsBean>()
    var types4=ArrayList<BusinessTypeDetailsBean>()
    var types5=ArrayList<BusinessTypeDetailsBean>()

    override fun setLayoutID(): Int =R.layout.dialog_business_type

    override fun isFullScreen(): Boolean =true

    override fun setLayoutData() {
        types1.add(BusinessTypeDetailsBean("美食",16))
        types1.add(BusinessTypeDetailsBean("奶茶店",17))
        types1.add(BusinessTypeDetailsBean("咖啡厅",18))
        types1.add(BusinessTypeDetailsBean("甜品",19))

        types2.add(BusinessTypeDetailsBean("酒店",20))
        types2.add(BusinessTypeDetailsBean("景点",21))
        types2.add(BusinessTypeDetailsBean("交通",22))
        types2.add(BusinessTypeDetailsBean("公园",23))

        types3.add(BusinessTypeDetailsBean("娱乐",24))
        types3.add(BusinessTypeDetailsBean("KTV",25))
        types3.add(BusinessTypeDetailsBean("酒吧",26))
        types3.add(BusinessTypeDetailsBean("网咖",27))

        types4.add(BusinessTypeDetailsBean("书店",28))
        types4.add(BusinessTypeDetailsBean("运动",29))
        types4.add(BusinessTypeDetailsBean("健身",30))
        types4.add(BusinessTypeDetailsBean("购物",31))

        types5.add(BusinessTypeDetailsBean("音乐馆",32))
        types5.add(BusinessTypeDetailsBean("博物馆",33))
        types5.add(BusinessTypeDetailsBean("书店",34))
        types5.add(BusinessTypeDetailsBean("电影院",35))

        types.add(BusinessTypeBean("美食",true,types1))
        types.add(BusinessTypeBean("旅游",false,types2))
        types.add(BusinessTypeBean("娱乐",false,types3))
        types.add(BusinessTypeBean("生活",false,types4))
        types.add(BusinessTypeBean("文化",false,types5))



        var typeAdapter=BusinessTypeAdapter(types)
        var typeManager=LinearLayoutManager(activity)
        typeManager.orientation=LinearLayout.VERTICAL
        recy_business_type.layoutManager=typeManager
        recy_business_type.adapter=typeAdapter

        var typeDetailsAdapter=BusinessTypeDetailsAdapter(types[0].list)
        var typeDetailsManager=LinearLayoutManager(activity)
        typeDetailsManager.orientation=LinearLayout.VERTICAL
        recy_business_type_details.layoutManager=typeDetailsManager
        recy_business_type_details.adapter=typeDetailsAdapter

        typeAdapter.setOnItemClickListener { adapter, view, position ->
            typeAdapter.data[position].isFlag=true
            for (i in typeAdapter.data.indices) {
                if (i != position) {
                    typeAdapter.data[i].isFlag = false
                }
            }
            typeDetailsAdapter.setNewData(typeAdapter.data[position].list)
            typeAdapter.notifyDataSetChanged()
        }

        typeDetailsAdapter.setOnItemClickListener { adapter, view, position ->
//            Toast.Tips(typeDetailsAdapter.data[position].name)
            edit.text=typeDetailsAdapter.data[position].name
            callBack!!.type(typeDetailsAdapter.data[position].name_id)
            dismiss()
        }
    }

    override fun clickListener() {
        Click.viewClick(dialogOver).subscribe {
            dismiss()
        }

        Click.viewClick(dialog_close).subscribe {
            dismiss()
        }
    }

    private var callBack:CallBack?=null

    public fun setCallBack(callBack: CallBack){
        this.callBack=callBack
    }

    interface CallBack{
        fun type(type_id:Int)
    }

}