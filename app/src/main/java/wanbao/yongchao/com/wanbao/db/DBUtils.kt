package wanbao.yongchao.com.wanbao.db

import com.blankj.utilcode.util.LogUtils
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper.getDaoSessions
import wanbao.yongchao.com.wanbao.db.db.BusinessDB
import wanbao.yongchao.com.wanbao.db.db.UserDB
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.BusinessBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.UserBean

object DBUtils{
    fun getUser(): UserDB {
        var user = getDaoSessions().userDBDao
        var data = user.loadAll().get(0)
        return data
    }

    fun setUserDB(info: UserBean.DataBeanX) {
        var userDB = UserDB(0.toLong(), info.token, info.data.avatar,info.data.id.toString(), info.data.nickname,info.data.signature,info.data.role,info.data.accountState,info.data.authState)
        var userdata = getDaoSessions().userDBDao
        if (userdata != null) {
            userdata.deleteAll()
        }
        try {
            userdata.insert(userDB)
        } catch (e: Exception) {
            LogUtils.a("DB", e.toString())
        }
    }

    fun DelUser(){
        var user = getDaoSessions().userDBDao
        user.deleteAll()
    }


    fun getBusiness(): BusinessDB {
        var user = getDaoSessions().businessDBDao
        var data = user.loadAll().get(0)
        return data
    }

    fun setBusinessDB(info: BusinessBean.DataBeanX) {
        var userDB = BusinessDB(0.toLong(), info.token, info.data.avatar,info.data.id.toString(), info.data.nickname,info.data.signature,info.data.role,info.data.accountState,info.data.authState)
        var userdata = getDaoSessions().businessDBDao
        if (userdata != null) {
            userdata.deleteAll()
        }
        try {
            userdata.insert(userDB)
        } catch (e: Exception) {
            LogUtils.a("DB", e.toString())
        }
    }

    fun DelBusiness(){
        var user = getDaoSessions().businessDBDao
        user.deleteAll()
    }
}