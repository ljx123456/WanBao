package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.view.View
import kotlinx.android.synthetic.main.activity_auth_details.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.UserAuthCenterPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.UserAuthCenterView
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click

class AuthDetailsActivity:BaseActivity(),UserAuthCenterView {
    override fun getAuthInfoRequest(data: AuthInfoBean) {
        if (data.data.authType==1){//用户认证类型：1 实名认证，2 行业大V认证，3 商家认证
            apply_details_des1.text="已提交实名认证资料"
        }else if(data.data.authType==2){
            apply_details_des1.text="已提交达人认证资料"
        }else{
            apply_details_des1.text="已提交商家认证资料"
        }

        if (data.data.auditState==0){//审核状态：0 未通过，1 已通过，2 审核中，3 已过期
            apply_details_view2.visibility= View.VISIBLE
            apply_details_time3.visibility= View.VISIBLE
            apply_details_img3.visibility= View.VISIBLE
            apply_details_content3.visibility= View.VISIBLE
            apply_details_des3.visibility= View.VISIBLE
            btnApplyAgain.visibility= View.VISIBLE

            apply_details_time1.text=data.data.createTime.substring(0,data.data.createTime.length-3)
            apply_details_time2.text=data.data.createTime.substring(0,data.data.createTime.length-3)
            apply_details_time3.text=data.data.refuseTime.substring(0,data.data.refuseTime.length-3)

            Click.viewClick(btnApplyAgain).subscribe {
                if(data.data.authType==1){
                    intentUtils.intentAuthRealName()
                    finish()
                }else if (data.data.authType==2){
                    intentUtils.intentAuthBloggerRealName()
                    finish()
                }else{
                    intentUtils.intentBusinessAuthRealName()
                    finish()
                }
            }
        }else {
            apply_details_view2.visibility= View.GONE
            apply_details_time3.visibility= View.GONE
            apply_details_img3.visibility= View.GONE
            apply_details_content3.visibility= View.GONE
            apply_details_des3.visibility= View.GONE
            btnApplyAgain.visibility= View.GONE
            apply_details_time1.text=data.data.createTime.substring(0,data.data.createTime.length-3)
            apply_details_time2.text=data.data.createTime.substring(0,data.data.createTime.length-3)
        }
    }

    private val presenter by lazy { UserAuthCenterPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_auth_details

    override fun setActivityTitle() {
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business=GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                presenter.getAuthInfo(AuthInfoBody(info.get(0).token))
            }else if (inf != null && inf.size > 0) {
                presenter.getAuthInfo(AuthInfoBody(inf.get(0).token))
            }
        }

    }

    override fun initActivityData() {

    }

    override fun clickListener() {
        Click.viewClick(back).subscribe {
            finish()
        }
    }
}