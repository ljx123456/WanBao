package wanbao.yongchao.com.wanbao.ui.main.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CityBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.data.CityData
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CityView
import java.util.ArrayList

class CityPresenter(owner: LifecycleOwner, val view: CityView, val mContext: Context) : BasePresenter(owner, view, mContext), CityData.City {

    override fun addDisposableList(list: ArrayList<Disposable>) {
        data.getDisposable()?.let { list.add(it) }
    }

    override fun presenterDestroy() {

    }

    val data=CityData(this)
    fun getCity(body:CityBody){
        data.getCity(body)
    }

    override fun getCityRequest(data: CityBean) {
        view.getCityRequest(data)
    }

    override fun getCityError() {

    }
}