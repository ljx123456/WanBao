package wanbao.yongchao.com.wanbao.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext
import wanbao.yongchao.com.wanbao.utils.image.ImageConfiguration.imagePlaceholder
import wanbao.yongchao.com.wanbao.utils.image.ImageConfiguration.userHeadPlaceholder
import wanbao.yongchao.com.wanbao.utils.mask.UtilBitmap
import wanbao.yongchao.com.wanbao.view.NineGrid.ImageLoaderUtil


/**
 * Created by Administrator on 2018/3/1 0001.
 * 加载普通图片
 */
object ImageLoad {


    //加载一般图片
    fun setImage(url: String, image: ImageView) {
        Glide.with(getContext())
                .load(url)
                .placeholder(imagePlaceholder)
                .dontAnimate()
//                .diskCacheStrategy(DiskCacheStrategy.)
                .into(image)
    }

    //无占位图加载
    fun setImageNull(url: String, image: ImageView) {
        Glide.with(getContext())
                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image)
    }

    //加载本地资源
    fun setResourceImage(id: Int, image: ImageView) {
        Glide.with(getContext()).load(id).dontAnimate().into(image)
    }


    //加载头像
    fun setUserHead(url: String, image: ImageView) {
        if (url != null && !url.equals("")) {
            Glide.with(getContext())
                    .load(url)
                    .placeholder(userHeadPlaceholder)
                    .dontAnimate()
                    .error(userHeadPlaceholder)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(image)
        } else
            Glide.with(getContext())
                    .load(url)
                    .placeholder(userHeadPlaceholder)
                    .dontAnimate()
                    .error(userHeadPlaceholder)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(image)
    }

    //加载一般图片(宽度固定，高度等比例缩放)
    fun setImageHeight(url: String, image: ImageView) {
        ImageLoaderUtil.displayImage(getContext(), image, url, ImageLoaderUtil.getPhotoImageOption(), object : ImageLoadingListener {
            override fun onLoadingStarted(imageUri: String, view: View) {

            }

            override fun onLoadingFailed(imageUri: String, view: View, failReason: FailReason) {

            }

            override fun onLoadingComplete(imageUri: String, view: View, bitmap: Bitmap) {

                val w = bitmap.width
                val h = bitmap.height

//                val newW: Int
                var newH=0
              if (image.width>bitmap.width){
                  newH=h * image.width / w
              }else{
                  newH=h * w / image.width
              }

//                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
//                    newW = parentWidth / 2
//                    newH = newW * 5 / 3
//                } else if (h < w) {//h:w = 2:3
//                    newW = parentWidth * 2 / 3
//                    newH = newW * 2 / 3
//                } else {//newH:h = newW :w
//                    newW = parentWidth / 2
//                    newH = h * newW / w
//                }
//                setOneImageLayoutParams(imageView, newW, newH)
                var par=image.layoutParams
                par.height=newH
                image.setLayoutParams(par)

//                Glide.with(getContext())
//                        .load(url)
//                        .placeholder(imagePlaceholder)
//                        .dontAnimate()
////                .diskCacheStrategy(DiskCacheStrategy.)
//                        .into(image)

            }

            override fun onLoadingCancelled(imageUri: String, view: View) {

            }
        })

    }

    //高斯模糊
    fun setMaskImage(url: String, image: ImageView,context: Context) {
        Glide.with(getContext())
                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(object :SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        image.setImageDrawable(resource)
                        UtilBitmap().blurImageView(context,image,25f)
                    }
                })

    }



}