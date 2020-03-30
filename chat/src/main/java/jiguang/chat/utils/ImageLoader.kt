package jiguang.chat.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import picker.prim.com.primpicker_core.engine.ImageEngine

class ImageLoader : ImageEngine {
    override fun loadImageThumbnail(context: Context, resize: Int, placeholder: Drawable?, view: ImageView, uri: Uri) {
        Glide.with(context).asBitmap().load(uri).placeholder(placeholder).override(resize, resize).centerCrop().into(view)
    }

    override fun loadImage(context: Context, resizeX: Int, resizeY: Int, placeholder: Drawable?, view: ImageView, uri: Uri) {
        Glide.with(context).asBitmap().load(uri).placeholder(placeholder).override(resizeX, resizeY).fitCenter().into(view)
    }

    override fun loadGifThumbnail(context: Context, resize: Int, placeholder: Drawable?, view: ImageView, uri: Uri) {
        Glide.with(context).asGif().load(uri).placeholder(placeholder).override(resize, resize).centerCrop().into(view)
    }

    override fun loadGifImage(context: Context, resizeX: Int, resizeY: Int, placeholder: Drawable?, view: ImageView, uri: Uri) {
        Glide.with(context).asGif().load(uri).placeholder(placeholder).override(resizeX, resizeY).into(view)
    }

}