package wanbao.yongchao.com.wanbao.ui.explore.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.widget.NestedScrollView
import android.view.View
import kotlinx.android.synthetic.main.activity_landmark_details.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.WantGoBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.LandMarkDetailsPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.LandMarkDetailsView
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo
import wanbao.yongchao.com.wanbao.ui.main.dialog.LoginDialog
import wanbao.yongchao.com.wanbao.ui.main.dialog.SelectMapDialog
import wanbao.yongchao.com.wanbao.ui.main.fragment.TopicFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddressHomePageBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.AddressHomePageBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.AddressHomePagePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AddressHomePageView
import wanbao.yongchao.com.wanbao.ui.main.utils.explore_banner.BannerDetailsUtil
import wanbao.yongchao.com.wanbao.utils.dialog.ShareDialog
import wanbao.yongchao.com.wanbao.utils.http.BaseUrl
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.share.ShareUtil
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import java.io.File
import java.net.URISyntaxException

class LandMarkDetailsActivity:BaseActivity(),ShareDialog.Share, SelectMapDialog.SelectMapDialogFace,AddressHomePageView, UserPermissions.MemoryReadPermissionsFace, LocationUtils.Location,LandMarkDetailsView{
    override fun getWantGoRequest() {
        user.setIsWant(true)
        user.setWantId(intent.getStringExtra("id"))
        if (user.getLocationLat()!=""&&user.getLocationLng()!="") {
            presenter.getAddressHomePage(AddressHomePageBody(intent.getStringExtra("id"),user.getLocationLat(),user.getLocationLng() ))
        }else{
            UserPermissions.userLocation(mContext, this)
        }
        if (isWant){
            Toast.Tips("已取消标记")
            user.setIsAddWant(1)
            isWant=false
//            tv_want.setBackgroundResource(R.drawable.tv_follow_bg)
//            tv_want.setTextColor(Color.parseColor("#000000"))
//            tv_want.text="想去"
        }else{
            Toast.Tips("已标记想去")
            user.setIsAddWant(0)
            isWant=true
//            tv_want.setBackgroundResource(R.drawable.tv_followed_bg)
//            tv_want.setTextColor(Color.parseColor("#FCC725"))
//            tv_want.text="已想去"
        }
    }

    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        LocationUtils(this).getLocation()
    }

    override fun requestPermissionsFaceError() {
        presenter.getAddressHomePage(AddressHomePageBody(intent.getStringExtra("id")))
    }

    override fun getLocationSuccess(city: String) {
        presenter.getAddressHomePage(AddressHomePageBody(intent.getStringExtra("id"), user.getLocationLat(), user.getLocationLng() ))
    }

    override fun getAddressHomePageRequest(data: AddressHomePageBean) {
        var imagelist = ArrayList<ImageBannerInfo>()
        data.data.videoSet.forEach {
            imagelist.add(ImageBannerInfo(it,false,0,"","",""))
        }
        BannerDetailsUtil.setBanner(banner, imagelist)

        tv_name.text=data.data.cityMapName

        if (data.data.content!=null&&data.data.content!=""){
            tv_content.text=data.data.content
        }else{
            tv_content.text=""
        }

        tv_address_name.text=data.data.cityMapName
        if (data.data.km!=null&&data.data.km!="") {
            tv_address_details.text = data.data.cityMapAddress+" · 距离"+data.data.km
        }else{
            tv_address_details.text = data.data.cityMapAddress
        }

        if (data.data.wantUserAvaterSet!=null&&data.data.wantUserAvaterSet.size>0){
            layout_user.visibility=View.VISIBLE
            when(data.data.wantUserAvaterSet.size){
                1->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.GONE
                    iv_user3.visibility=View.GONE
                    iv_user4.visibility=View.GONE
                    iv_user5.visibility=View.GONE
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[0],iv_user1)
                }
                2->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.VISIBLE
                    iv_user3.visibility=View.GONE
                    iv_user4.visibility=View.GONE
                    iv_user5.visibility=View.GONE
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[0],iv_user1)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[1],iv_user2)
                }
                3->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.VISIBLE
                    iv_user3.visibility=View.VISIBLE
                    iv_user4.visibility=View.GONE
                    iv_user5.visibility=View.GONE
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[0],iv_user1)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[1],iv_user2)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[2],iv_user3)
                }
                4->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.VISIBLE
                    iv_user3.visibility=View.VISIBLE
                    iv_user4.visibility=View.VISIBLE
                    iv_user5.visibility=View.GONE
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[0],iv_user1)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[1],iv_user2)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[2],iv_user3)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[3],iv_user4)
                }
                5->{
                    iv_user1.visibility=View.VISIBLE
                    iv_user2.visibility=View.VISIBLE
                    iv_user3.visibility=View.VISIBLE
                    iv_user4.visibility=View.VISIBLE
                    iv_user5.visibility=View.VISIBLE
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[0],iv_user1)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[1],iv_user2)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[2],iv_user3)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[3],iv_user4)
                    ImageLoad.setUserHead(data.data.wantUserAvaterSet[4],iv_user5)

                }
            }
        }else{
            layout_user.visibility=View.GONE
            iv_user1.visibility=View.GONE
            iv_user2.visibility=View.GONE
            iv_user3.visibility=View.GONE
            iv_user4.visibility=View.GONE
            iv_user5.visibility=View.GONE
        }

        if (data.data.wantUserNum!=null&&data.data.wantUserNum>0){
            tv_num.text=data.data.wantUserNum.toString()+"人想去"
        }else{
            tv_num.text="暂时无人"
        }

        if (data.data.isWant==1){
            isWant=true
            tv_want.setBackgroundResource(R.drawable.tv_followed_bg)
            tv_want.setTextColor(Color.parseColor("#FCC725"))
            tv_want.text="已想去"
        }else{
            isWant=false
            tv_want.setBackgroundResource(R.drawable.tv_follow_bg)
            tv_want.setTextColor(Color.parseColor("#000000"))
            tv_want.text="想去"
        }

        lng=data.data.longitude
        lat=data.data.latitude
        name=data.data.cityMapName

        if (y==0) {
            if (fragment!=null){
                (fragment!! as TopicFragment).setRefresh()
            }else {
                initFragment(data.data.locationId.toString())
            }
        }

        Click.viewClick(tv_want).subscribe {
            if (isLogin) {
                detailsPresenter.getWantGo(WantGoBody(data.data.cityMapId))
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }
        }

        Click.viewClick(layout_want).subscribe {
            intentUtils.intentWant(data.data.cityMapId.toString(),data.data.wantUserNum.toString())
        }

        scroll.scrollTo(0,y)

    }

    override fun getAddressHomePageError() {

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

    override fun setShareWX() {
        ShareUtil.shareWxCircle(this,"这里很不错，你去过吗","这里很不错，你去过吗", BaseUrl.HOST_URL+"share/page?id="+intent.getStringExtra("id")+"&type=6")
    }

    override fun setSharePYQ() {
//        Toast.Tips("分享到朋友圈")
        ShareUtil.shareWx(this,"这里很不错，你去过吗","这里很不错，你去过吗",BaseUrl.HOST_URL+"share/page?id="+intent.getStringExtra("id")+"&type=6")
    }

    override fun setShareQQ() {
//        Toast.Tips("分享到QQ")
        ShareUtil.QQShare(this,"晚豹App","这里很不错，你去过吗",BaseUrl.HOST_URL+"share/page?id="+intent.getStringExtra("id")+"&type=6")
    }

    var fragment: BaseFragment? = null
    private lateinit var shareDialog: ShareDialog
    private var lat=""
    private var lng=""
    private var name=""
    private lateinit var dialog: SelectMapDialog
    private val presenter by lazy { AddressHomePagePresenter(this,this,this) }
    private val detailsPresenter by lazy { LandMarkDetailsPresenter(this,this,this) }
    private var isWant=false

    private var isLogin=false

    private var y=0

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_landmark_details

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

        var use = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (use!= null||business!=null) {
            var info = use.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                isLogin=true
            }else if (inf != null && inf.size > 0) {
                isLogin=true
            }else{
                isLogin=false
            }
        } else{
            isLogin=false
        }

        if (user.getLocationLat()!=""&&user.getLocationLng()!="") {
            presenter.getAddressHomePage(AddressHomePageBody(intent.getStringExtra("id"),user.getLocationLat(),user.getLocationLng() ))
        }else{
            UserPermissions.userLocation(mContext, this)
        }


        shareDialog= ShareDialog(this)

        dialog= SelectMapDialog(this)
        dialog.setDialogFace(this)




    }

    override fun clickListener() {

        scroll.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView, scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {

                y=scrollY
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
                var distanceScrollY = layout_top.height - layout_title.height+SystemUtils.dip2px(this@LandMarkDetailsActivity,20f)
                if (scrollY >= distanceScrollY) {
                    layout_choose.visibility = View.VISIBLE
//                    ll_tab.visibility = View.INVISIBLE
//                    title_divider.visibility = View.VISIBLE
                } else {
                    layout_choose.visibility = View.INVISIBLE
//                    ll_tab.visibility = View.VISIBLE
//                    title_divider.visibility = View.GONE
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
                var  contentView = scroll.getChildAt(0)
                if (contentView != null && contentView.getMeasuredHeight() == (scroll.getScrollY() + scroll.getHeight())) {
                    if (fragment!=null)
                        (fragment as TopicFragment).setLoadMore()
                }

                var alpha=0f
                var h= SystemUtils.dip2px(this@LandMarkDetailsActivity,355f)
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


        Click.viewClick(iv_back).subscribe {
            finish()
        }

        Click.viewClick(iv_title_back).subscribe {
            finish()
        }

        Click.viewClick(iv_share).subscribe {
            shareDialog.show(supportFragmentManager,"")
        }

        Click.viewClick(iv_title_share).subscribe {
            shareDialog.show(supportFragmentManager,"")
        }


        Click.viewClick(layout_address).subscribe {
            if (lat!=""&&lng!=""&&name!="") {
                dialog.showDialog()
            }
        }



    }

    //设置初始显示的Fragment
    private fun initFragment(id:String) {
        fragment = TopicFragment("打卡",id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container, fragment!!)
        transaction.commitAllowingStateLoss()
    }

    //判断包名是否存在
    fun isPackageInstalled(packageName: String): Boolean {
        return File("/data/data/$packageName").exists()
    }
}