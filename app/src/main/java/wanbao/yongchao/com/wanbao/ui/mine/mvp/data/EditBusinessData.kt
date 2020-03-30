package wanbao.yongchao.com.wanbao.ui.mine.mvp.data

import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditBusinessBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditBusinessBody
import wanbao.yongchao.com.wanbao.utils.http.Api
import wanbao.yongchao.com.wanbao.utils.http.BaseData
import wanbao.yongchao.com.wanbao.utils.http.SaveInfo
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class EditBusinessData(val edit:EditBusiness): BaseData<EditBusinessBean>(){
    override fun requestCache(): SaveInfo = SaveInfo(false, javaClass.name)

    fun getEditBusiness(body: EditBusinessBody){
        api(Api.getApi().getEditBusiness(body)).build()
    }

    override fun onSucceedRequest(data: EditBusinessBean, what: Int) {
        edit.getEditBusinessRequest(data)
    }

    override fun onErrorRequest(flag: Int, msg: String, what: Int) {
        Toast.Tips(msg)
        edit.getEditBusinessError()
    }

    interface EditBusiness{
        fun getEditBusinessRequest(data: EditBusinessBean)
        fun getEditBusinessError()
    }
}