package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_edit_user_sign.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditUserBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditUserBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditUserView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class EditUserSignActivity:BaseActivity(),EditUserView{

    private val presenter by lazy { EditUserPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_edit_user_sign

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        if (intent.getStringExtra("sign")!=null){
            edit_sign.setText(intent.getStringExtra("sign"))
        }
    }

    override fun clickListener() {
        Click.viewClick(iv_back).subscribe {
            finish()
        }

        Click.viewClick(tv_keep).subscribe {
            if (edit_sign.text!=null&&edit_sign.text.toString().isNotEmpty()){
                var body=EditUserBody()
                body.type="5"
                body.signature=edit_sign.text.toString()
                presenter.setEditUser(body)
            }else{
                Toast.Tips("请输入签名")
            }
        }

        edit_sign.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s!!.toString().isNotEmpty()){
                    tv_num.text="("+s.toString().length.toString()+"/50"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    override fun getEditUserRequest(data: EditUserBean) {
        Toast.Tips("已添加")
        user.setChange(true)
        finish()
    }
}