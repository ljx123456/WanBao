package wanbao.yongchao.com.wanbao.ui.login.utils

import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean

object TagsUtils{

    private var list=ArrayList<TagsBean.DataBean>()

    fun setTagList(list: ArrayList<TagsBean.DataBean>){
        this.list=list
    }

    fun getTagList():ArrayList<TagsBean.DataBean>{
        return list
    }
}