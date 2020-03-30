package wanbao.yongchao.com.wanbao.ui.main.dialog

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.dialog_community_comment.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class CommunityCommentDialog(val comment:Comment,val avatar:String,val name:String,val isMine:Boolean):BaseDialogFragment() {
    override fun setLayoutID(): Int = R.layout.dialog_community_comment

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        ImageLoad.setUserHead(avatar,iv_header)
        tv_name.text=name
        if (isMine){
            v3.visibility= View.GONE
            tv_4.visibility=View.GONE
            tv_3.text="删除评论"
        }
    }

    override fun clickListener() {

        Click.viewClick(dialogOver).subscribe { dismiss() }

        Click.viewClick(shareOff).subscribe {
            dismiss()
        }

        Click.viewClick(tv_1).subscribe {
            comment.setReply()
            dismiss()
        }

        Click.viewClick(tv_2).subscribe {
            comment.setCopy()
            dismiss()
        }

        Click.viewClick(tv_3).subscribe {
            comment.setChat()
            dismiss()
        }

        Click.viewClick(tv_4).subscribe {
            comment.setReport()
            dismiss()
        }
    }

    interface Comment{
        fun setReply()
        fun setCopy()
        fun setChat()
        fun setReport()
    }
}