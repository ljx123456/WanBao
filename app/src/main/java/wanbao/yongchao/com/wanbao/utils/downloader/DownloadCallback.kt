package com.ycwl.servebixin.cn.utils.downloader

/**
 * Created by Administrator on 2018/3/2 0002.
 */
interface  DownloadCallback{
     fun downloadSucceed(file:String)

     fun downloadProgress(progress: Int)

     fun downloadError(e: Throwable?) {
    }
}