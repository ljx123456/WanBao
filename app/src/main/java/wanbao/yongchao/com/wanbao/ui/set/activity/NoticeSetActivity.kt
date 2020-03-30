package wanbao.yongchao.com.wanbao.ui.set.activity

import android.content.Context
import android.support.v4.app.NotificationManagerCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_notice_set.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.NoticeStatusBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.ChangeNoticeStatusBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.NoticeStatusBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.presenter.NoticeStatusPresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.NoticeStatusView
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions

import wanbao.yongchao.com.wanbao.utils.utils.Click

class NoticeSetActivity:BaseActivity(),NoticeStatusView {
    override fun getChangeNoticeStatusRequest() {

    }

    override fun getChangeNoticeStatusError() {
        if (type==1) {
            switch_like.isChecked = !flag
        }else if (type==2){
            switch_comment.isChecked = !flag
        }else if (type==3){
            switch_focus.isChecked = !flag
        }else if (type==4){
            switch_at.isChecked = !flag
        }
    }

    override fun getNoticeStatusRequest(data: NoticeStatusBean) {
        switch_like.isChecked=data.data.isMessageLike
        switch_comment.isChecked=data.data.isMessageComment
        switch_focus.isChecked=data.data.isMessageFocus
        switch_at.isChecked=data.data.isMessageAtMe

        switch_like.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed){
                type=1
                flag=isChecked
                presenter.getChangeNoticeStatus(ChangeNoticeStatusBody(type.toString()))
            }
        }

        switch_comment.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed){
                type=2
                flag=isChecked
                presenter.getChangeNoticeStatus(ChangeNoticeStatusBody(type.toString()))
            }
        }

        switch_focus.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed){
                type=3
                flag=isChecked
                presenter.getChangeNoticeStatus(ChangeNoticeStatusBody(type.toString()))
            }
        }

        switch_at.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed){
                type=4
                flag=isChecked
                presenter.getChangeNoticeStatus(ChangeNoticeStatusBody(type.toString()))
            }
        }

    }

    private val presenter by lazy { NoticeStatusPresenter(this,this,this) }
    private var type=1
    private var flag=false

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_notice_set

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }

        Click.viewClick(layout_notice).subscribe {
            UserPermissions.gotoSet(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isNotificationEnabled(this)){
            tv_notice.text="已打开"
            layout_like_mask.visibility= View.GONE
        }else{
            tv_notice.text="已关闭"
            layout_like_mask.visibility= View.VISIBLE
        }
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
//                token=info.get(0).token
                presenter.getNoticeStatus(NoticeStatusBody(info.get(0).token))
            }else if (inf != null && inf.size > 0) {
//                token=info.get(0).token
                presenter.getNoticeStatus(NoticeStatusBody(inf.get(0).token))
            }
        }
    }

    private fun isNotificationEnabled(context: Context):Boolean {
        var isOpened = false
        try {
            isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled()
        } catch (e:Exception ) {
            e.printStackTrace()
            isOpened = false
        }
        return isOpened

    }
}