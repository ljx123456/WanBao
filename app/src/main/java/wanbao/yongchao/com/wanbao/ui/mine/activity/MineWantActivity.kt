package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_mine_want.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.main.adapter.FragmentAdapter
import wanbao.yongchao.com.wanbao.ui.mine.fragment.MineWantActivitesFragment
import wanbao.yongchao.com.wanbao.ui.mine.fragment.MineWantLandMarkFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click

class MineWantActivity:BaseActivity() {

    var mFragmentAdapter: FragmentAdapter? = null
    var fragments = ArrayList<Fragment>()
    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_mine_want

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        var titles = ArrayList<String>()
        titles.add("夜计划")
        titles.add("城市地标")

//        fragments.add(CommunityHotFragment("热门"))
//        fragments.add(CommunityHotFragment("附近"))
//        fragments.add(CommunityFollowFragment("关注"))
        fragments.add(MineWantActivitesFragment())
        fragments.add(MineWantLandMarkFragment())

        titles.forEach {
            tab_want.addTab(tab_want.newTab().setText(it))
        }
        mFragmentAdapter = FragmentAdapter(supportFragmentManager, fragments, titles)
        vp_want.adapter = mFragmentAdapter
        tab_want.setupWithViewPager(vp_want)
        tab_want.setTabsFromPagerAdapter(mFragmentAdapter)
        vp_want.setOffscreenPageLimit(2)
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }
    }
}