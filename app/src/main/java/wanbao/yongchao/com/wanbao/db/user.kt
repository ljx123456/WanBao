package wanbao.yongchao.com.wanbao.db

import wanbao.yongchao.com.wanbao.base.BaseContext.getContext

object user {

    private var flag=false
    private var isOut=false
    private var isDel=false
    private var isDelMine=false

    private var isChange=false
    fun setChange(b:Boolean){
        isChange=b
    }

    fun getChange():Boolean{
        return isChange
    }

    private var isWant=false
    private var isAddWant=-1

    fun setFlag(b:Boolean){
        flag=b
    }

    fun getFlag():Boolean{
        return flag
    }

    fun setOut(b:Boolean){
        isOut=b
    }

    fun getOut():Boolean{
        return isOut
    }

    var mCache = ACache.get(getContext()) // 初始化，一般放在基类里


    //account账号
    fun setAccount(phone:String){
        mCache.put("account",phone)
    }

    fun getAccount():String{
        if (mCache.getAsString("account")!=null){
            return mCache.getAsString("account")
        }else{
            return ""
        }
    }

    //定位维度
    fun setLocationLat(lat:String){
        mCache.put("locationLat",lat)
    }
    fun getLocationLat():String{
        if (mCache.getAsString("locationLat") != null) {
            return mCache.getAsString("locationLat")
        } else {
            return ""
        }
    }

    //定位精度
    fun setlocationLng(lng:String){
        mCache.put("locationLng",lng)
    }
    fun getLocationLng():String{
        if (mCache.getAsString("locationLng") != null) {
            return mCache.getAsString("locationLng")
        } else {
            return ""
        }
    }

    //商家locationId
    fun setLocationId(id:String){
        mCache.put("locationId",id)
    }

    fun getLocationId():String{
        if (mCache.getAsString("locationId")!=null){
            return mCache.getAsString("locationId")
        }else{
            return ""
        }
    }


    //商家精度
    fun setlng(lng: String) {
        mCache.put("lng", lng)
    }

    //商家维度
    fun setlat(lat: String) {
        mCache.put("lat", lat)
    }

    //当前商家精度
    fun getlng(): String {
        if (mCache.getAsString("lng") != null) {
            return mCache.getAsString("lng")
        } else {
            return ""
        }
    }

    //当前商家维度
    fun getlat(): String {
        if (mCache.getAsString("lat") != null) {
            return mCache.getAsString("lat")
        } else {
            return ""
        }
    }

    //当前商家详细地址
    fun getBusinessAddress(): String {
        if (mCache.getAsString("address") != null) {
            return mCache.getAsString("address")
        } else {
            return ""
        }
    }

    //当前商家详细地址
    fun setBusinessAddress(city: String) {
        mCache.put("address", city)
    }

    //设置微信code
    fun setWxCode(code: String) {
        mCache.put("WxCode", code)
    }
    fun getWxCode(): String {
        if (mCache.getAsString("WxCode") != null) {
            return mCache.getAsString("WxCode")
        } else {
            return ""
        }
    }


    //当前城市
    fun getCity(): String {
        if (mCache.getAsString("city") != null) {
            return mCache.getAsString("city")
        } else {
            return ""
        }
    }

    //城市
    fun setCity(city: String) {
        mCache.put("city", city)
    }

    //城市ID
    fun setCityID(cityId: String) {
        mCache.put("cityid", cityId)
    }

    fun getCityID(): String {
        if (mCache.getAsBinary("cityid") != null) {
            return mCache.getAsString("cityid")
        } else {
            return ""
        }

    }

    //是否删除消息
    fun getIsDel():Boolean{
        return isDel
    }

    fun setIsDel(flag:Boolean){
        this.isDel=flag
    }

    //是否添加想去
    fun getIsWant():Boolean{
        return isWant
    }

    fun setIsWant(flag:Boolean){
        this.isWant=flag
    }

    //想去ID
    fun getWantId(): String {
        if (mCache.getAsString("wantId") != null) {
            return mCache.getAsString("wantId")
        } else {
            return ""
        }
    }

    //想去ID
    fun setWantId(id: String) {
        mCache.put("wantId", id)
    }

    //是否添加想去
    fun getIsAddWant():Int{//0加，1减
        return isAddWant
    }

    fun setIsAddWant(flag:Int){
        this.isAddWant=flag
    }

    //删除动态id
    fun getDelId(): String {
        if (mCache.getAsString("delId") != null) {
            return mCache.getAsString("delId")
        } else {
            return "-1"
        }
    }

    //删除ID
    fun setDelId(id: String) {
        mCache.put("delId", id)
    }

    //是否删除动态
    fun getIsDelMine():Boolean{
        return isDelMine
    }

    fun setIsDelMine(flag:Boolean){
        this.isDelMine=flag
    }


}