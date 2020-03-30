package wanbao.yongchao.com.wanbao.ui.login.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import io.reactivex.disposables.Disposable
import wanbao.yongchao.com.wanbao.base.BasePresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.CodeBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.GetCodeBody
import wanbao.yongchao.com.wanbao.ui.login.mvp.data.GetCodeData
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.GetCodeView
import java.util.ArrayList

class GetCodePresenter(owner: LifecycleOwner, val view: GetCodeView, val mContext: Context) : BasePresenter(owner, view, mContext), GetCodeData.GetCode{

    private val getCode=GetCodeData(this)

    fun getCode(body: GetCodeBody){
        getCode.getCode(body)
    }

    override fun addDisposableList(list: ArrayList<Disposable>) {
        getCode.getDisposable()?.let {
            list.add(it)
        }
    }

    override fun presenterDestroy() {

    }

    override fun getCodeRequest(data: CodeBean) {
        view.getGetCodeRequest(data)
    }

    override fun getCodeError() {

    }

}