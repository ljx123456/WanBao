package com.ycwl.servebixin.cn.utils.downloader

import android.content.Context
import com.ycwl.servebixin.cn.utils.downloader.DownloadListener

import com.liulishuo.filedownloader.FileDownloader
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions
import wanbao.yongchao.com.wanbao.utils.utils.FileUtils
import wanbao.yongchao.com.wanbao.utils.utils.SpHelp
import wanbao.yongchao.com.wanbao.utils.utils.SpHelp.file_progress

/**
 * Created by Administrator on 2018/3/2 0002.
 * 下载文件专用
 */
class DownloaderFile : DownloadListener, UserPermissions.MemoryReadPermissionsFace {
    override fun requestPermissionsFaceError() {

    }


    private var context: Context
    private var fileName: String? = null
    private var downloaderUrl: String? = null
    private var downloadCallback: DownloadCallback? = null

    constructor(context: Context) : super() {
        this.context = context
    }

    fun startDownload(downloaderUrl: String, fileName: String, downloadCallback: DownloadCallback) {
        this.fileName = fileName
        this.downloaderUrl = downloaderUrl
        this.downloadCallback = downloadCallback
        UserPermissions.userSD(context, this)
    }

    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        FileDownloader.getImpl().create(downloaderUrl).
                setPath(FileUtils.createFile(fileName!!).absolutePath).setListener(this).start()
    }


    //是否满足重连条件
    fun reconnection(): Boolean {
        return baseDownloadTask?.status!!.toInt() == -1
    }

    override fun downloadSucceed(file: String) {
        downloadCallback?.downloadSucceed(file)
    }

    override fun downloadProgress(progress: Int) {
        SpHelp.putInt(file_progress, progress)
        downloadCallback?.downloadProgress(progress)
    }

    override fun downloadError(e: Throwable?) {
        downloadCallback?.downloadError(e)
    }

    override fun downloadPaused() {

    }

    fun onDestroy() {
        baseDownloadTask?.pause()
    }

}