package wanbao.yongchao.com.wanbao.ui.login.activity

import android.os.Bundle

import kotlinx.android.synthetic.main.activity_business_register_map.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.utils.utils.Click

import android.graphics.BitmapFactory
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.*
import com.amap.api.maps.model.Marker
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.geocoder.*
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.adapter.BusinessMapSearchAdapter
import wanbao.yongchao.com.wanbao.utils.utils.Toast


class RegisterBusinessMapActivity:BaseActivity(), GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {
    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

    override fun onPoiSearched(p0: PoiResult, p1: Int) {
        Log.e("测试","搜索")
        if (p1==1000){
            Log.e("测试","搜索结果"+p0.pois)
            var adapter=BusinessMapSearchAdapter(p0.pois)
            var m=LinearLayoutManager(this)
            m.orientation=LinearLayout.VERTICAL
            recy_map_search.layoutManager=m
            recy_map_search.adapter=adapter
            recy_map_search.visibility=View.VISIBLE
            adapter.setOnItemClickListener { mAdapter, view, position ->
                recy_map_search.visibility=View.GONE
                isChoose=true
                place=adapter.data[position].title
                businessAdd=adapter.data[position].snippet
                edit_map_search.setText("")
                edit_map_search.clearFocus()
                var address=adapter.data[position].provinceName+adapter.data[position].cityName+adapter.data[position].snippet
                val query= GeocodeQuery(address, adapter.data[position].cityName)
                geocoderSearch.getFromLocationNameAsyn(query)
                user.setLocationId(adapter.data[position].poiId.toString())
                Log.e("测试id",adapter.data[position].poiId.toString())
            }
        }

    }

    override fun onRegeocodeSearched(p0: RegeocodeResult, p1: Int) {
        if (p1==1000){
            tv?.text=p0.regeocodeAddress.pois[0].title

            //商家坐标
            businessLat=p0.regeocodeAddress.pois[0].latLonPoint.latitude
            businessLng=p0.regeocodeAddress.pois[0].latLonPoint.longitude
            businessAdd=p0.regeocodeAddress.pois[0].snippet
            user.setLocationId(p0.regeocodeAddress.pois[0].poiId)
        }
    }

    override fun onGeocodeSearched(p0: GeocodeResult, p1: Int) {
        Log.e("测试",p1.toString())
        if (p1==1000){
            Log.e("测试","活的坐标")
            p0.geocodeAddressList[0].latLonPoint

            //商家坐标
            businessLat=p0.geocodeAddressList[0].latLonPoint.latitude
            businessLng=p0.geocodeAddressList[0].latLonPoint.longitude
//            user.setLocationId("")
            if (isMove) {
//                if (!isChoose) {
                    businessAdd = p0.geocodeAddressList[0].formatAddress
//                }
            }else{
                if (!isChoose) {
                    if (intent.getStringExtra("address")!=null){
                        businessAdd=startAdd
                    }else {
                        businessAdd = ""
                    }
                }
            }

            var markerOption = MarkerOptions()
//            markerOption.position(Constants.XIAN)
            var latLng=LatLng(p0.geocodeAddressList[0].latLonPoint.latitude,p0.geocodeAddressList[0].latLonPoint.longitude)
            markerOption.position(latLng)
            if (address=="") {
                markerOption.title(area)
            }else{
                markerOption.title(address)
            }
            markerOption.visible(true)
            markerOption.draggable(true)//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(resources, R.mipmap.signin_map_needle)))
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//            markerOption.setFlat(true)//设置marker平贴地图效果
            if (marker!=null){
                marker!!.remove()
            }
            marker=aMap!!.addMarker(markerOption)
           aMap!!.setInfoWindowAdapter(object :AMap.InfoWindowAdapter{
               /**
                * 监听自定义infowindow窗口的infocontents事件回调
                */
               override fun getInfoContents(marker: Marker): View? {
                   return null
                   //示例没有采用该方法。
               }

               var infoWindow: View? = null

               /**
                * 监听自定义infowindow窗口的infowindow事件回调
                */
               override fun getInfoWindow(marker: Marker): View? {
                   if (infoWindow == null) {
                       infoWindow = LayoutInflater.from(this@RegisterBusinessMapActivity).inflate(
                               R.layout.custom_info_window, null)
                   }
                   render(marker, infoWindow)
                   return infoWindow
                   //加载custom_info_window.xml布局文件作为InfoWindow的样式
                   //该布局可在官方Demo布局文件夹下找到
               }

               /**
                * 自定义infowinfow窗口
                */
               fun render(marker: Marker, view: View?) {
                   //如果想修改自定义Infow中内容，请通过view找到它并修改
                   tv=view?.findViewById<TextView>(R.id.snippet)
                   if (isChoose){
                       tv?.text=place
                   }else if (!isMove) {
                       if (startAdd!="") {
                           tv?.text = startAdd
                       }else{
                           tv?.text=area
                       }
                   }else{
                       tv?.text = p0.geocodeAddressList[0].formatAddress
                   }
               }
           })

//            aMap!!. moveCamera(CameraUpdateFactory.changeLatLng(latLng))
            marker!!.showInfoWindow()

            var markerDragListener=object :AMap.OnMarkerDragListener{
                override fun onMarkerDragEnd(p0: Marker) {
                    lat=p0.position.latitude
                    lng=p0.position.longitude
                    var LatLonPoint=LatLonPoint(p0.position.latitude,p0.position.longitude)
                    var query = RegeocodeQuery(LatLonPoint, 50.toFloat(),GeocodeSearch.AMAP)
                    geocoderSearch.getFromLocationAsyn(query)
                }

                override fun onMarkerDragStart(p0: Marker) {
                    isMove=true
                }

                override fun onMarkerDrag(p0: Marker) {

                }
            }
            // 绑定marker拖拽事件
            aMap!!.setOnMarkerDragListener(markerDragListener);

            //改变可视区域为指定位置
            //CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）
            var cameraUpdate= CameraUpdateFactory.newCameraPosition(CameraPosition(latLng,14.toFloat(),0.toFloat(),0.toFloat()))
            aMap!!.moveCamera(cameraUpdate)
        }
    }

    private lateinit var  mMapView : MapView
    private lateinit var mUiSettings:UiSettings
    private var aMap: AMap?=null
    private var province=""
    private var area=""
    private var city=""
    private var address=""
    private var tv:TextView?=null
    private lateinit var  geocoderSearch:GeocodeSearch
    private var lat=0.0
    private var lng=0.0
    private var marker:Marker?=null
    private var isChoose=false
    private var place=""
    private var isMove=false
    private var startAdd=""

    private var businessLat=0.0
    private var businessLng=0.0
    private var businessAdd=""

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        //获取地图控件引用
        mMapView =  findViewById(R.id.map)
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(bundle)
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap()
            mUiSettings = aMap!!.getUiSettings()
        }
        mUiSettings.isScaleControlsEnabled=true
         geocoderSearch= GeocodeSearch(this)
        geocoderSearch.setOnGeocodeSearchListener(this)

        city=intent.getStringExtra("city")
        province=intent.getStringExtra("province")
        area=intent.getStringExtra("area")
        if (intent.getStringExtra("address")!=null) {
            address = province+city+area+intent.getStringExtra("address")
            startAdd=intent.getStringExtra("address")
        }else{
            address = province+city+area
            startAdd=""
        }

        val query= GeocodeQuery(address, city)

        geocoderSearch.getFromLocationNameAsyn(query)

    }

    override fun openEventBus(): Boolean =false

    override fun getActivityLayout(): Int = R.layout.activity_business_register_map

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

    }

    override fun clickListener() {
        Click.viewClick(map_back).subscribe {
            finish()
        }

        edit_map_search.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s.toString().length>0){
                    var query = PoiSearch.Query(s.toString(), "", city)
                    query.pageSize=20
                    var poiSearch = PoiSearch(this@RegisterBusinessMapActivity, query)
                    poiSearch.setOnPoiSearchListener(this@RegisterBusinessMapActivity)
                    poiSearch.searchPOIAsyn()
//                    query.pageNum
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        Click.viewClick(btn_map).subscribe {
            if (businessLat!=0.0&&businessLng!=0.0&&businessAdd!="") {
                user.setlat(businessLat.toString())
                user.setlng(businessLng.toString())
                user.setBusinessAddress(businessAdd)
                Log.e("测试",businessLat.toString())
                Log.e("测试",businessAdd)
                finish()
            }else{
                Log.e("测试",businessLat.toString())
                Log.e("测试",businessAdd)
                Toast.Tips("请选择坐标")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState)
    }
}