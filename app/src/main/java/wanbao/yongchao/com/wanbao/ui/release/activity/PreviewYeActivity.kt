package wanbao.yongchao.com.wanbao.ui.release.activity

import android.support.v4.widget.NestedScrollView
import android.view.View
import kotlinx.android.synthetic.main.activity_preview_ye.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo
import wanbao.yongchao.com.wanbao.ui.main.utils.explore_banner.BannerDetailsUtil
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils

class PreviewYeActivity:BaseActivity() {
    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_preview_ye

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        if (intent.getStringArrayListExtra("images")!=null&&intent.getStringArrayListExtra("images").size>0){
            var imagelist = ArrayList<ImageBannerInfo>()
            intent.getStringArrayListExtra("images").forEach {
                imagelist.add(ImageBannerInfo(it,false,0,"","",""))
            }
            BannerDetailsUtil.setBanner(banner, imagelist)
        }

        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                ImageLoad.setUserHead(info[0].avatar,iv_header)
                tv_name.text=info[0].nickName
                tv_time.text="刚刚"
            }else if (inf != null && inf.size > 0) {
                ImageLoad.setUserHead(inf[0].avatar,iv_header)
                tv_name.text=inf[0].nickName
                tv_time.text="刚刚"
            }
        }

        try {
//            var user=DBUtils.getBusiness()

            var user=DBUtils.getUser()


        }catch (e:Exception){

        }
        if (intent.getStringExtra("title")!=null&&intent.getStringExtra("title")!=""){
            tv_title.text=intent.getStringExtra("title")
        }

        if (intent.getStringExtra("content")!=null&&intent.getStringExtra("content")!=""){
            tv_content.text=intent.getStringExtra("content")
        }

        if (intent.getStringExtra("area")!=null&&intent.getStringExtra("area")!=""){
            tv_city.text=intent.getStringExtra("area")
        }

        if (intent.getStringExtra("address")!=null&&intent.getStringExtra("title")!=""){
            tv_address.text=intent.getStringExtra("address")
        }

        if (intent.getStringExtra("time")!=null&&intent.getStringExtra("time")!=""){
            tv_activities_time.text="活动时间："+intent.getStringExtra("time")
        }

        if (intent.getStringArrayListExtra("phone")!=null&&intent.getStringArrayListExtra("phone").size>0){
            if (intent.getStringArrayListExtra("phone").size==1){
                tv_phone.text="联系电话："+intent.getStringArrayListExtra("phone")[0]
            }else{
                tv_phone.text="联系电话："+intent.getStringArrayListExtra("phone")[0]+" / "+intent.getStringArrayListExtra("phone")[1]
            }
        }

    }

    override fun clickListener() {

        scroll.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView, scrollX: Int, scrollY: Int, oldx: Int, oldY: Int) {
                if (scrollY == 0) {
                    //顶部
//                    manage.setmCanVerticalScroll(false)
                    iv_back.visibility= View.VISIBLE
                }else{
                    iv_back.visibility= View.GONE
                }
                var alpha=0f
                var h= SystemUtils.dip2px(this@PreviewYeActivity,230f)
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

    }
}