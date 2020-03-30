package wanbao.yongchao.com.wanbao.ui.image

import android.annotation.SuppressLint
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

//import com.ycwl.servebixin.cn.R
//import com.ycwl.servebixin.cn.base.BaseActivity
//import com.ycwl.servebixin.cn.utils.image.ImageLoad
//import com.ycwl.servebixin.cn.utils.intent.intentUtils.intentVideo
import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.utils.Click


/**
 * Created by Administrator on 2018/8/20 0020.
 */
class ImageBrowseAdapter(val list: MutableList<ImageBrowseInfo>) : PagerAdapter(), PhotoViewAttacher.OnPhotoTapListener {

    private var mChildCount = 0


    override fun notifyDataSetChanged() {
        mChildCount = count
        super.notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        if (mChildCount > 0) {
            mChildCount--
            return PagerAdapter.POSITION_NONE
        }
        return super.getItemPosition(`object`)
    }


    override
    fun getCount(): Int = list.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    @SuppressLint("WrongViewCast")
    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val layoutView = LayoutInflater.from(view.context).inflate(R.layout.item_image_browse, view, false)
        val imageView = layoutView.findViewById<PhotoView>(R.id.item_image)
        val video = layoutView.findViewById<ImageView>(R.id.itemVideo)
        if (list.get(position).id == 1) {
            video.visibility = View.VISIBLE
            Click.viewClick(video).subscribe {
//                intentVideo(list[position].openUrl)
            }
        } else {
            video.visibility = View.GONE
        }
        ImageLoad.setImage(list[position].openUrl, imageView)
        imageView.setOnPhotoTapListener(this)
        view.addView(layoutView)
        return layoutView
    }


    override fun onPhotoTap(view: View, x: Float, y: Float) {
        (view.context as BaseActivity).finish()
    }




}