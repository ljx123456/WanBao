package wanbao.yongchao.com.wanbao.ui.login.activity

import android.util.Log
import android.view.View
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_register_tags.*
import kotlinx.android.synthetic.main.layout_title.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.TagsBean
import wanbao.yongchao.com.wanbao.ui.login.mvp.presenter.TagsPresenter
import wanbao.yongchao.com.wanbao.ui.login.mvp.view.TagsView
import wanbao.yongchao.com.wanbao.ui.login.utils.TagsUtils
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.EditUserBean
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.EditUserBody
import wanbao.yongchao.com.wanbao.ui.mine.mvp.presenter.EditUserPresenter
import wanbao.yongchao.com.wanbao.ui.mine.mvp.view.EditUserView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class RegisterTagsActivity :BaseActivity(),TagsView,EditUserView{
    override fun getEditUserRequest(data: EditUserBean) {
        user.setChange(true)
        finish()
    }

    override fun getTagsRequest(data: TagsBean) {
        if (data.data!=null&&data.data.size>0){
            tags=data.data
            tags.forEach {
                list1.add(it.description)
            }

            Tags.setList(list1)

            if (TagsUtils.getTagList().size>0){
                chooseTag.visibility=View.VISIBLE
                tag=TagsUtils.getTagList()
                tag.forEach {
                    list.add(it.description)
                }
                chooseTag.setList(list)
                list.forEach {
                    Tags.setIndexItemSelected(list1.indexOf(it))
                }
                titleRightText.text = "确定("+list.size.toString() + "/6)"
            }else{
                chooseTag.visibility=View.GONE
            }
        }

    }

    private val presenter by lazy { TagsPresenter(this,this,this) }

    private var tags=ArrayList<TagsBean.DataBean>()
    private var tag=ArrayList<TagsBean.DataBean>()
    private var list:ArrayList<String> = ArrayList()
    private var list1:ArrayList<String> = ArrayList()

    private val editPresenter by lazy { EditUserPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_register_tags

    override fun setActivityTitle() {
        titleLeft.setImageResource(R.mipmap.signin_button_back)
        titleText.text="添加标签"
        titleRightText.visibility=View.VISIBLE
        titleRightText.text="确定(0/6)"
    }

    override fun initActivityData() {

        presenter.getTags()


    }

    override fun clickListener() {

        Click.viewClick(titleLeft).subscribe { finish() }
        Click.viewClick(titleRightText).subscribe {
            //TODO
            if (intent.getStringExtra("flag")!=null&&intent.getStringExtra("flag")!=""&&intent.getStringExtra("flag")=="编辑"){
                if (tag.size>0) {
                    var body = EditUserBody()
                    var list = ArrayList<Int>()
                    tag.forEach {
                        list.add(it.id)
                    }
                    body.tagIds = list
                    body.type = "6"
                    editPresenter.setEditUser(body)
                }else{
                    Toast.Tips("请添加标签")
                }
            }else {
                TagsUtils.setTagList(tag)
                finish()
            }
        }

        chooseTag.setOnItemClickListener { position, text ->
            Log.e("测试pos",":"+position.toString())
//            chooseTagsLabel.isSelected()
            Tags.cancelSelectedIndex(list1.indexOf(list[position]))
            list.removeAt(position)
            tag.removeAt(position)
//            listBean.removeAt(position)
            chooseTag.setList(list)

            titleRightText.text = "确定("+list.size.toString() + "/6)"
            if (list.size==0){
                chooseTag.visibility=View.GONE
            }

        }

        Tags.setOnItemClickListener { position, text ->
            if (list.size<6) {
                if (!Tags.isSelected(position)) {
//                if (list.size<12) {
                    list.remove(list1[position])
                    var s: TagsBean.DataBean? = null
                    tag.forEach {
                        if (it.description == list1[position]) {
                            s = it
                            return@forEach
                        }
                    }
                    tag.remove(s)
//                    LogUtils.a("测试listBean变化", tag.size.toString())
                    chooseTag.setList(list)
                    titleRightText.text = "确定("+list.size.toString() + "/6)"
                    if (list.size == 0) {
                        chooseTag.visibility = View.GONE
                    }
//                }
                } else {
                    if (list.size==0) {
                        list.add(list1[position])
                        tag.add(tags[position])
//                    listBean.add(listBean1[position])
                        chooseTag.setList(list)
                    }else{
                        list.add(list1[position])
                        tag.add(tags[position])
                        chooseTag.add(list1[position])
                    }
                    if (chooseTag.visibility==View.GONE) {
                        chooseTag.visibility = View.VISIBLE
                    }
                    titleRightText.text = "确定("+list.size.toString() + "/6)"
                }
            }else{
                if (!Tags.isSelected(position)){
                    list.remove(list1[position])
                    var s: TagsBean.DataBean? = null
                    tag.forEach {
                        if (it.description == list1[position]) {
                            s = it
                            return@forEach
                        }
                    }
                    tag.remove(s)
//                    LogUtils.a("测试listBean变化", tag.size.toString())
                    chooseTag.setList(list)
//                    chooseTag.remove(list.indexOf(list1[position]))
                    titleRightText.text = "确定("+list.size.toString() + "/6)"
                    if (list.size == 0) {
                        chooseTag.visibility = View.GONE
                    }
                }else{
                    Toast.Tips("标签最多允许选择6个")
                    Tags.cancelSelectedIndex(position)
                }
            }
        }

    }
}