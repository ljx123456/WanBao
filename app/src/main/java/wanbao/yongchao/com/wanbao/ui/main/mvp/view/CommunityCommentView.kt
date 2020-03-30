package wanbao.yongchao.com.wanbao.ui.main.mvp.view

import wanbao.yongchao.com.wanbao.base.BaseView
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddCommentBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.AddReplyBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityCommentBean

interface CommunityCommentView:BaseView{
    fun CommunityCommentRequest(data:CommunityCommentBean)
    fun CommmunityCommentError()

    fun getLikeRequest()

    fun getAddCommentRequest(data:AddCommentBean)

    fun getCommentDelRequest()

    fun getAddReplyRequest(data: AddReplyBean)

}