package wanbao.yongchao.com.wanbao.ui.login.utils

import android.graphics.Color
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

class CodeTime {

    private val startTime: Long = 1000
    private val endTimer = startTime * 60
    private var countDownTimer: CountDownTimer? = null


    fun codeCountTimer(codeView: Button) {
        countDownTimer = object : CountDownTimer(endTimer, startTime) {
            override fun onFinish() {
                codeView.isEnabled = true
                codeView.text = "重新获取"
                codeView.setTextColor(Color.parseColor("#ffffff"))
            }

            override fun onTick(millisUntilFinished: Long) {
                codeView.isEnabled = false
                codeView.text = "重新获取（ "+(millisUntilFinished / 1000).toString()+"s）"
                codeView.setTextColor(Color.parseColor("#40ffffff"))
            }
        }.start()
    }

    fun onDestroy() {
        countDownTimer?.cancel()
    }
}