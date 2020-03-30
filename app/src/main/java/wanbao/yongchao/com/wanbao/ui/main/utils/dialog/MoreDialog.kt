package wanbao.yongchao.com.wanbao.ui.main.utils.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import kotlinx.android.synthetic.main.dialog_more.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class MoreDialog(val more: More,val mContext: Context,val flag: Boolean,val isCollect: Boolean) : BaseDialogFragment() {

    override fun setLayoutID(): Int = R.layout.dialog_more

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
//        setLocation(Gravity.BOTTOM)
        if (flag){
            tv_report.visibility=View.GONE
            tv_collect.visibility= View.GONE
            tv_del.visibility=View.VISIBLE
        }else{
            tv_report.visibility=View.VISIBLE
            tv_collect.visibility= View.VISIBLE
            tv_del.visibility=View.GONE
        }

        if (isCollect){
            tv_collect.text="取消收藏"
            var draw = mContext.resources.getDrawable(R.mipmap.share_button_collection_moveout)
            draw.setBounds(0, 0, draw.minimumWidth, draw.minimumHeight)
            tv_collect.setCompoundDrawables(null,draw,null, null)
        }else{
            tv_collect.text="收藏"
            var draw = mContext.resources.getDrawable(R.mipmap.share_button_collection)
            draw.setBounds(0, 0, draw.minimumWidth, draw.minimumHeight)
            tv_collect.setCompoundDrawables(null,draw,null, null)
        }

    }

    override fun clickListener() {
        Click.viewClick(shareOff).subscribe { dismiss() }
        //微信分享
        Click.viewClick(shareWX).subscribe {
            more.setMoreShareWX()
            dismiss()
        }
        //朋友圈分享
        Click.viewClick(sharePYQ).subscribe {
            more.setMoreSharePYQ()
            dismiss()
        }

        //QQ分享
        Click.viewClick(shareQQ).subscribe {
           more.setMoreShareQQ()
            dismiss()
        }

        //举报
        Click.viewClick(tv_report).subscribe {
            more.setMoreReport()
            dismiss()
        }

        //收藏
        Click.viewClick(tv_collect).subscribe {
            more.setMoreCollect()
            dismiss()
        }

        //不感兴趣
        Click.viewClick(tv_uninterested).subscribe {
            more.setMoreHate()
            dismiss()
        }

        //删除
        Click.viewClick(tv_del).subscribe {
            more.setMoreDel()
            dismiss()
        }
    }


    interface More {
        fun setMoreShareWX()
        fun setMoreSharePYQ()
        fun setMoreShareQQ()

        fun setMoreReport()
        fun setMoreCollect()
        fun setMoreHate()

        fun setMoreDel()
    }
}