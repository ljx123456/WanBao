package wanbao.yongchao.com.wanbao.utils.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
//import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget

import java.lang.Exception

/**
 * Created by Administrator on 2018/3/1 0001.
 * 加载自定义尺寸的普通图片
 */
class ImageCustom {

//    fun setImageSize(url: String, width: Int, height: Int, image: ImageView) {
//        Glide.with(getContext()).load(url).asBitmap().placeholder(ImageConfiguration.imagePlaceholder)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE).error(ImageConfiguration.imageError).into(image)
//    }
//
//
//    fun setImageSizeCall(url: String, width: Int, height: Int, imageCall: ImageLoadCall) {
//        Glide.with(getContext()).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).override(width, height)
//                .placeholder(ImageConfiguration.userHeadPlaceholder).error(ImageConfiguration.userHeadError).into(object : SimpleTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
//                        if (imageCall != null) imageCall?.onResourceReady(url, resource!!)
//                    }
//
//                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
//                        super.onLoadFailed(e, errorDrawable)
//                        if (imageCall != null) imageCall?.onLoadFailed()
//                    }
//                })
//    }
}