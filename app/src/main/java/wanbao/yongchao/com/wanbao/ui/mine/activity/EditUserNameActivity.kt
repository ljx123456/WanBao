package wanbao.yongchao.com.wanbao.ui.mine.activity

import kotlinx.android.synthetic.main.activity_edit_user_name.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditUserBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditUserBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditUserView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class EditUserNameActivity:BaseActivity(),EditUserView {
    override fun getEditUserRequest(data: EditUserBean) {
        Toast.Tips("已修改")
        finish()
        user.setChange(true)
    }

    private val presenter by lazy { EditUserPresenter(this,this,this) }

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
                var body=EditUserBody()
                body.type="3"
                body.nickname=edit_user_name.text.toString()
                presenter.setEditUser(body)
            }else{
                Toast.Tips("请输入昵称")
            }
        }

        Click.viewClick(iv_back).subscribe {
            finish()
        }
    }
}