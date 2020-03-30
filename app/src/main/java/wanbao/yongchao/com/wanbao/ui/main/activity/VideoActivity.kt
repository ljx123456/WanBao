package wanbao.yongchao.com.wanbao.ui.main.activity

import android.os.Bundle
import cn.jzvd.JZMediaSystem
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd

import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.activity_video.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.SystemUtils.activityFullScreen

class VideoActivity : BaseActivity() {
    override fun openEventBus(): Boolean = false
    override fun getActivityLayout(): Int = R.layout.activity_video

    override fun setActivityTitle() {
        setTheme(R.style.VideoTheme_NoActionBar)
        activityFullScreen(this)
    }

    override fun initActivityData() {
        
        val path = intent.getStringExtra("video")
        Glide.with(this).load(path).into(videoplayer.thumbImageView)
//        videoplayer.setUp(path, "", Jzvd.SCREEN_WINDOW_NORMAL)
        videoplayer.setUp(path, "", Jzvd.SCREEN_NORMAL)
        videoplayer.startButton.performClick()
//        JzvdStd.startFullscreen(this,JzvdStd::class.java,path,"视频")
        videoplayer.startVideo()
    }

    override fun clickListener() {
        Click.viewClick(videoOver).subscribe { finish() }
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val decorView = window.decorView
//            val option = ( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//            decorView.systemUiVisibility = option
//            window.navigationBarColor = Color.TRANSPARENT
//            window.statusBarColor = Color.TRANSPARENT
//        }
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
//        Jzvd.releaseAllVideos()
        JzvdStd.goOnPlayOnPause()
    }

    override fun onResume() {
        super.onResume()
        JzvdStd.goOnPlayOnResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        Jzvd.releaseAllVideos()
    }

}
