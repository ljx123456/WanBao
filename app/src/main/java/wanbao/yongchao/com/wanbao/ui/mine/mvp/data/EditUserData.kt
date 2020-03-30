package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditUserBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditUserBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class EditUserData(val edit:EditUser): BaseData<EditUserBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getEditUser(body: EditUserBody){
        api(Api.getApi().getEditUser(body)).build()
    }

    override fun onSucceedRequest(data: EditUserBean, what: Int) {
        edit.getEditUserRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        edit.getEditUserError()
    }

    interface EditUser{
        fun getEditUserRequest(data: EditUserBean)
        fun getEditUserError()
    }
}