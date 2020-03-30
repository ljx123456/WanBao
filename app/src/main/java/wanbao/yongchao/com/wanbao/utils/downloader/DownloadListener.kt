package com.ycwl.servebixin.cn.utils.downloader

import android.util.Log
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener

/**
 * Created by Administrator on 2018/3/2 0002.
 */
abstract class DownloadListener : FileDownloadListener() {

    protected open var baseDownloadTask: BaseDownloadTask? = null

    //在下载队列中(正在等待/正在下载)已经存在相同下载连接与相同存储路径的任务
    override fun warn(task: BaseDownloadTask?) {
        baseDownloadTask = task
    }

    //完成整个下载过程
    override fun completed(task: BaseDownloadTask?) {
        baseDownloadTask = task
        downloadSucceed(baseDownloadTask!!.path)
    }

    //等待，已经进入下载队列
    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
        baseDownloadTask = task
    }

    // 下载出现错误
    override fun error(task: BaseDownloadTask?, e: Throwable?) {
        baseDownloadTask = task
    }

    //下载进度回调
    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
        val percent = soFarBytes / totalBytes.toFloat()
        downloadProgress((percent * 100 + 1).toInt())
        baseDownloadTask = task
    }

    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
        baseDownloadTask = task
    }

    abstract fun downloadSucceed(path: String)

    abstract fun downloadProgress(progress: Int)

    abstract fun downloadError(e: Throwable?)

    abstract fun downloadPaused()

}