package wanbao.yongchao.com.wanbao.ui.main.dialog

import android.graphics.Color
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.dialog_at.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.main.adapter.AtAdapter
import wanbao.yongchao.com.wanbao.ui.main.adapter.ChooseAtAdapter
import wanbao.yongchao.com.wanbao.ui.main.adapter.FragmentAdapter
import wanbao.yongchao.com.wanbao.ui.main.fragment.AtFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.FansBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.SearchBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.AtDialogPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.AtDialogView
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.CustomLoadMoreView

class AtDialog:BaseDialogFragment(),AtFragment.setAt,AtDialogView{
    override fun getSearchFansRequest(data: FansBean) {
        if (isVisible) {
            if (data.data != null && data.data.size > 0) {
                fans = data.data
                fans.forEach {
                    it.isFlag = false
                    var bean = it
                    list.forEach {
                        if (it.id == bean.id) {
                            bean.isFlag = true
                        }
                    }
                }
                searchAdapter = AtAdapter(fans)
                var manager = LinearLayoutManager(activity!!)
                manager.orientation = LinearLayout.VERTICAL
                recy_search.layoutManager = manager
                recy_search.adapter = searchAdapter
                recy_search.isNestedScrollingEnabled = false
                searchAdapter!!.setOnItemClickListener { mAdapter, view, position ->
                    layout_vp.visibility = View.VISIBLE
                    layout_search.visibility = View.GONE
                    if (searchAdapter!!.data[position].isFlag) {
                        searchAdapter!!.data[position].isFlag = false
                        setCancel(searchAdapter!!.data[position])
                    } else {
                        searchAdapter!!.data[position].isFlag = true
                        setAdd(searchAdapter!!.data[position])
                    }

                    edit_search.setText("")
                    fans.clear()
                    search.clear()
                    searchAdapter!!.setNewData(null)
                    scroll.visibility = View.GONE

                }
            }
            if (search.size > 0) {
                scroll.visibility = View.VISIBLE
            } else {
                scroll.visibility = View.GONE
            }

        }
    }

    override fun getSearchUserRequest(data: FansBean) {
//        if (data.data!=null&&data.data.size>0){
        if (isVisible) {
            search.addAll(data.data)
            search.forEach {
                it.isFlag = false
                var bean = it
                list.forEach {
                    if (it.id == bean.id) {
                        bean.isFlag = true
                    }
                }
            }
            if (userAdapter != null) {
                userAdapter!!.addData(data.data)
            } else {
                userAdapter = AtAdapter(search)
                var manager = LinearLayoutManager(activity!!)
                manager.orientation = LinearLayout.VERTICAL
                recy_user.layoutManager = manager
                recy_user.adapter = userAdapter
                recy_user.isNestedScrollingEnabled = false
            }
            userAdapter!!.setLoadMoreView(CustomLoadMoreView())
            if (data.data.size < 10) {
                userAdapter!!.loadMoreEnd()
//            adapter!!.removeAllFooterView()
//            adapter!!.addFooterView(View.inflate(activity!!,R.layout.layout_community_footer,null))
            } else {
                userAdapter!!.loadMoreComplete()
            }

            userAdapter!!.setOnItemClickListener { mAdapter, view, position ->
                layout_vp.visibility = View.VISIBLE
                layout_search.visibility = View.GONE
                if (userAdapter!!.data[position].isFlag) {
                    userAdapter!!.data[position].isFlag = false
                    setCancel(userAdapter!!.data[position])
                } else {
                    userAdapter!!.data[position].isFlag = true
                    setAdd(userAdapter!!.data[position])
                }

                edit_search.setText("")
                fans.clear()
                search.clear()
                userAdapter!!.setNewData(null)
                scroll.visibility = View.GONE
            }

//            userAdapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
//                override fun onLoadMoreRequested() {
//                    pageIndex=pageIndex+1
//                    presenter.getSearchUser(SearchBody(edit_search.text.toString(),"2","1",pageIndex.toString(),pageSize.toString()))
//                }
//            },recy_user)
//        }
            if (fans.size > 0) {
                scroll.visibility = View.VISIBLE
            } else {
                scroll.visibility = View.GONE
            }
        }
    }

    private var fragments=ArrayList<AtFragment>()
    var mFragmentAdapter: FragmentAdapter? = null
    private var list=ArrayList<FansBean.DataBean>()
    private val presenter by lazy { AtDialogPresenter(this,this,activity!!) }
    
    private var adapter:ChooseAtAdapter?=null

    private var searchAdapter:AtAdapter?=null
    private var userAdapter:AtAdapter?=null
    private var pageIndex=1
    private var pageSize=10
    private var fans=ArrayList<FansBean.DataBean>()
    private var search=ArrayList<FansBean.DataBean>()
    private var atList:AtList?=null
    private var id=""

    override fun setLayoutID(): Int = R.layout.dialog_at

    override fun isFullScreen(): Boolean =true

    override fun setLayoutData() {

        var title=ArrayList<String>()
        title.add("最近")
        title.add("关注")
        title.add("粉丝")
        title.forEach {
            fragments.add(AtFragment(it,this))
        }
        mFragmentAdapter= FragmentAdapter(childFragmentManager,fragments,title)
        vp_at.adapter=mFragmentAdapter
        vp_at.offscreenPageLimit=2
        vp_at.currentItem=0
    }

    override fun clickListener() {
        Click.viewClick(tv_nearby).subscribe {
            vp_at.currentItem=0
            tv_nearby.setTextColor(Color.parseColor("#FCC725"))
            tv_nearby.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)

            tv_like.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_fans.setTextColor(Color.parseColor("#a6ffffff"))
            tv_fans.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        }

        Click.viewClick(tv_like).subscribe {
            vp_at.currentItem=1
            tv_like.setTextColor(Color.parseColor("#FCC725"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)

            tv_nearby.setTextColor(Color.parseColor("#a6ffffff"))
            tv_nearby.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_fans.setTextColor(Color.parseColor("#a6ffffff"))
            tv_fans.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        }

        Click.viewClick(tv_fans).subscribe {
            vp_at.currentItem=2
            tv_fans.setTextColor(Color.parseColor("#FCC725"))
            tv_fans.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20f)

            tv_like.setTextColor(Color.parseColor("#a6ffffff"))
            tv_like.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
            tv_nearby.setTextColor(Color.parseColor("#a6ffffff"))
            tv_nearby.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16f)
        }

        Click.viewClick(iv_search).subscribe {
            layout_vp.visibility=View.GONE
            layout_search.visibility=View.VISIBLE
        }

        Click.viewClick(tv_cancel).subscribe {
            layout_vp.visibility=View.VISIBLE
            layout_search.visibility=View.GONE
        }

        Click.viewClick(iv_close).subscribe {
            dismiss()
        }

        edit_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId== EditorInfo.IME_ACTION_SEARCH){
                if (edit_search.text!=null&&edit_search.text.toString().isNotEmpty()){
                    pageIndex=1
                    presenter.getSearchFans(SearchBody(edit_search.text.toString(),"7","1","1000000"))
                    presenter.getSearchUser(SearchBody(edit_search.text.toString(),"2","1",pageIndex.toString(),pageSize.toString()))
                }else{
                    Toast.Tips("请输入搜索内容")
                }
            }
            return@setOnEditorActionListener false
        }

        scroll.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView?, p1: Int, p2: Int, p3: Int, p4: Int) {
                var  contentView = scroll.getChildAt(0)
                if (contentView != null && contentView.getMeasuredHeight() == (scroll.getScrollY() + scroll.getHeight())) {
                    //底部
//                    manage.setmCanVerticalScroll(true)
                    Log.e("测试","滑动到底部了")
                    pageIndex=pageIndex+1
                    presenter.getSearchUser(SearchBody(edit_search.text.toString(),"2","1",pageIndex.toString(),pageSize.toString()))
                }
            }
        })

        Click.viewClick(btn_at).subscribe {
            if (list.size>0){
                if (atList!=null){
                    dismiss()
                    atList!!.atList(list)
                }
            }else{
                Toast.Tips("请选择@的人")
            }
        }
    }

    override fun setAt(bean: FansBean.DataBean) {
        setAdd(bean)
    }

    override fun cancel(bean: FansBean.DataBean) {
        setCancel(bean)
    }

    fun setAdd(bean: FansBean.DataBean){
        if (isVisible) {
            layout_at.visibility = View.VISIBLE
            list.add(bean)
            if (adapter != null) {
                adapter!!.setNewData(list)
            } else {
                adapter = ChooseAtAdapter(list)
                var manager = LinearLayoutManager(activity!!)
                manager.orientation = LinearLayout.HORIZONTAL
                recy_at.layoutManager = manager
                recy_at.adapter = adapter
            }
            recy_at.smoothScrollToPosition(list.size - 1)
            adapter!!.setOnItemClickListener { mAdapter, view, position ->
                fragments.forEach {
                    it.setCancel(adapter!!.data[position])
                }
                list.removeAt(position)
                adapter!!.data.removeAt(position)
                adapter!!.notifyDataSetChanged()
            }

            fragments.forEach {
                it.setUpdateList(list)
            }
        }
    }

    fun setCancel(bean: FansBean.DataBean){
        if (isVisible) {
            if (list.size > 0) {
                Log.e("测试b", bean.toString())
                var b = FansBean.DataBean()
                list.forEachIndexed { index, atBean ->
                    if (atBean.nickname == bean.nickname && atBean.avatar == bean.avatar) {
                        Log.e("测试at", atBean.toString())
                        b = atBean
                    }
                }
                list.remove(b)
                adapter!!.data.remove(b)
                fragments.forEach {
                    it.setUpdateList(list)
                }
                if (list.size == 0) {
                    layout_at.visibility = View.GONE
                }
            }
        }
    }

    fun setAtDialog(atList: AtList){
        this.atList=atList
    }

    interface AtList{
        fun atList(list:ArrayList<FansBean.DataBean>)
    }

}