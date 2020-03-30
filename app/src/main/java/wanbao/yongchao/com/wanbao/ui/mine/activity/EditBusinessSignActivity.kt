package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_edit_business_sign.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditBusinessBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditBusinessBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditBusinessPresenter

import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditBusinessView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class EditBusinessSignActivity:BaseActivity(),EditBusinessView {
    override fun getEditBusinessRequest(data: EditBusinessBean) {
        Toast.Tips("已添加")
        finish()
        user.setChange(true)
    }

    private val presenter by lazy { EditBusinessPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_edit_business_sign

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
                var body= EditBusinessBody()
                body.type="5"
                body.signature=edit_sign.text.toString()
                presenter.setEditBusiness(body)
            }else{
                Toast.Tips("请输入简介")
            }
        }

        edit_sign.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s!!.toString().isNotEmpty()){
                    tv_num.text="("+s.toString().length.toString()+"/100"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}