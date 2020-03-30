package wanbao.yongchao.com.wanbao.ui.login.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView

interface QiniuView: BaseView {
    fun sendSucceedImage(fileUrlList: ArrayList<String>)
    fun sendFileErrorImage()
}