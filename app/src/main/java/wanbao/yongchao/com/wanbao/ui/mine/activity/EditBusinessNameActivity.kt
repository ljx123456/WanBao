package wanbao.yongchao.com.wanbao.ui.mine.activity

import kotlinx.android.synthetic.main.activity_edit_business_name.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditBusinessBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditBusinessBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditBusinessPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditBusinessView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class EditBusinessNameActivity:BaseActivity(),EditBusinessView {
    override fun getEditBusinessRequest(data: EditBusinessBean) {
        user.setChange(true)
        Toast.Tips("已修改")
        finish()
    }

    private val presenter by lazy { EditBusinessPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_edit_user_name

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        edit_user_name.setText(intent.getStringExtra("name"))
    }

    override fun clickListener() {
        Click.viewClick(tv_keep).subscribe {
            if (edit_user_name.text!=null&&edit_user_name.text.toString().isNotEmpty()){
                var body=EditBusinessBody()
                body.type="3"
                body.nickname=edit_user_name.text.toString()
                presenter.setEditBusiness(body)
            }else{
                Toast.Tips("请输入昵称")
            }
        }

        Click.viewClick(iv_back).subscribe {
            finish()
        }
    }
}