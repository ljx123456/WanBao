package wanbao.yongchao.com.wanbao.ui.set.activity

import android.content.DialogInterface
import android.os.Handler
import android.util.Log
import cn.jpush.im.android.api.JMessageClient
import com.blankj.utilcode.util.AppUtils
import kotlinx.android.synthetic.main.activity_set.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.DBUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean
import wanbao.yongchao.com.wanbao.ui.login.utils.TagsUtils
import wanbao.yongchao.com.wanbao.ui.set.dialog.VersionUpdatingDialog
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.LogoutBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateBean
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.LogoutBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.UpdateBody
import wanbao.yongchao.com.wanbao.ui.set.mvp.presenter.LogoutPresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.presenter.SplashPresenter
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.LogoutView
import wanbao.yongchao.com.wanbao.ui.set.mvp.view.SplashView
import wanbao.yongchao.com.wanbao.utils.dialog.LoadDialog
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.DataCleanManager
import wanbao.yongchao.com.wanbao.utils.utils.GlideCacheUtil
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class SetActivity:BaseActivity(),LogoutView,SplashView, VersionUpdatingDialog.VersionUpdatingCallBack {
    override fun enterInto() {

    }

    override fun getUpdateRequest(data: UpdateBean) {
        if (AppUtils.getAppVersionCode()!=data.data.version) {
            updatingdialog.setDialogContent(data, this)
            updatingdialog.show(supportFragmentManager, "")
        }else{
            Toast.Tips("已是最新版本")
        }
    }

    override fun getUpdateError(code: Int, message: String) {
        Toast.Tips(message)
    }

    override fun getLogoutRequest(data: LogoutBean) {
        user.setWxCode("")
        intentUtils.intentLogin()
        DBUtils.DelUser()
        DBUtils.DelBusiness()
        TagsUtils.setTagList(ArrayList<TagsBean.DataBean>())
        user.setChange(true)
        user.setOut(true)
        user.setFlag(false)
        try {
            JMessageClient.logout()
            Log.e("测试","退出登录")
        }catch (e:Exception){}

    }

    private val presenter by lazy { LogoutPresenter(this,this,this) }
    private val updataPresenter by lazy { SplashPresenter(this, this, this) }
    private val updatingdialog = VersionUpdatingDialog()

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_set

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        tv_cache_size.text= GlideCacheUtil.getInstance().getCacheSize(this)
    }

    override fun clickListener() {
        Click.viewClick(back).subscribe { finish() }

        Click.viewClick(tv_account).subscribe {
            intentUtils.intentAccount()
        }

        Click.viewClick(layout_cache).subscribe {
            ShowDialog.showCustomDialogNoTitle(this, "确定清空晚豹本地的缓存记录？（包括图片、视频、聊天记录等）", "清空", "取消", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            dialog.dismiss()
                            LoadDialog.show(this@SetActivity)
                            Handler().postDelayed(object :Runnable{
                                override fun run() {
                                    LoadDialog.dismiss(this@SetActivity)
                                    tv_cache_size.text= "0.0 M"
                                }
                            },500)
                            GlideCacheUtil.getInstance().clearImageAllCache(this@SetActivity)//清除图片所有缓存
                            DataCleanManager.cleanInternalCache(mContext)//清除本应用内部缓存
                            DataCleanManager.cleanFiles(mContext)//清除/data/data/com.xxx.xxx/files下的内容
                            DataCleanManager.cleanExternalCache(mContext)//清除外部cache下的内容
//        set_clearawaySize.text = getTotalCacheSize(this)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
            })
        }


        Click.viewClick(tv_blacklist).subscribe {
            intentUtils.intentBlacklist()
        }

        Click.viewClick(tv_notice).subscribe {
            intentUtils.intentNoticeSet()
        }

        Click.viewClick(tv_about).subscribe {
            intentUtils.intentAbout()
        }

        Click.viewClick(tv_suggest).subscribe {
            intentUtils.intentSuggest()
        }

        Click.viewClick(tv_out).subscribe {
            var user = GreenDaoHelper.getDaoSessions().userDBDao
            var business= GreenDaoHelper.getDaoSessions().businessDBDao
            if (user != null||business!=null) {
                var info = user.loadAll()
                var inf = business.loadAll()
                if (info != null && info.size > 0) {
                    presenter.getLogout(LogoutBody(info.get(0).token))
                }else if (inf != null && inf.size > 0) {
                    presenter.getLogout(LogoutBody(inf.get(0).token))
                }
            }

        }


        Click.viewClick(tv_agreement).subscribe {
            intentUtils.intentAgreement("用户协议")
        }

        Click.viewClick(tv_policy).subscribe {
            intentUtils.intentAgreement("隐私协议")
        }

        Click.viewClick(tv_update).subscribe {
            updataPresenter.getUpdata(UpdateBody("1"))
        }

    }
}