package wanbao.yongchao.com.wanbao.utils.image

import android.graphics.Bitmap

/**
 * Created by Administrator on 2018/3/1 0001.
 */
interface  ImageLoadCall{

    fun onResourceReady(url: String, bitmap: Bitmap)

    fun onLoadFailed()
}