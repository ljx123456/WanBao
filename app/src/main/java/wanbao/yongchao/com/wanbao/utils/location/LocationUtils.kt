package wanbao.yongchao.com.wanbao.utils.location


import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext
import wanbao.yongchao.com.wanbao.db.user
import java.text.SimpleDateFormat
import java.util.*


class LocationUtils (val location:Location): AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener{
    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {

    }

    override fun onRegeocodeSearched(p0: RegeocodeResult, p1: Int) {
        if (p1==1000){
            var add=p0.regeocodeAddress
           if (add.city!=null&&add.city!=""){
//               location.getLocationSuccess(add.city.replace("市", ""))
               location.getLocationSuccess(add.city)
           }else{
//               location.getLocationSuccess(add.province.replace("市", ""))
               location.getLocationSuccess(add.city)
           }
//            Toast.Tips("国家："+add.country+"省份:"+add.province+"城市："+add.city)
//            location.getLocationSuccess(add.pois[0].snippet)
        }
    }

    var mlocationClient = AMapLocationClient(getContext())
    var mLocationOption = AMapLocationClientOption()
    var geocoderSearch = GeocodeSearch(getContext())

    fun getLocation() {
        mlocationClient.setLocationListener(this)
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
        mLocationOption.setInterval(2000)
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true)
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        mlocationClient.setLocationOption(mLocationOption)
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false)
        mlocationClient.startLocation()

        geocoderSearch.setOnGeocodeSearchListener(this)
    }

    fun getLocationOnce() {
        mlocationClient.setLocationListener(this)
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
        mLocationOption.setOnceLocation(true);
        mLocationOption.setInterval(2000)
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true)
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        mlocationClient.setLocationOption(mLocationOption)
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false)
        mlocationClient.startLocation()

        geocoderSearch.setOnGeocodeSearchListener(this)
    }

    override fun onLocationChanged(amapLocation: AMapLocation) {
        if (amapLocation != null && amapLocation.errorCode == 0) {

//            amapLocation.locationType
//            amapLocation.latitude//获取纬度
//            amapLocation.longitude//获取经度
//            amapLocation.accuracy//获取精度信息
//            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            val date = Date(amapLocation.time)
//            df.format(date)//定位时间
            user.setlocationLng("${amapLocation.longitude}")
            user.setLocationLat("${amapLocation.latitude}")
            val city = amapLocation.city
//            val city =amapLocation.poiName
            if (city!=null&&city!="") {
//            Toast.Tips("国家："+amapLocation.country+"省份:"+amapLocation.province+"城市："+city+"地址："+amapLocation.address)
//            setCity(city.replace("市", ""))
//                location.getLocationSuccess(city.replace("市", ""))
                location.getLocationSuccess(city)
//                location.getLocationSuccess(city)
            }else if(amapLocation.province!=null&&amapLocation.province!=""){
//                location.getLocationSuccess(amapLocation.province.replace("市",""))
                location.getLocationSuccess(amapLocation.province)
//                Toast.Tips("国家："+amapLocation.country+"省份:"+amapLocation.province+"城市："+city+"地址："+amapLocation.address)
//                location.getLocationSuccess(city)
            }else{
                var query= RegeocodeQuery(LatLonPoint(amapLocation.latitude,amapLocation.longitude),100.toFloat(),GeocodeSearch.AMAP)
                geocoderSearch.getFromLocationAsyn(query)
            }
            LogUtils.a("定位" + amapLocation.cityCode + Gson().toJson(amapLocation))
            mlocationClient.stopLocation()
        }
    }

    interface Location{
        fun getLocationSuccess(city:String)
    }
}
