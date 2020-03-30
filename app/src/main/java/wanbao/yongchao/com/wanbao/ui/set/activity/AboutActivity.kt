package wanbao.yongchao.com.wanbao.ui.set.activity

import kotlinx.android.synthetic.main.activity_about.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.utils.utils.Click

class AboutActivity:BaseActivity(){
    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_about

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }
    }

}