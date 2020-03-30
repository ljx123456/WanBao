package wanbao.yongchao.com.wanbao.ui.mine.dialog

import android.annotation.SuppressLint
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_auth_blogger_type.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.mine.adapter.AuthBloggerTypeAdapter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.AuthTypeBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.AuthTypeBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.AuthTypePresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.AuthTypeView
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class AuthBloggerTypeDialog(val type:AuthType):BaseDialogFragment() ,AuthTypeView{
    override fun getAuthTypeRequest(data: AuthTypeBean) {
        if (isVisible) {
            var adapter = AuthBloggerTypeAdapter(data.data)
            var manager = LinearLayoutManager(activity!!)
            recy_type.layoutManager = manager
            recy_type.adapter = adapter
            adapter.setOnItemClickListener { mAdapter, view, position ->
                type.setAuthType(adapter.data[position].id, adapter.data[position].description)
                dismiss()
            }
        }
    }

    private val presenter by lazy { AuthTypePresenter(this,this,activity!!) }

    override fun setLayoutID(): Int = R.layout.dialog_auth_blogger_type

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        var user = GreenDaoHelper.getDaoSessions().userDBDao
        if (user != null) {
            var info = user.loadAll()
            if (info != null && info.size > 0) {
                presenter.getAuthType(AuthTypeBody(info.get(0).token))
            }
        }
    }

    override fun clickListener() {

        Click.viewClick(dialogOver).subscribe {
            dismiss()
        }

        Click.viewClick(dialog_close).subscribe {
            dismiss()
        }

    }

    interface AuthType{
        fun setAuthType(id:Int,type:String)
    }
}