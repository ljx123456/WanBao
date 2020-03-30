package wanbao.yongchao.com.wanbao.ui.main.dialog

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.dialog_report_details.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.ReportBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.ReportDetailsDialogPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.ReportDetailsDialogView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast


@SuppressLint("ValidFragment")
class CommunityReportDetailsDialog(val id:String,val type:String):BaseDialogFragment() ,ReportDetailsDialogView{
    override fun getReportRequest() {
        Toast.Tips("举报成功")
    }

    private var  report_id=0
    private val presenter by lazy { ReportDetailsDialogPresenter(this,this,activity!!) }

    override fun setLayoutID(): Int = R.layout.dialog_report_details

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        this.isCancelable=false
    }

    override fun clickListener() {
        dialog_edit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable) {
                if (s!=null&&s.toString()!=""){
                    dialog_num.text="${s.toString().length}/120"
                }else{
                    dialog_num.text="0/120"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        Click.viewClick(dialog_submit).subscribe {
            if (dialog_edit.text!=null&&dialog_edit.text.toString()!=""&&dialog_edit.text.toString().isNotEmpty()){
                dismiss()
                presenter.getReport(ReportBody(type,id,report_id.toString(),dialog_edit.text.toString()))
            }else{
                Toast.Tips("请描述举报详情")
            }
        }

        Click.viewClick(dialog_close).subscribe {
            dismiss()
        }
    }

    fun setReportID(id:Int){
        this.report_id=id
        Log.e("测试",report_id.toString())
    }


}