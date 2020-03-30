package wanbao.yongchao.com.wanbao.utils.utils

/**
 * Created by Administrator on 2018/2/1 0001.
 */
object FastDoubleClick {

    private val MIN_CLICK_DELAY_TIME = 1000
    private var lastClickTime: Long = 0

    fun isFastDoubleClick(): Boolean {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            false
        } else {
            true
        }
    }
}