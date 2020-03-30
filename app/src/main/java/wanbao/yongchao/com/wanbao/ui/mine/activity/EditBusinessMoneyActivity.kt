package wanbao.yongchao.com.wanbao.ui.mine.activity

import kotlinx.android.synthetic.main.activity_edit_business_money.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditBusinessBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditBusinessBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditBusinessPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditBusinessView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class EditBusinessMoneyActivity:BaseActivity() ,EditBusinessView{
    override fun getEditBusinessRequest(data: EditBusinessBean) {
        user.setChange(true)
        Toast.Tips("修改成功")
        finish()
    }

    private val presenter by lazy { EditBusinessPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_edit_business_money

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

    }

    override fun clickListener() {
        Click.viewClick(iv_back).subscribe { finish() }

        Click.viewClick(tv_keep).subscribe {
            if (edit_money.text!=null&&edit_money.text.toString().isNotEmpty()){
                var body=EditBusinessBody()
                body.type="11"
                body.perPersonConsume=edit_money.text.toString()
                presenter.setEditBusiness(body)
            }else{
                Toast.Tips("请输入人均消费金额")
            }
        }
    }
}