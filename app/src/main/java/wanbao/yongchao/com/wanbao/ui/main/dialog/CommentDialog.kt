package wanbao.yongchao.com.wanbao.ui.main.dialog

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.dialog_community_comment.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class CommentDialog(val comment:Comment, val avatar:String, val name:String, val isMine:Boolean): BaseDialogFragment() {
    override fun setLayoutID(): Int = R.layout.dialog_community_comment

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        v2.visibility= View.GONE
        v3.visibility= View.GONE
        tv_3.visibility= View.GONE
        tv_4.visibility= View.GONE
        ImageLoad.setUserHead(avatar,iv_header)
        tv_name.text=name
        if (isMine){
            v1.visibility= View.GONE
            tv_2.visibility= View.GONE
            tv_1.text="删除评论"
        }
    }

    override fun clickListener() {

        Click.viewClick(dialogOver).subscribe { dismiss() }

        Click.viewClick(shareOff).subscribe {
            dismiss()
        }

        Click.viewClick(tv_1).subscribe {
            comment.setChat()
            dismiss()
        }

        Click.viewClick(tv_2).subscribe {
            comment.setReport()
            dismiss()
        }


    }

    interface Comment{
        fun setChat()
        fun setReport()
    }
}