package wanbao.yongchao.com.wanbao.utils.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.muzhi.camerasdk.utils.FileUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Administrator on 2018/2/1 0001.
 */
object FileUtils {


    /**
     * 创建图片文件
     * @param paperFile：文件夹
     * @param fileName： 文件名
     */
    private fun createTmpFile(): File {
        val paperFile = ".bixin_image"
        val fileName = "avatar" + ".jpg"
        val dir = File(Environment.getExternalStorageDirectory().path, paperFile)
        if (!dir.exists()) dir.mkdirs()
        return File(dir, fileName)
    }

    /**
     * 创建文件
     * @param paperFile：文件夹
     * @param fileName： 文件名
     */
    fun createFile(fileName: String): File {
        val paperFile = ".bixin_download"
        val dir = File(Environment.getExternalStorageDirectory().path, paperFile)
        if (!dir.exists()) dir.mkdirs()
        return File(dir, fileName)
    }


    //保存图片
    fun saveImage(bitmap: Bitmap): String {
        val file = createTmpFile()
        val bos = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        getContext().sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.absolutePath)))
        bos.flush()
        return file.absolutePath
    }

    //保存图片
    fun saveImageFile(bitmap: Bitmap): File {
        val file = createTmpFile()
        val bos = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        getContext().sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.absolutePath)))
        bos.flush()
        return file
    }
}