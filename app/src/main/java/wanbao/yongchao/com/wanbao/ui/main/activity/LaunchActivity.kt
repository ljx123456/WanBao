package wanbao.yongchao.com.wanbao.ui.main.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.tbruyelle.rxpermissions2.RxPermissions
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.permissions.UserPermissions

class LaunchActivity: AppCompatActivity(), UserPermissions.MemoryReadPermissionsFace{
    override fun requestPermissionsFaceError() {

    }

    override fun requestPermissionsFaceSucceed(context: Context, what: Int) {

    }

    private var flag=false
    private var isTime=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            val option = ( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            decorView.systemUiVisibility = option
            window.navigationBarColor = Color.TRANSPARENT
            window.statusBarColor = Color.TRANSPARENT
        }
//        val actionBar = getSupportActionBar()
//        actionBar!!.hide()
//        nac_layout.alpha = 0.toFloat()
//        nac_root.setFadingView(nac_layout)
//        nac_root.setFadingHeightView(detailsBanner)
//        val rxPermissions = RxPermissions(this)
//        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe { aBoolean ->
//            if (aBoolean!!) {
//                try {
//                    DBUtils.delMerchat()
//                    ServeUtils.deleteALLServe()
//                    DrinkUtils.deleteALLDrinks()
//                }catch (e:Exception){
//                    e.printStackTrace()
//                }
//                var userDB = GreenDaoHelper.getDaoSessions().userDBDao
//
//                Handler().postDelayed(object :Runnable{
//                    override fun run() {
//
//                        if (userDB != null) {
//                            var data = userDB.loadAll()
//                            if (data != null && data.size >= 1 && data.get(0).token != null) {
//                                intentUtils.intentMain()
//                                finish()
//                            }else{
//                                intentUtils.intentLogin()
//                                finish()
//                            }
//                        }else{
//                            intentUtils.intentLogin()
//                            finish()
//                        }
//                    }
//                },1000*3)
//            }
//            else {
//                ShowDialog.showCustomDialogNoTitle(this,"由于无法获取设备信息的权限,应用无法运行，请前往设置中心应用权限页设置","去设置","取消",object : DialogInterface.OnClickListener{
//                    override fun onClick(dialog: DialogInterface, which: Int) {
//                        when (which) {
//                            DialogInterface.BUTTON_POSITIVE -> {
//                                dialog.dismiss()
//                                UserPermissions.gotoSet(this@LaunchActivity)
//                            }
//                            DialogInterface.BUTTON_NEGATIVE -> {
//                                dialog.dismiss()
//                            }
//                        }
//                    }
//                })
//            }
//        }

    }

    override fun onResume() {
        super.onResume()
        if (!flag) {
            flag=true
            UserPermissions.userLocation(this,this)
            init()
        }
//        if (BaseUrl.HOST_URL=="http://service.bixinyule.com/") {
//            MobclickAgent.onResume(this)
//        }
    }

    override fun onPause() {
        super.onPause()
//        if (BaseUrl.HOST_URL=="http://service.bixinyule.com/") {
//            MobclickAgent.onPause(this)
//        }
    }

    fun init(){
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION).subscribe { aBoolean ->
            if (aBoolean!!) {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        if (user.getCityID()==""){
                            intentUtils.intentCity("first")
                            finish()
                        }else {
                            intentUtils.intentMain()
                            finish()
                        }
                    }
                }, 1000)

            } else {
                if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    ShowDialog.showCustomDialogNoTitle(this, "由于无法获取设备信息的权限,应用无法运行，请前往设置中心应用权限页设置", "去设置", "取消", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    dialog.dismiss()
                                    flag=false
                                    UserPermissions.gotoSet(this@LaunchActivity)
                                }
                                DialogInterface.BUTTON_NEGATIVE -> {
                                    dialog.dismiss()
                                    finish()
                                }
                            }
                        }
                    })
                }else{
                    ShowDialog.showCustomDialogs(this, "由于无法获取设备定位权限,应用无法运行，请开启权限", "去开启", "取消", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    init()
                                    dialog.dismiss()
                                }
                                DialogInterface.BUTTON_NEGATIVE -> {
                                    dialog.dismiss()
                                    finish()
                                }
                            }
                        }
                    })
                }

            }
        }
    }
}