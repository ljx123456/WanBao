package wanbao.yongchao.com.wanbao.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object GreenDaoHelper {

    private lateinit var devOpenHelper: DaoMaster.DevOpenHelper
    private lateinit var datebese: SQLiteDatabase
    private lateinit var daoMaster: DaoMaster
    private lateinit var daoSession: DaoSession

    fun initDatabase(mContext: Context) {
        devOpenHelper = DaoMaster.DevOpenHelper(mContext, "wanbao", null)
        datebese = devOpenHelper.writableDatabase
        daoMaster = DaoMaster(datebese)
        daoSession = daoMaster.newSession()
    }

    fun getDaoSessions(): DaoSession {
        return daoSession
    }

}