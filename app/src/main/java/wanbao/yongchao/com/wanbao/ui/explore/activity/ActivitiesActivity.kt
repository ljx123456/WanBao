package wanbao.yongchao.com.wanbao.ui.explore.activity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_activities.*
import kotlinx.android.synthetic.main.pop_activities_sort.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.explore.adapter.ExploreActivityAdapter
import wanbao.yongchao.com.wanbao.ui.explore.fragment.ActivityFragment
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.ExploreActivityBean
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.ExploreActivityBody
import wanbao.yongchao.com.wanbao.ui.explore.mvp.presenter.ExploreActivityFragmentPresenter
import wanbao.yongchao.com.wanbao.ui.explore.mvp.view.ExploreActivityFragmentView
import wanbao.yongchao.com.wanbao.ui.main.adapter.FragmentAdapter
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.pop.PopupWindowHelper
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class ActivitiesActivity:BaseActivity(){//,ExploreActivityFragmentView

    var mFragmentAdapter: FragmentAdapter? = null
    var fragments = ArrayList<ActivityFragment>()
    private var titles=ArrayList<String>()

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_activities

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        titles.add("精选")
        titles.add("美食")
        titles.add("旅游")
        titles.add("娱乐")
        titles.add("生活")
        titles.add("文化")

        titles.forEach {
            fragments.add(ActivityFragment(it))
            tab.addTab(tab.newTab().setText(it))
        }
        mFragmentAdapter = FragmentAdapter(supportFragmentManager, fragments, titles)
        vp_activities.adapter = mFragmentAdapter
        tab.setupWithViewPager(vp_activities)
        tab.setTabsFromPagerAdapter(mFragmentAdapter)
        vp_activities.offscreenPageLimit=3

    }

    override fun clickListener() {
        Click.viewClick(iv_back).subscribe {
            finish()
        }


        Click.viewClick(iv_search).subscribe {
            intentUtils.intentActivitiesSearch()
        }


    }



//    override fun onResume() {
//        super.onResume()
//        var avatar=""
//        var use = GreenDaoHelper.getDaoSessions().userDBDao
//        var business= GreenDaoHelper.getDaoSessions().businessDBDao
//        if (use != null||business!=null) {
//            var info = use.loadAll()
//            var inf = business.loadAll()
//            if (info != null && info.size > 0) {
//                avatar=info[0].avatar
//            }else if (inf != null && inf.size > 0) {
//                avatar=inf[0].avatar
//            }else{
//                avatar=""
//            }
//        } else{
//            avatar=""
//        }
//        if (avatar!=""&&user.getIsWant()&&user.getWantId()!=""){
//            if (adapter!=null){
//                adapter!!.data.forEachIndexed { position, dataBean ->
//                    if (adapter!!.data[position].id.toString()==user.getWantId()){
//                        if (user.getIsAddWant()==0) {
//                            if (adapter!!.data[position].wantAvatars.size < 5) {
//                                adapter!!.data[position].wantAvatars.add(avatar)
//                            }
//                            adapter!!.data[position].wantNum = adapter!!.data[position].wantNum + 1
//                        }else if (user.getIsAddWant()==1){
//                            if (adapter!!.data[position].wantNum<=5){
//                                adapter!!.data[position].wantAvatars.remove(avatar)
//                            }
//                            adapter!!.data[position].wantNum = adapter!!.data[position].wantNum - 1
//                        }
//                        adapter!!.notifyDataSetChanged()
//                        user.setWantId("")
//                        user.setIsWant(false)
//                        user.setIsAddWant(-1)
//                    }
//                }
//            }
//        }
//    }
}