package wanbao.yongchao.com.wanbao.utils.utils

import android.content.Context
import android.content.SharedPreferences
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext


/**
 * Created by Administrator on 2018/3/2 0002.
 */
object SpHelp {

    const val file_progress = "file_progress"
    const val sp_package = "sp_package"
    const val first_install = "第一次安装程序"

    private lateinit var edit: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences



    fun initSharedPreferences() {
        sharedPreferences = getContext().getSharedPreferences(sp_package, Context.MODE_PRIVATE)
        edit = sharedPreferences.edit()
    }



    fun putString(key: String, value: String) = edit.putString(key, value).commit()

    fun putBoolean(key: String, value: Boolean) = edit.putBoolean(key, value).commit()

    fun putInt(key: String, value: Int) = edit.putInt(key, value).commit()




    fun getString(key: String, value: String?): String? = sharedPreferences.getString(key, value)

    fun getBoolean(key: String, value: Boolean = false): Boolean = sharedPreferences.getBoolean(key, value)

    fun getInt(key: String, value: Int = -1): Int = sharedPreferences.getInt(key, value)

    fun clearAll() = edit.clear()

}