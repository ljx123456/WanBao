package wanbao.yongchao.com.wanbao.ui.main.dialog

import android.annotation.SuppressLint
import android.view.View
import cn.jiguang.dy.Protocol.mContext
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_more.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class HomeMoreDialog(val more:More,val isBlank:Boolean,val isMine:Boolean): BaseDialogFragment() {

    override fun setLayoutID(): Int = R.layout.dialog_more

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
//        setLocation(Gravity.BOTTOM)
        tv_report.visibility= View.VISIBLE
        tv_collect.visibility= View.VISIBLE
        tv_del.visibility= View.GONE
        tv_uninterested.visibility=View.INVISIBLE

        if (isMine){
            layout_other.visibility=View.GONE
        }else{
            layout_other.visibility=View.VISIBLE
        }

        if (isBlank){
            tv_collect.text="移出黑名单"
            var draw = mContext.resources.getDrawable(R.mipmap.share_button_blacklist_moveout)
            draw.setBounds(0, 0, draw.minimumWidth, draw.minimumHeight)
            tv_collect.setCompoundDrawables(null,draw,null, null)
        }else{
            tv_collect.text="拉黑"
            var draw = mContext.resources.getDrawable(R.mipmap.share_button_blacklist)
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

        //拉黑
        Click.viewClick(tv_collect).subscribe {
            more.setMoreCollect()
            dismiss()
        }




    }


    interface More {
        fun setMoreShareWX()
        fun setMoreSharePYQ()
        fun setMoreShareQQ()

        fun setMoreReport()
        fun setMoreCollect()



    }
}