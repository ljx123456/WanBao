package wanbao.yongchao.com.wanbao.ui.mine.activity

import android.view.View
import kotlinx.android.synthetic.main.activity_business_auth_center.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthInfoBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthInfoBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.UserAuthCenterPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.UserAuthCenterView
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click

class BusinessAuthCenterActivity: BaseActivity(), UserAuthCenterView{
    override fun getAuthInfoRequest(data: AuthInfoBean) {
        if (data.data!=null&&data.data.createTime!=null&&data.data.createTime!=""){
            if (data.data.authType==3){//用户认证类型：1 实名认证，2 行业大V认证，3 商家认证
                layout_business_default.visibility= View.GONE
                layout_business.visibility= View.VISIBLE
                tv_expired.visibility= View.GONE
                if (data.data.auditState==0){//审核状态：0 未通过，1 已通过，2 审核中，3 已过期
                    ImageLoad.setImage(data.data.idCardBack,iv_business)
                    tv_business_auth.text="实名认证未通过"
                    Click.viewClick(layout_business).subscribe {
                        intentUtils.intentAuthDetails()
                    }
                }else if (data.data.auditState==1){
                    ImageLoad.setImage(data.data.idCardBack,iv_business)
                    tv_business_auth.text="认证已完成"

                }else if (data.data.auditState==2){
                    ImageLoad.setImage(data.data.idCardBack,iv_business)
                    tv_business_auth.text="认证审核中"
                    Click.viewClick(layout_business).subscribe {
                        intentUtils.intentAuthDetails()
                    }
                }else if (data.data.auditState==3){
                    ImageLoad.setImage(data.data.idCardBack,iv_business)
                    tv_business_auth.text="更新认证资料"
                    tv_expired.visibility= View.VISIBLE
                    Click.viewClick(layout_business).subscribe {
                        intentUtils.intentBusinessAuthRealName()
                    }
                }



            }

        }else{
            Click.viewClick(layout_business_default).subscribe {
                intentUtils.intentBusinessAuthRealName()
            }

        }
    }

    private val presenter by lazy { UserAuthCenterPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_business_auth_center

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

    }

    override fun clickListener() {
        Click.viewClick(back).subscribe { finish() }

    }

    override fun onResume() {
        super.onResume()
        var user = GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null) {
            var info = user.loadAll()
            if (info != null && info.size > 0) {
                presenter.getAuthInfo(AuthInfoBody(info.get(0).token))
            }
        }

    }
}