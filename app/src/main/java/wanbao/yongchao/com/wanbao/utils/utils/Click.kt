package wanbao.yongchao.com.wanbao.utils.utils

import android.view.View
import android.widget.CompoundButton
import com.jakewharton.rxbinding2.InitialValueObservable
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Administrator on 2018/2/26 0026.
 */
object Click {

    //单机监听
    fun viewClick(view: View): Observable<Any> {
        return RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS)
    }

    //选中监听
    fun viewChecked(view: CompoundButton): InitialValueObservable<Boolean> {
        return RxCompoundButton.checkedChanges(view)
    }
}