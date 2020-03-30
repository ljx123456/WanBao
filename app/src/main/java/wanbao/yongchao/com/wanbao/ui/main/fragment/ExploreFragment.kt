package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_business_info.*
import kotlinx.android.synthetic.main.fragment_explore.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.fragment.ExploreActivityFragment
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo
import wanbao.yongchao.com.wanbao.ui.explore.adapter.ExploreShopAdapter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreBannerBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreLandMarkBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreShopBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.ExplorePresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ExploreView
import wanbao.yongchao.com.wanbao.ui.image.ImageExploreInfo
import wanbao.yongchao.com.wanbao.ui.main.dialog.YeScreenDialog
import wanbao.yongchao.com.wanbao.ui.main.utils.explore_banner.BannerUtil
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.pop.PopupWindowHelper
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class ExploreFragment : BaseFragment(),ExploreView, UserPermissions.MemoryReadPermissionsFace, LocationUtils.Location{
    override fun getExploreBannerError() {
        swip.isRefreshing=false
    }

    override fun getExploreLandMarkError() {

    }

    override fun getExploreShopError() {

    }

    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        LocationUtils(this).getLocationOnce()
    }

    override fun requestPermissionsFaceError() {
        Toast.Tips("请打开定位权限")
    }

    override fun getLocationSuccess(city: String) {
        var body=ExploreShopBody()
        body.cityId=user.getCityID()
        body.type=-1
        body.sort=1
        body.pageIndex=1
        body.pageSize=8
        body.lat= user.getLocationLat()
        body.lng=user.getLocationLng()
        presenter.getExploreShop(body)
    }

    override fun getExploreShopRequest(data: ExploreShopBean) {
        swip.isRefreshing=false
        var shopAdapter= ExploreShopAdapter(data.data)
        var managerShop=LinearLayoutManager(activity!!)
        managerShop.orientation=LinearLayout.HORIZONTAL
        recy_shop.layoutManager=managerShop
        recy_shop.adapter=shopAdapter

        shopAdapter.setOnItemClickListener { adapter, view, position ->
            intentUtils.intentBusinessHomePage(shopAdapter.data[position].businessId.toString())
        }
    }

    override fun getExploreLandMarkRequest(data: ExploreLandMarkBean) {
        swip.isRefreshing=false
        if (data.data!=null&&data.data.size==3){
            layout_landmark.visibility= View.VISIBLE
            ImageLoad.setImage(data.data[0].avatar,iv_land1)
            tv_land_name1.text=data.data[0].cityMapName
            tv_land_num1.text=data.data[0].wantUserNum.toString()+"人想去"

            ImageLoad.setImage(data.data[1].avatar,iv_land2)
            tv_land_name2.text=data.data[1].cityMapName
            tv_land_num2.text=data.data[1].wantUserNum.toString()+"人想去"

            ImageLoad.setImage(data.data[2].avatar,iv_land3)
            tv_land_name3.text=data.data[2].cityMapName
            tv_land_num3.text=data.data[2].wantUserNum.toString()+"人想去"

//            ImageLoad.setImage(data.data[3].avatar,iv_land4)
//            tv_land_name4.text=data.data[3].cityMapName
//            tv_land_num4.text=data.data[3].wantUserNum.toString()+"人想去"

            Click.viewClick(layout_land1).subscribe {
                intentUtils.intentLandMarkDetails(data.data[0].cityMapId.toString())
            }

            Click.viewClick(layout_land2).subscribe {
                intentUtils.intentLandMarkDetails(data.data[1].cityMapId.toString())
            }

            Click.viewClick(layout_land3).subscribe {
                intentUtils.intentLandMarkDetails(data.data[2].cityMapId.toString())
            }

//            Click.viewClick(layout_land4).subscribe {
//                intentUtils.intentLandMarkDetails(data.data[3].cityMapId.toString())
//            }
        }else{
            layout_landmark.visibility= View.INVISIBLE
        }
    }

    override fun getExploreBannerRequest(data: ExploreBannerBean) {
        swip.isRefreshing=false
        if (data.data!=null&&data.data.size>0) {
            banner.visibility=View.VISIBLE
            var imagelist = ArrayList<ImageExploreInfo>()
            data.data.forEach {
                imagelist.add(ImageExploreInfo(it.image, false, 0, it.id, it.objectType, it.objectId))
            }
            BannerUtil.setBanner(banner, imagelist)
        }else{
            banner.visibility=View.INVISIBLE
        }
    }


    var fragment: BaseFragment? = null
    lateinit var transaction:FragmentTransaction
//    private var dialog:YeScreenDialog?=null

    private lateinit var pop: PopupWindowHelper
    private lateinit var popView: View
    private var type=""

    private val presenter by lazy { ExplorePresenter(this,this,activity!!) }

    override fun openEventBus(): Boolean = false

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {

        if (user.getCityID()!="") {
            tv_city.text=user.getCity().replace("市","")
            presenter.getExploreBanner(ExploreBannerBody(user.getCityID().toInt()))
            presenter.getExploreLandMark(ExploreLandMarkBody(user.getCityID().toInt(), 1, 3))
            UserPermissions.userLocation(activity!!, this)

//        setList()


//        landMarkAdapter.setOnItemClickListener { adapter, view, position ->
//            intentUtils.intentLandMarkDetails()
//        }


            initFragment()
        }


    }

    override fun setFragmentListener() {

        scroll.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView?, p1: Int, p2: Int, p3: Int, p4: Int) {

                var  contentView = scroll.getChildAt(0)
                if (contentView != null && contentView.getMeasuredHeight() == (scroll.getScrollY() + scroll.getHeight())) {
                    //底部
                    if (fragment!=null)
                        (fragment!! as ExploreActivityFragment).setLoadMore()
                }

                //如果向上滑动的距离>=iv_img.height - tv_title_text.height,隐藏的标题导航栏设置显示
//                var distanceScrollY = layout_top_user.height - layout_title_user.height+ SystemUtils.dip2px(activity!!,20f)
                var distanceScrollY = scroll_top.height+ SystemUtils.dip2px(activity!!,44f)
                if (p2 >= distanceScrollY) {
                    layout_title.visibility = View.VISIBLE
//                    ll_tab.visibility = View.INVISIBLE
//                    title_divider.visibility = View.VISIBLE
                } else {
                    layout_title.visibility = View.INVISIBLE
//                    ll_tab.visibility = View.VISIBLE
//                    title_divider.visibility = View.GONE
                }
            }
        })
        Click.viewClick(tv_city).subscribe {
//            ShowDialog.showCustomDialogNoTitle(activity!!,"其他城市即将开放","好哒 (๑‾ ꇴ ‾๑)",object : DialogInterface.OnClickListener{
//                override fun onClick(dialog: DialogInterface, which: Int) {
//                    dialog.dismiss()
//                }
//
//            })
            intentUtils.intentCity()
        }

        Click.viewClick(layout_search).subscribe {
            intentUtils.intentCommunitySearch()
        }


        Click.viewClick(tv_landmark_more).subscribe {
            intentUtils.intentLandMark()
        }

        Click.viewClick(tv_shop_more).subscribe {
            intentUtils.intentShop()
        }

//        Click.viewClick(tv_activities_more).subscribe {
//            intentUtils.intentActivities()
//        }
        swip.setOnRefreshListener {
            presenter.getExploreBanner(ExploreBannerBody(user.getCityID().toInt()))
            presenter.getExploreLandMark(ExploreLandMarkBody(user.getCityID().toInt(),1,3))
            if (user.getLocationLat()!=""){
                var body=ExploreShopBody()
                body.cityId=user.getCityID()
                body.type=-1
                body.sort=1
                body.pageIndex=1
                body.pageSize=8
                body.lat= user.getLocationLat()
                body.lng=user.getLocationLng()
                presenter.getExploreShop(body)
            }
            initFragment()
        }

        Click.viewClick(tv_activities_screen).subscribe {
            intentUtils.intentActivities()
//            popView = LayoutInflater.from(mContext!!).inflate(R.layout.dialog_screen_ye, null)
//            pop = PopupWindowHelper(popView, mContext)
//            pop.showAsDropDownLocation(layout_ye, 0, 0)
//            if (type!=""){
//                when(type){
//                    "美食"->{
//                        popView.findViewById<TextView>(R.id.tv_sort1).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
//                    "旅游"->{
//                        popView.findViewById<TextView>(R.id.tv_sort2).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
//                    "娱乐"->{
//                        popView.findViewById<TextView>(R.id.tv_sort3).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
//                    "生活"->{
//                        popView.findViewById<TextView>(R.id.tv_sort4).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
//                    "文化"->{
//                        popView.findViewById<TextView>(R.id.tv_sort5).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
////                "文化"->{
////                    tv_sort6.setTextColor(Color.parseColor("#fcc725"))
////                }
//                }
//            }else{
//                popView.findViewById<TextView>(R.id.tv_sort1).setTextColor(Color.parseColor("#d9ffffff"))
//                popView.findViewById<TextView>(R.id.tv_sort2).setTextColor(Color.parseColor("#d9ffffff"))
//                popView.findViewById<TextView>(R.id.tv_sort3).setTextColor(Color.parseColor("#d9ffffff"))
//                popView.findViewById<TextView>(R.id.tv_sort4).setTextColor(Color.parseColor("#d9ffffff"))
//                popView.findViewById<TextView>(R.id.tv_sort5).setTextColor(Color.parseColor("#d9ffffff"))
////            tv_sort6.setTextColor(Color.parseColor("#d9ffffff"))
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort1)).subscribe {
//                pop.dismiss()
//                type="美食"
//                tv_activities_screen.text="美食"
//                tv_title_activities_screen.text="美食"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("11")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort2)).subscribe {
//                pop.dismiss()
//                type="旅游"
//                tv_activities_screen.text="旅游"
//                tv_title_activities_screen.text="旅游"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("12")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort3)).subscribe {
//                pop.dismiss()
//                type="娱乐"
//                tv_activities_screen.text="娱乐"
//                tv_title_activities_screen.text="娱乐"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("13")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort4)).subscribe {
//                pop.dismiss()
//                type="生活"
//                tv_activities_screen.text="生活"
//                tv_title_activities_screen.text="生活"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("14")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort5)).subscribe {
//                pop.dismiss()
//                type="文化"
//                tv_activities_screen.text="文化"
//                tv_title_activities_screen.text="文化"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("15")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<View>(R.id.dialogOver)).subscribe {
//                pop.dismiss()
//            }

        }

        Click.viewClick(tv_title_activities_screen).subscribe {
            intentUtils.intentActivities()
//            popView = LayoutInflater.from(mContext!!).inflate(R.layout.dialog_screen_ye, null)
//            pop = PopupWindowHelper(popView, mContext)
//            pop.showAsDropDownLocation(layout_title, 0, 0)
//            if (type!=""){
//                when(type){
//                    "美食"->{
//                        popView.findViewById<TextView>(R.id.tv_sort1).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
//                    "旅游"->{
//                        popView.findViewById<TextView>(R.id.tv_sort2).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
//                    "娱乐"->{
//                        popView.findViewById<TextView>(R.id.tv_sort3).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
//                    "生活"->{
//                        popView.findViewById<TextView>(R.id.tv_sort4).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
//                    "文化"->{
//                        popView.findViewById<TextView>(R.id.tv_sort5).setTextColor(Color.parseColor("#fcc725"))
//                    }
//
////                "文化"->{
////                    tv_sort6.setTextColor(Color.parseColor("#fcc725"))
////                }
//                }
//            }else{
//                popView.findViewById<TextView>(R.id.tv_sort1).setTextColor(Color.parseColor("#d9ffffff"))
//                popView.findViewById<TextView>(R.id.tv_sort2).setTextColor(Color.parseColor("#d9ffffff"))
//                popView.findViewById<TextView>(R.id.tv_sort3).setTextColor(Color.parseColor("#d9ffffff"))
//                popView.findViewById<TextView>(R.id.tv_sort4).setTextColor(Color.parseColor("#d9ffffff"))
//                popView.findViewById<TextView>(R.id.tv_sort5).setTextColor(Color.parseColor("#d9ffffff"))
////            tv_sort6.setTextColor(Color.parseColor("#d9ffffff"))
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort1)).subscribe {
//                pop.dismiss()
//                type="美食"
//                tv_activities_screen.text="美食"
//                tv_title_activities_screen.text="美食"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("11")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort2)).subscribe {
//                pop.dismiss()
//                type="旅游"
//                tv_activities_screen.text="旅游"
//                tv_title_activities_screen.text="旅游"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("12")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort3)).subscribe {
//                pop.dismiss()
//                type="娱乐"
//                tv_activities_screen.text="娱乐"
//                tv_title_activities_screen.text="娱乐"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("13")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort4)).subscribe {
//                pop.dismiss()
//                type="生活"
//                tv_activities_screen.text="生活"
//                tv_title_activities_screen.text="生活"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("14")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_sort5)).subscribe {
//                pop.dismiss()
//                type="文化"
//                tv_activities_screen.text="文化"
//                tv_title_activities_screen.text="文化"
//                if (fragment!=null){
//                    (fragment!! as ExploreActivityFragment).setSort("15")
//                }
//            }
//
//            Click.viewClick(popView.findViewById<View>(R.id.dialogOver)).subscribe {
//                pop.dismiss()
//            }
        }


    }

    override fun layoutID(): Int = R.layout.fragment_explore



    //设置初始显示的Fragment
    private fun initFragment() {
        fragment = ExploreActivityFragment("0")
        transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container, fragment!!)
        transaction.commitAllowingStateLoss()
        fl_container.visibility=View.VISIBLE
    }

    fun remove(){
        if (fragment!=null) {
            Log.e("测试","是否隐藏")
            fl_container.visibility=View.GONE
        }
    }

    public fun setCity(){
        tv_city.text=user.getCity().replace("市","")
        Log.e("测试",user.getCity().replace("市",""))
        presenter.getExploreBanner(ExploreBannerBody(user.getCityID().toInt()))
        presenter.getExploreLandMark(ExploreLandMarkBody(user.getCityID().toInt(),1,3))
        if (user.getLocationLat()!=""){
            var body=ExploreShopBody()
            body.cityId=user.getCityID()
            body.type=-1
            body.sort=1
            body.pageIndex=1
            body.pageSize=8
            body.lat= user.getLocationLat()
            body.lng=user.getLocationLng()
            presenter.getExploreShop(body)
        }
        initFragment()
    }


    public fun setWant(){
        if (user.getWantId()!=""){
            presenter.getExploreLandMark(ExploreLandMarkBody(user.getCityID().toInt(),1,3))
            if (fragment!=null){
                (fragment!! as ExploreActivityFragment).setUpdataWant(user.getWantId())
            }
        }
    }


//    override fun onHiddenChanged(hidden: Boolean) {
//        super.onHiddenChanged(hidden)
//
//    }

}