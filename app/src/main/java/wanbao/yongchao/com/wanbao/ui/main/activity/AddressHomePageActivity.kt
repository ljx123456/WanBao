package wanbao.yongchao.com.wanbao.ui.main.activity

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import kotlinx.android.synthetic.main.activity_address_homepage.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo
import wanbao.yongchao.com.wanbao.ui.main.dialog.SelectMapDialog
import wanbao.yongchao.com.wanbao.ui.main.fragment.TopicFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddressHomePageBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.AddressHomePageBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.AddressHomePagePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AddressHomePageView
import wanbao.yongchao.com.wanbao.utils.dialog.ShareDialog
import wanbao.yongchao.com.wanbao.utils.http.BaseUrl
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.share.ShareUtil
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.net.URISyntaxException

class AddressHomePageActivity: BaseActivity(),PoiSearch.OnPoiSearchListener,SelectMapDialog.SelectMapDialogFace,ShareDialog.Share,AddressHomePageView, UserPermissions.MemoryReadPermissionsFace, LocationUtils.Location{
    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        LocationUtils(this).getLocation()
    }

    override fun requestPermissionsFaceError() {
        presenter.getAddressHomePage(AddressHomePageBody(intent.getStringExtra("id")))
    }

    override fun getLocationSuccess(city: String) {
        presenter.getAddressHomePage(AddressHomePageBody(intent.getStringExtra("id"),user.getLocationLat(),user.getLocationLng() ))
    }

    override fun getAddressHomePageRequest(data: AddressHomePageBean) {
        poiSearch.searchPOIIdAsyn(data.data.locationId)
        tv_name.text=data.data.cityMapName
        tv_title.text=data.data.cityMapName
        tv_address.text=data.data.cityMapAddress
        if (data.data.km!=null&&data.data.km!=""){
            tv_distance.text=data.data.km
        }else{
            tv_distance.text=""
        }


        if (fragment!=null){
            (fragment!! as TopicFragment).setRefresh()
        }else {
            initFragment(data.data.locationId)
        }
    }

    override fun getAddressHomePageError() {

    }

    override fun setShareWX() {
        ShareUtil.shareWxCircle(this,"我去过这里，这儿很不错哦","我去过这里，这儿很不错哦", BaseUrl.HOST_URL+"share/page?id="+intent.getStringExtra("id")+"&type=5")
    }

    override fun setSharePYQ() {
//        Toast.Tips("分享到朋友圈")
        ShareUtil.shareWx(this,"我去过这里，这儿很不错哦","我去过这里，这儿很不错哦",BaseUrl.HOST_URL+"share/page?id="+intent.getStringExtra("id")+"&type=5")
    }

    override fun setShareQQ() {
//        Toast.Tips("分享到QQ")
        ShareUtil.QQShare(this,"晚豹App","我去过这里，这儿很不错哦",BaseUrl.HOST_URL+"share/page?id="+intent.getStringExtra("id")+"&type=5")
    }

    override fun gaodeBtn() {
        if (isPackageInstalled("com.autonavi.minimap")){
            var intent= Intent()
            intent.action= Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            var uri= Uri.parse("amapuri://route/plan/?did=BGVIS2&dlat=" + lat
                    + "&dlon=" + lng + "&dname=${name}&dev=0&t=0")
            intent.data=uri
            startActivity(intent)
        }else{
            Toast.Tips("请安装高德地图")
        }
    }

    override fun baiduBtn() {
        if (isPackageInstalled("com.baidu.BaiduMap")){
            try {
//                name:对外经贸大学|latlng:39.98871,116.43234
                var pareUrl = "baidumap://map/direction?region=" +
                        "&destination=latlng:" + lat + "," + lng +"|name:"+name+ "&coord_type=bd09ll&src=andr.bixinyule.ServeBixin"
                var intent = Intent.getIntent(pareUrl)
                startActivity(intent)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }else{
            Toast.Tips("请安装百度地图")
        }
    }

    override fun tencentBtn() {

        if (isPackageInstalled("com.tencent.map")){
            var intent= Intent()
            intent.action= Intent.ACTION_VIEW
            var uri= Uri.parse("qqmap://map/routeplan?type=drive&from=我的位置&fromcoord=0,0"
                    + "&to=" + name
                    + "&tocoord=" + lat + "," + lng
                    + "&policy=1&referer=myapp")
            intent.data=uri
            startActivity(intent)
        }else{
            Toast.Tips("请安装腾讯地图")
        }

    }

    override fun onPoiItemSearched(p0: PoiItem, p1: Int) {
        if (p1==1000){
            Log.e("测试","搜到了")
            var markerOption = MarkerOptions()
//            markerOption.position(Constants.XIAN)
            lat=p0.latLonPoint.latitude.toString()
            lng=p0.latLonPoint.longitude.toString()
            name=p0.title
            var latLng= LatLng(p0.latLonPoint.latitude,p0.latLonPoint.longitude)
            Log.e("测试",p0.latLonPoint.latitude.toString())
            markerOption.position(latLng)
            markerOption.visible(true)
            markerOption.draggable(false)//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(resources, R.mipmap.locationpage_icon_location)))
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//            markerOption.setFlat(true)//设置marker平贴地图效果
            var marker=aMap!!.addMarker(markerOption)
            //改变可视区域为指定位置
            //CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）
            var cameraUpdate= CameraUpdateFactory.newCameraPosition(CameraPosition(latLng,15.toFloat(),0.toFloat(),0.toFloat()))
            aMap!!.moveCamera(cameraUpdate)
        }
    }

    override fun onPoiSearched(p0: PoiResult, p1: Int) {

    }


    var fragment: BaseFragment? = null
    private lateinit var  mMapView : MapView
    private lateinit var mUiSettings: UiSettings
    private var aMap: AMap?=null
    private lateinit var poiSearch: PoiSearch
    private lateinit var dialog: SelectMapDialog
    private var lat=""
    private var lng=""
    private var name=""
    private lateinit var shareDialog: ShareDialog

    private val presenter by lazy { AddressHomePagePresenter(this,this,this) }

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
        mUiSettings.isZoomControlsEnabled=false
        mUiSettings.setAllGesturesEnabled(false)
        poiSearch= PoiSearch(this,null)
        poiSearch.setOnPoiSearchListener(this)

    }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_address_homepage

    override fun setActivityTitle() {

//        var h= SystemUtils.px2dip(this, SystemUtils.getScreenHeight(this).toFloat())-88.0f
//
//        var mheight= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,h,resources.displayMetrics)
//        var params = layout.layoutParams
//        params.height=mheight
//        Log.e("测试",mheight.toString())
//        val params = vp_homePage.getLayoutParams()
//        params.height=mheight.toInt()
//        vp_homePage.setLayoutParams(params)

    }

    override fun initActivityData() {


//        var titles = ArrayList<String>()
//        titles.add("动态")
//        titles.add("打卡")
//
//
//        titles.forEach {
//            fragments.add(TopicFragment(it))
//        }

        if (user.getLocationLat()!=""&&user.getLocationLng()!="") {
            presenter.getAddressHomePage(AddressHomePageBody(intent.getStringExtra("id"),user.getLocationLat(),user.getLocationLng() ))
        }else{
            UserPermissions.userLocation(mContext, this)
        }



        scroll.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView, scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {

                if (scrollY == 0) {
                    //顶部
//                    manage.setmCanVerticalScroll(false)
                    iv_back.visibility= View.VISIBLE
                    iv_share.visibility= View.VISIBLE
                }else{
                    iv_back.visibility= View.GONE
                    iv_share.visibility= View.GONE
                }
                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
                var distanceScrollY = layout_top.height - layout_title.height+ SystemUtils.dip2px(this@AddressHomePageActivity,20f)
                if (scrollY >= distanceScrollY) {
                    layout_choose.visibility = View.VISIBLE
//                    ll_tab.visibility = View.INVISIBLE
//                    title_divider.visibility = View.VISIBLE
                } else {
                    layout_choose.visibility = View.INVISIBLE
//                    ll_tab.visibility = View.VISIBLE
//                    title_divider.visibility = View.GONE
                }

                var  contentView = scroll.getChildAt(0)
                if (contentView != null && contentView.getMeasuredHeight() == (scroll.getScrollY() + scroll.getHeight())) {
                    //底部
//                    manage.setmCanVerticalScroll(true)
                    Log.e("测试","滑动到底部了")
                    (fragment as TopicFragment).setLoadMore()
                }
                //设置标题栏渐变
//                if (scrollY <= 0) {
//                    //初始位置：未滑动时，设置标题背景透明
//                    layout_title.setBackgroundColor(Color.TRANSPARENT)
////                    tv_title_text.setTextColor(Color.WHITE)
//                } else if (scrollY > 0 && scrollY <= distanceScrollY) {
//                    var scale: Float = (scrollY.toFloat()) / distanceScrollY
//                    var alpha: Float = 255 * scale
//                    layout_title.setBackgroundColor(Color.argb(alpha.toInt(), 255, 255, 255))
////                    tv_title_text.setTextColor(Color.argb(alpha.toInt(), 0, 0, 0))
//                } else {
//                    layout_title.setBackgroundColor(Color.argb(255, 255, 255, 255))
////                    tv_title_text.setTextColor(Color.argb(255, 0, 0, 0))
//                }
                var alpha=0f
                var h= SystemUtils.dip2px(this@AddressHomePageActivity,120f)
                if (scrollY>=h){
                    alpha=1f
//                    manage.setmCanVerticalScroll(true)
                }else{
                    alpha=scrollY/h
//                    manage.setmCanVerticalScroll(false)
                }

                layout_title.alpha=alpha
                layout_title.visibility= View.VISIBLE
//
            }

        })

        dialog= SelectMapDialog(this)
        dialog.setDialogFace(this)

        shareDialog= ShareDialog(this)

    }

    override fun clickListener() {

        Click.viewClick(iv_back).subscribe {
            finish()
        }

        Click.viewClick(iv_title_back).subscribe {
            finish()
        }

        Click.viewClick(view_map).subscribe {
            dialog.showDialog()
        }

        Click.viewClick(iv_navi).subscribe {
            dialog.showDialog()
        }

        Click.viewClick(iv_share).subscribe {
            shareDialog.show(supportFragmentManager,"")
        }

        Click.viewClick(iv_title_share).subscribe {
            shareDialog.show(supportFragmentManager,"")
        }



    }


    //设置初始显示的Fragment
    private fun initFragment(id:String) {
        fragment = TopicFragment("打卡",id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container, fragment!!)
        transaction.commitAllowingStateLoss()
    }

    //设置数量
    public fun setNum(num:Int){
        if (num>0) {
            tv_num.visibility=View.VISIBLE
            tv_num.text ="累计动态："+num.toString()+"条"
        }else{
            tv_num.visibility=View.INVISIBLE
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

    //判断包名是否存在
    fun isPackageInstalled(packageName: String): Boolean {
        return File("/data/data/$packageName").exists()
    }

}