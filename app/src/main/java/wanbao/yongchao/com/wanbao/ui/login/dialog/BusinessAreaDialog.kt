package wanbao.yongchao.com.wanbao.ui.login.dialog

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSONObject
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_business_area.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.ui.login.adapter.BusinessAreaAdapter
import wanbao.yongchao.com.wanbao.ui.login.adapter.BusinessCityAdapter
import wanbao.yongchao.com.wanbao.ui.login.adapter.BusinessProvinceAdapter
import wanbao.yongchao.com.wanbao.ui.login.utils.city.*
import wanbao.yongchao.com.wanbao.utils.utils.Click
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("ValidFragment")
class BusinessAreaDialog(var tv:TextView):BaseDialogFragment(),BusinessProvinceAdapter.CallBack,BusinessCityAdapter.CallBack,BusinessAreaAdapter.CallBack{
    override fun chooseArea(str: String) {
        if (isVisible) {
            tv_area.visibility = View.VISIBLE
            tv_area.text = str
            tv_title.visibility = View.GONE
            a = str

            tv.text = p + " " + c + " " + a
            dismiss()
        }
    }

    override fun chooseCity(str: String,list:ArrayList<CitiesBean.CityBean.AreaBean>) {
        if (isVisible) {
            tv_city.visibility = View.VISIBLE
            tv_city.text = str
            c = str

            Collections.sort(list, PinyinComparator2())
            areas = CityUtils.getArea(list)
            var adapter = BusinessAreaAdapter(areas)
            adapter.setCallBack(this)
            var m = LinearLayoutManager(activity!!)
            m.orientation = LinearLayout.VERTICAL
            recy_business_area.layoutManager = m
            recy_business_area.adapter = adapter
        }
    }

    override fun chooseProvince(str: String,list: ArrayList<CitiesBean.CityBean>) {
        if (isVisible) {
            layout_area.visibility = View.VISIBLE
            tv_province.visibility = View.VISIBLE
            tv_province.text = str
            p = str
            Collections.sort(list, PinyinComparator1())
            cities = CityUtils.getCity(list)
            var adapter = BusinessCityAdapter(cities)
            adapter.setCallBack(this)
            var m = LinearLayoutManager(activity!!)
            m.orientation = LinearLayout.VERTICAL
            recy_business_area.layoutManager = m
            recy_business_area.adapter = adapter
        }
    }

    private var provinces=ArrayList<ProvinceModel>()
    private var cities=ArrayList<CityModel>()
    private var areas=ArrayList<AreaModel>()
    private var p=""
    private var c=""
    private var a=""

    override fun setLayoutID(): Int =R.layout.dialog_business_area

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        getCities()
    }

    override fun clickListener() {

        Click.viewClick(dialogOver).subscribe {
            dismiss()
        }

        Click.viewClick(dialog_close).subscribe {
            dismiss()
        }

        Click.viewClick(tv_province).subscribe {
            layout_area.visibility= View.GONE
            tv_province.visibility=View.GONE
            var adapter=BusinessProvinceAdapter(provinces)
            adapter.setCallBack(this)
            var m=LinearLayoutManager(activity!!)
            m.orientation=LinearLayout.VERTICAL
            recy_business_area.layoutManager=m
            recy_business_area.adapter=adapter
        }

        Click.viewClick(tv_city).subscribe {
            tv_city.visibility=View.GONE
            var adapter=BusinessCityAdapter(cities)
            adapter.setCallBack(this)
            var m=LinearLayoutManager(activity!!)
            m.orientation=LinearLayout.VERTICAL
            recy_business_area.layoutManager=m
            recy_business_area.adapter=adapter
        }


    }

    fun getCities(){
        var inputStream: InputStream? = null
        try {
            inputStream = activity!!.assets.open("cities.json")
            val teachersData = CityUtils.convertStraemToString(inputStream)
            val listBean= JSONObject.parseArray(teachersData)
            var list=ArrayList<CitiesBean>()
            listBean.forEach {
                var city= Gson().fromJson(it.toString(), CitiesBean::class.java)
                list.add(city)
            }
            Collections.sort(list, PinyinComparator())

            provinces= CityUtils.getProvince(list)

            var adapter=BusinessProvinceAdapter(provinces)
            adapter.setCallBack(this)
            var m=LinearLayoutManager(activity!!)
            m.orientation=LinearLayout.VERTICAL
            recy_business_area.layoutManager=m
            recy_business_area.adapter=adapter


        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

class PinyinComparator : Comparator<CitiesBean> {

    override fun compare(o1: CitiesBean, o2: CitiesBean): Int {
        return o1.abb.substring(0,1)
                .compareTo(o2.abb.substring(0,1))

    }

}

class PinyinComparator1 : Comparator<CitiesBean.CityBean> {

    override fun compare(o1: CitiesBean.CityBean, o2: CitiesBean.CityBean): Int {
        return o1.abb.substring(0,1)
                .compareTo(o2.abb.substring(0,1))

    }

}

class PinyinComparator2 : Comparator<CitiesBean.CityBean.AreaBean> {

    override fun compare(o1: CitiesBean.CityBean.AreaBean, o2: CitiesBean.CityBean.AreaBean): Int {
        return o1.abb.substring(0,1)
                .compareTo(o2.abb.substring(0,1))

    }

}