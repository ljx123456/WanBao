package wanbao.yongchao.com.wanbao.ui.set.activity

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_suggest.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.SuggestBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.presenter.SuggestPresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.SuggestView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SuggestActivity:BaseActivity(),SuggestView {

    private val presenter by lazy { SuggestPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_suggest

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }

        edit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!=null&&s.toString().isNotEmpty()){
                    tv_num.text=s.toString().length.toString()+"/500"
                    tv_suggest.isEnabled=true
                    tv_suggest.setTextColor(Color.parseColor("#ffffffff"))
                }else{
                    tv_num.text="0/500"
                    tv_suggest.isEnabled=false
                    tv_suggest.setTextColor(Color.parseColor("#40ffffff"))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        Click.viewClick(tv_suggest).subscribe {
            presenter.getSuggest(SuggestBody(edit.text.toString()))
        }
    }

    override fun getSuggestRequest() {
        Toast.Tips("已提交")
        finish()
    }
}