package wanbao.yongchao.com.wanbao.ui.explore.activity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_shop.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.main.adapter.FragmentAdapter
import wanbao.yongchao.com.wanbao.ui.explore.fragment.ShopFragment
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.pop.PopupWindowHelper
import wanbao.yongchao.com.wanbao.utils.utils.Click

class ShopActivity:BaseActivity() {

    private var titles=ArrayList<String>()
    var mFragmentAdapter: FragmentAdapter? = null
    var fragments=ArrayList<ShopFragment>()

    private lateinit var pop: PopupWindowHelper
    private lateinit var popView: View

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_shop

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        titles.add("精选")
//        titles.add("全部")
        titles.add("美食")
        titles.add("旅游")
        titles.add("娱乐")
        titles.add("生活")
        titles.add("文化")

        titles.forEach {
            fragments.add(ShopFragment(it))
            tab.addTab(tab.newTab().setText(it))
        }
        mFragmentAdapter = FragmentAdapter(supportFragmentManager, fragments, titles)
        vp_shop.adapter = mFragmentAdapter
        tab.setupWithViewPager(vp_shop)
        tab.setTabsFromPagerAdapter(mFragmentAdapter)
        vp_shop.offscreenPageLimit=3


    }

    override fun clickListener() {
        Click.viewClick(iv_back).subscribe {
            finish()
        }

//        Click.viewClick(tv_sort).subscribe {
//            if (tv_sort.text.toString()=="距离最近"){
//                popView = LayoutInflater.from(this).inflate(R.layout.pop_shop, null)
//                pop = PopupWindowHelper(popView,this)
//                popView.findViewById<TextView>(R.id.tv_distance).setTextColor(Color.parseColor("#FCC725"))
//                popView.findViewById<TextView>(R.id.tv_clock).setTextColor(Color.parseColor("#d9ffffff"))
//            }else{
//                popView = LayoutInflater.from(this).inflate(R.layout.pop_shop, null)
//                pop = PopupWindowHelper(popView,this)
//                popView.findViewById<TextView>(R.id.tv_distance).setTextColor(Color.parseColor("#d9ffffff"))
//                popView.findViewById<TextView>(R.id.tv_clock).setTextColor(Color.parseColor("#FCC725"))
//            }
//            pop.showAsDropDownLocation(tv_sort,-50,-20)
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_distance)).subscribe {
//                tv_sort.text="距离最近"
//                pop.dismiss()
//                fragments.forEach {
//                    it.setSort("距离最近")
//                }
//            }
//            Click.viewClick(popView.findViewById<TextView>(R.id.tv_clock)).subscribe {
//                tv_sort.text="最多打卡"
//                pop.dismiss()
//                fragments.forEach {
//                    it.setSort("最多打卡")
//                }
//            }
//        }



        Click.viewClick(iv_search).subscribe {
            intentUtils.intentShopSearch()
        }
    }
}