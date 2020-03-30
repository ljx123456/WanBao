package wanbao.yongchao.com.wanbao.ui.set.dialog

import android.content.Context
import android.view.View
import com.blankj.utilcode.util.AppUtils
import kotlinx.android.synthetic.main.dialog_version_updating.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.UpdateBean
import wanbao.yongchao.com.wanbao.utils.VersionDownloader
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions.userSD
import wanbao.yongchao.com.wanbao.utils.utils.Click

class VersionUpdatingDialog : BaseDialogFragment(), UserPermissions.MemoryReadPermissionsFace, VersionDownloader.FileDownloaderCallBack {
    override fun requestPermissionsFaceError() {

    }

    override fun isFullScreen(): Boolean = true

    //开始下载
    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        startFileDownloaderLayout()
        versionDownloader.fileDownloader(auditStateBean.data.url, "${auditStateBean.data.versionStr}")
    }

    private val STATETEXT = "安装"
    private val versionDownloader by lazy { VersionDownloader(this, activity!!) }


    private lateinit var auditStateBean: UpdateBean
    private lateinit var versionUpdatingCallBack: VersionUpdatingCallBack


    override fun setLayoutID(): Int = R.layout.dialog_version_updating

    override fun setLayoutData() {
        setLayoutView()
        judgeUpdating()
    }

    //渲染UI页面
    private fun setLayoutView() {
//        if (auditStateBean.data.description!=null&&auditStateBean.data.description.size>0) {
//            auditStateBean.data.description.forEachIndexed { index, s ->
//                if (index == 0) {
//                    content.text = "${index + 1}、" + s
//                } else {
//                    content.text = "${content.text}\n" + "${index + 1}、" + s
//                }
//            }
//        }else{
//            content.text="无"
//        }
        if (auditStateBean.data.description!=null&&auditStateBean.data.description!="") {
            content.text=auditStateBean.data.description
        }else{
            content.text="无"
        }
//        content.text = auditStateBean.data.packageUrl
        titleVersion.text = auditStateBean.data.versionStr
    }

    //判断是否强制更新
    private fun judgeUpdating() {
        if (auditStateBean.data.isForce == 1) {
            this.isCancelable=false
            constraintUpdating()
        }
        else {
            this.isCancelable=true
            closableUpdating()
        }
    }

    //可选更新
    private fun closableUpdating() {
        dismissBtn.visibility = View.VISIBLE
    }


    //强制更新
    private fun constraintUpdating() {
        dismissBtn.visibility = View.GONE
    }


    override fun clickListener() {
        Click.viewClick(updatingBtn).subscribe { updateClick() }
        Click.viewClick(dismissBtn).subscribe { dismissBtnClick() }
    }

    //取消更新
    private fun dismissBtnClick() {
        versionUpdatingCallBack.enterInto()
        dismiss()
    }


    //开始更新
    private fun updateClick() {
//        updatingBtn.visibility = View.GONE
        val content = updatingBtn.text.toString()
        if (content != STATETEXT) startUpdating()
        else AppUtils.installApp(versionDownloader.saveFileName, AppUtils.getAppPackageName() + ".fileprovider")
    }


    //开始下载更新
    private fun startUpdating() {
        userSD(context!!, this)
    }


    //显示更新的UI
    private fun startFileDownloaderLayout() {
        updatingBtn.visibility = View.GONE
        progressbarLayout.visibility = View.VISIBLE
    }


    //下载进度
    override fun onProgress(progress: Int) {
        progressbar.progress = progress
        progressText.text = progress.toString() + "%"
    }

    //开始下载
    override fun onPending(string: String) {
        progressText.text = string
    }


    //下载结束
    override fun completed(file: String) {
        updatingBtn.visibility = View.VISIBLE
        progressbarLayout.visibility = View.GONE
        updatingBtn.text = STATETEXT
        setCompletedUi()
        AppUtils.installApp(versionDownloader.saveFileName, AppUtils.getAppPackageName() + ".fileprovider")
    }


    //设置下载完成UI
    private fun setCompletedUi() {
        progressbar.progress = 100
        progressText.text = "100%"
        updatingBtn.text = STATETEXT
        updatingBtn.visibility = View.VISIBLE
    }


    interface VersionUpdatingCallBack {
        fun enterInto()
    }


    fun setDialogContent(auditStateBean: UpdateBean, versionUpdatingCallBack: VersionUpdatingCallBack) {
        this.auditStateBean = auditStateBean
        this.versionUpdatingCallBack = versionUpdatingCallBack
    }


}