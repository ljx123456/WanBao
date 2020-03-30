package wanbao.yongchao.com.wanbao.utils

import android.app.Activity
import android.os.Environment
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader

/**
 * Created by Administrator on 2019/1/8 0008.
 */
class VersionDownloader(private val callBack: FileDownloaderCallBack, val activity: Activity) : FileDownloadListener() {

    //文件名
    var saveFileName: String = ""
    //下载包安装路径
    var savePath = Environment.getExternalStorageDirectory().absolutePath + "/Download/"


    private var downId: Int = 0


    fun fileDownloader(fileUrl: String?, version: String) {
        if (!fileUrl.isNullOrBlank()) {
            saveFileName = "$savePath$version.apk"
            downId = FileDownloader.getImpl()
                    .create(fileUrl)
                    .setPath(saveFileName)
                    .setListener(this)
                    .setForceReDownload(true).start()
        }
    }


    override fun completed(task: BaseDownloadTask?) {
        callBack.completed(saveFileName)
    }

    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
        callBack.onPending("0%")
    }


    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
        val percent = soFarBytes / totalBytes.toFloat()
        callBack.onProgress((percent * 100).toInt())

    }


    override fun warn(task: BaseDownloadTask?) {}

    override fun error(task: BaseDownloadTask?, e: Throwable?) {}

    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {}

    interface FileDownloaderCallBack {

        fun onProgress(progress: Int)

        fun onPending(string: String)

        fun completed(file: String)
    }

}