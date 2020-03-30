package wanbao.yongchao.com.wanbao.ui.main.activity

import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.GridLayoutManager
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_city.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.adapter.CityAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CityBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.CityPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CityView
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.location.LocationUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions

class CityActivity:BaseActivity(), UserPermissions.MemoryReadPermissionsFace, LocationUtils.Location ,CityView{
    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {
        LocationUtils(this).getLocation()
    }

    override fun requestPermissionsFaceError() {

    }

    override fun getLocationSuccess(city: String) {
        this.city=city
        if (isFirst) {
            if (list.size > 0) {
                list.forEach {
                    if (this.city == it.name) {
                        cityId = it.cityId.toString()
                        isFlag = true
                    }
                }
                if (isFlag) {
                    ShowDialog.showCustomDialogNoTitle(this, "当前定位为${city}，是否切换到该城市", "是", "否", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    dialog.dismiss()
                                    user.setCity(city)
                                    user.setCityID(cityId)
                                    intentUtils.intentMainCity(city)
                                    finish()
                                }
                                DialogInterface.BUTTON_NEGATIVE -> {
                                    dialog.dismiss()
                                }
                            }
                        }
                    })
                }
            }
        }
    }

    override fun getCityRequest(data: CityBean) {
        list=data.data
        var adapter=CityAdapter(data.data)
        var manager=GridLayoutManager(this,2)
        recy_city.layoutManager=manager
        recy_city.adapter=adapter

        adapter.addFooterView(View.inflate(this,R.layout.layout_no_more_city,null))

        adapter.setOnItemClickListener { mAdapter, view, position ->
            user.setCity(adapter.data[position].name)
            user.setCityID(adapter.data[position].cityId.toString())

            intentUtils.intentMainCity(adapter.data[position].name)
            finish()
        }


        if (isFirst) {
            if (city != "") {
                list.forEach {
                    if (this.city == it.name) {
                        cityId = it.cityId.toString()
                        isFlag = true
                    }
                }

                if (isFlag) {
                    ShowDialog.showCustomDialogNoTitle(this, "当前定位为${city}，是否切换到该城市", "是", "否", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    dialog.dismiss()
                                    user.setCity(city)
                                    user.setCityID(cityId)
                                    intentUtils.intentMainCity(city)
                                    finish()
                                }
                                DialogInterface.BUTTON_NEGATIVE -> {
                                    dialog.dismiss()
                                }
                            }
                        }
                    })
                }
            }
        }

    }

    private val presenter by lazy { CityPresenter(this,this,this) }
    private var list=ArrayList<CityBean.DataBean>()
    private var city=""
    private var cityId=""
    private var isFlag=false
    private var isFirst=false

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_city

    override fun setActivityTitle() {

    }

    override fun initActivityData() {
        if (intent.getStringExtra("first")!=null){
            isFirst=true
        }else{
            isFirst=false
        }
        UserPermissions.userLocation(mContext, this)
        presenter.getCity(CityBody("1","99999"))
    }

    override fun clickListener() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if(KeyEvent.KEYCODE_BACK==keyCode&&isFirst)
            return false
        return super.onKeyDown(keyCode, event)
    }
}