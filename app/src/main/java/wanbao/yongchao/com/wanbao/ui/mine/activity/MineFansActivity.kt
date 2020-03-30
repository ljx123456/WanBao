package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.graphics.Color
import android.support.v4.app.Fragment
import android.util.TypedValue
import kotlinx.android.synthetic.main.activity_mine_fans.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.main.adapter.ViewPageAdapter
import wanbao.yongchao.com.wanbao.ui.main.fragment.FansFragment
import wanbao.yongchao.com.wanbao.ui.mine.fragment.MineFanFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click

class MineFansActivity: BaseActivity(){

    var mFragmentAdapter: ViewPageAdapter? = null
    var fragments = ArrayList<Fragment>()
    var first=false
    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_mine_fans

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        fragments.add(MineFanFragment(intent.getStringExtra("id"),"粉丝"))
        fragments.add(MineFanFragment(intent.getStringExtra("id"),"关注"))
        mFragmentAdapter= ViewPageAdapter(supportFragmentManager,fragments)
        vp_fans.adapter=mFragmentAdapter
        var str=intent.getStringExtra("fans")
        if (str=="我关注的"){
            vp_fans.currentItem=1
            tv_like.setTextColor(Color.parseColor("#FCC725"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)

            tv_like_num.setTextColor(Color.parseColor("#FCC725"))
            tv_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_fans.setTextColor(Color.parseColor("#a6ffffff"))
            tv_fans.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)

            tv_fans_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_fans_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)
        }else{
            vp_fans.currentItem=0
            tv_fans.setTextColor(Color.parseColor("#FCC725"))
            tv_fans.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)

            tv_fans_num.setTextColor(Color.parseColor("#FCC725"))
            tv_fans_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_like.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)

            tv_like_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)
        }
    }

    override fun clickListener() {

        Click.viewClick(iv_back).subscribe {
            finish()
        }

        Click.viewClick(layout_like).subscribe {
            tv_like.setTextColor(Color.parseColor("#FCC725"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)

            tv_like_num.setTextColor(Color.parseColor("#FCC725"))
            tv_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_fans.setTextColor(Color.parseColor("#a6ffffff"))
            tv_fans.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)

            tv_fans_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_fans_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

            vp_fans.currentItem=1
        }

        Click.viewClick(layout_fans).subscribe {
            tv_fans.setTextColor(Color.parseColor("#FCC725"))
            tv_fans.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)

            tv_fans_num.setTextColor(Color.parseColor("#FCC725"))
            tv_fans_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

            tv_like.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)

            tv_like_num.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like_num.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)

            vp_fans.currentItem=0
        }

    }

    override fun onResume() {
        super.onResume()
        if (first)
            (fragments[vp_fans.currentItem] as MineFanFragment).setRefresh()
        else
            first=true
    }
}