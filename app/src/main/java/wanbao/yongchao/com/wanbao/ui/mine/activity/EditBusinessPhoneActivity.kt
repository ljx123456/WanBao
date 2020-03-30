package wanbao.yongchao.com.wanbao.ui.mine.activity

import kotlinx.android.synthetic.main.activity_edit_business_phone.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditBusinessBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditBusinessBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditBusinessPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditBusinessView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class EditBusinessPhoneActivity:BaseActivity() ,EditBusinessView{
    override fun getEditBusinessRequest(data: EditBusinessBean) {
        Toast.Tips("修改成功")
        finish()
        user.setChange(true)
    }

    private val presenter by lazy { EditBusinessPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_edit_business_phone

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        if (intent.getStringArrayListExtra("phone")!=null&&intent.getStringArrayListExtra("phone").size>0){
            if (intent.getStringArrayListExtra("phone").size==1){
                edit_phone1.setText(intent.getStringArrayListExtra("phone")[0])
            }else{
                edit_phone1.setText(intent.getStringArrayListExtra("phone")[0])
                edit_phone2.setText(intent.getStringArrayListExtra("phone")[1])
            }
        }
    }

    override fun clickListener() {
        Click.viewClick(iv_back).subscribe { finish() }

        Click.viewClick(tv_keep).subscribe {
            if (edit_phone1.text!=null&&edit_phone1.text.toString().isNotEmpty()||edit_phone2.text!=null&&edit_phone2.text.toString().isNotEmpty()){
                var list=ArrayList<String>()
                if (edit_phone1.text!=null&&edit_phone1.text.toString().isNotEmpty()){
                    list.add(edit_phone1.text.toString())
                }

                if (edit_phone2.text!=null&&edit_phone2.text.toString().isNotEmpty()){
                    list.add(edit_phone2.text.toString())
                }

                var body=EditBusinessBody()
                body.type="4"
                body.phones=list
                presenter.setEditBusiness(body)
            }
        }
    }
}