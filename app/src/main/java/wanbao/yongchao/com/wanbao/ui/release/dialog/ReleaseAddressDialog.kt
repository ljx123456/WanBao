package wanbao.yongchao.com.wanbao.ui.release.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.dialog_release_address.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.ui.release.adapter.ReleaseAddressAdapter
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.ClockAddressBean
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.ClockAddressBody
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.SearchClockBody
import wanbao.yongchao.com.wanbao.ui.release.mvp.presenter.ReleaseAddressPresenter
import wanbao.yongchao.com.wanbao.ui.release.mvp.view.ReleaseAddressView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView
import java.math.BigDecimal

@SuppressLint("ValidFragment")
class ReleaseAddressDialog(val lat:String, val lng:String,val address:Address):BaseDialogFragment(),ReleaseAddressView {
    override fun getReleaseAddressRequest(data: ClockAddressBean) {
        if (this.isVisible) {
            if (adapter != null) {
                if (pageIndex == 1) {
                    adapter!!.setNewData(data.data)
                } else {
                    adapter!!.addData(data.data)
                }
            } else {
                adapter = ReleaseAddressAdapter(data.data)
                var manager = LinearLayoutManager(activity!!)
                manager.orientation = LinearLayout.VERTICAL
                recy_search.layoutManager = manager
                recy_search.adapter = adapter
            }

            adapter!!.setLoadMoreView(CustomLoadMoreView())
            if (data.data.size < pageSize) {
                adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
            } else {
                adapter!!.loadMoreComplete()
            }

            adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
                override fun onLoadMoreRequested() {
                    pageIndex = pageIndex + 1
                    presenter.getLocationAddress(ClockAddressBody(lng, lat, pageIndex.toString(), pageSize.toString()))
                }
            }, recy_search)

            adapter!!.setOnItemClickListener { mAdapter, view, position ->
                address.setAddress("${adapter!!.data[position].cityName}·${adapter!!.data[position].name}", adapter!!.data[position].id)
                pageIndex = 1
                search = ""
                edit_search.setText("")
                dismiss()
            }
        }
    }

    override fun getSearchAddressRequest(data: ClockAddressBean) {
        if (isVisible) {
            if (adapter != null) {
                if (pageIndex == 1) {
                    adapter!!.setNewData(data.data)
                } else {
                    adapter!!.addData(data.data)
                }
            } else {
                adapter = ReleaseAddressAdapter(data.data)
                var manager = LinearLayoutManager(activity!!)
                manager.orientation = LinearLayout.VERTICAL
                recy_search.layoutManager = manager
                recy_search.adapter = adapter
            }

            adapter!!.setLoadMoreView(CustomLoadMoreView())
            if (data.data.size < pageSize) {
                adapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
            } else {
                adapter!!.loadMoreComplete()
            }

            adapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
                override fun onLoadMoreRequested() {
                    pageIndex = pageIndex + 1
                    presenter.getLocationAddress(ClockAddressBody(lng, lat, pageIndex.toString(), pageSize.toString()))
                }
            }, recy_search)

            adapter!!.setOnItemClickListener { mAdapter, view, position ->
                address.setAddress("${adapter!!.data[position].cityName}·${adapter!!.data[position].name}", adapter!!.data[position].id)
                pageIndex = 1
                search = ""
                edit_search.setText("")
                dismiss()
            }
        }

    }

    override fun setLayoutID(): Int = R.layout.dialog_release_address

    override fun isFullScreen(): Boolean = true

//    private lateinit var  mMapView : MapView
//    private lateinit var mUiSettings: UiSettings
//    private var aMap: AMap?=null
//    private var marker:Marker?=null
    private val presenter by lazy { ReleaseAddressPresenter(this,this,activity!!) }
    private var pageIndex=1
    private var pageSize=10
    private var adapter:ReleaseAddressAdapter?=null
    private var search=""

//    override fun onCreate(bundle: Bundle?) {
//        super.onCreate(bundle)
//        //获取地图控件引用
//        mMapView =  view!!.findViewById<MapView>(R.id.map)
//        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
//        mMapView.onCreate(bundle)
//        //初始化地图控制器对象
//        if (aMap == null) {
//            aMap = mMapView.getMap()
//        }
//        mUiSettings = aMap!!.getUiSettings()
//        mUiSettings.isZoomControlsEnabled=false
//        mUiSettings.setAllGesturesEnabled(false)
////        LatLng()
//        var markerOption = MarkerOptions()
////            markerOption.position(Constants.XIAN)
//        var latLng=LatLng(BigDecimal(lat).toDouble(),BigDecimal(lng).toDouble())
//        markerOption.position(latLng)
//        if (name=="") {
//            markerOption.title(name)
//        }
//        markerOption.visible(true)
//        markerOption.draggable(false)//设置Marker可拖动
//        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                .decodeResource(resources, R.mipmap.locationpage_icon_location)))
//        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
////            markerOption.setFlat(true)//设置marker平贴地图效果
//        if (marker!=null){
//            marker!!.remove()
//        }
//        marker=aMap!!.addMarker(markerOption)
//        aMap!!.setInfoWindowAdapter(object :AMap.InfoWindowAdapter{
//            /**
//             * 监听自定义infowindow窗口的infocontents事件回调
//             */
//            override fun getInfoContents(marker: Marker): View? {
//                return null
//                //示例没有采用该方法。
//            }
//
//            var infoWindow: View? = null
//
//            /**
//             * 监听自定义infowindow窗口的infowindow事件回调
//             */
//            override fun getInfoWindow(marker: Marker): View? {
//                if (infoWindow == null) {
//                    infoWindow = LayoutInflater.from(activity!!).inflate(
//                            R.layout.custom_info_window, null)
//                }
//                render(marker, infoWindow)
//                return infoWindow
//                //加载custom_info_window.xml布局文件作为InfoWindow的样式
//                //该布局可在官方Demo布局文件夹下找到
//            }
//
//            /**
//             * 自定义infowinfow窗口
//             */
//            fun render(marker: Marker, view: View?) {
//                //如果想修改自定义Infow中内容，请通过view找到它并修改
//                var tv=view?.findViewById<TextView>(R.id.snippet)
//                tv?.text=name
//            }
//        })
//
////            aMap!!. moveCamera(CameraUpdateFactory.changeLatLng(latLng))
//        marker!!.showInfoWindow()
//    }

    override fun setLayoutData() {
        pageIndex=1
        presenter.getLocationAddress(ClockAddressBody(lng,lat,pageIndex.toString(),pageSize.toString()))
    }

    override fun clickListener() {
        Click.viewClick(iv_close).subscribe {
            dismiss()
        }

        edit_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId== EditorInfo.IME_ACTION_SEARCH){
                if (edit_search.text!=null&&edit_search.text.toString().isNotEmpty()){
                    search=edit_search.text.toString()
                    pageIndex=1
                    adapter=null
                    presenter.getSearchClock(SearchClockBody(search,pageIndex.toString(),pageSize.toString()))
                }else{
                    Toast.Tips("请输入搜索内容")
                }
            }
            return@setOnEditorActionListener false
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(edit_search.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
    }


    interface Address{
        fun setAddress(name:String,id:String)
    }
}