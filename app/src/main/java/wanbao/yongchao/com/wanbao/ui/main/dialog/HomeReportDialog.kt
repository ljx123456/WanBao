package wanbao.yongchao.com.wanbao.ui.main.dialog

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.dialog_community_report.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class HomeReportDialog(val id:String) : BaseDialogFragment() {

    private lateinit var dialog:CommunityReportDetailsDialog
    override fun setLayoutID(): Int = R.layout.dialog_community_report

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        dialog= CommunityReportDetailsDialog(id,"2")
        view8.visibility=View.INVISIBLE
        tv_report8.visibility= View.INVISIBLE
    }

    override fun clickListener() {
        Click.viewClick(dialog_close).subscribe {
            dismiss()
        }

        Click.viewClick(dialogOver).subscribe {
            dismiss()
        }

        Click.viewClick(tv_report1).subscribe {
            dismiss()
            dialog.setReportID(1)
            dialog.show(activity!!.supportFragmentManager,"")
        }

        Click.viewClick(tv_report2).subscribe {
            dismiss()
            dialog.setReportID(2)
            dialog.show(activity!!.supportFragmentManager,"")
        }

        Click.viewClick(tv_report3).subscribe {
            dismiss()
            dialog.setReportID(3)
            dialog.show(activity!!.supportFragmentManager,"")
        }

        Click.viewClick(tv_report4).subscribe {
            dismiss()
            dialog.setReportID(4)
            dialog.show(activity!!.supportFragmentManager,"")
        }

        Click.viewClick(tv_report5).subscribe {
            dismiss()
            dialog.setReportID(5)
            dialog.show(activity!!.supportFragmentManager,"")
        }

        Click.viewClick(tv_report6).subscribe {
            dismiss()
            dialog.setReportID(6)
            dialog.show(activity!!.supportFragmentManager,"")
        }

        Click.viewClick(tv_report7).subscribe {
            dismiss()
            dialog.setReportID(7)
            dialog.show(activity!!.supportFragmentManager,"")
        }



    }
}