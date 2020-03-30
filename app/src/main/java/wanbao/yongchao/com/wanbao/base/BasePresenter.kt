package wanbao.yongchao.com.wanbao.base


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.*


/**
 * Created by Administrator on 2018/2/1 0001.
 */
open abstract class BasePresenter : Consumer<Disposable>, LifecycleObserver {


    private var disposableList = ArrayList<Disposable>()
    private var view: BaseView
    private var context: Context

    constructor(owner: LifecycleOwner, view: BaseView, context: Context) {
        this.view = view
        this.context = context
        owner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroyHttp() {
        addDisposableList(disposableList)
        if (!disposableList.isEmpty()) Observable.fromIterable(disposableList).subscribe(this)
    }

    override fun accept(t: Disposable) {
        if (!t.isDisposed) t.dispose()
    }

    abstract fun addDisposableList(list: ArrayList<Disposable>)


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    abstract fun presenterDestroy()

}