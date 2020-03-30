package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddReplyBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentDetailsBean

interface CommentDetailsView:BaseView{
    fun getCommentRequest(data:CommentBean)
    fun getCommentError()

    fun getCommentDetailsRequest(data:CommentDetailsBean)
    fun getCommentDetailsError()

    fun getLikeRequest()

    fun getAddReplyRequest(data:AddReplyBean)

    fun getCommentDelRequest()
}