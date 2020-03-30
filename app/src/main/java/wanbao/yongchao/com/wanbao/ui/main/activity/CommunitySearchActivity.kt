package wanbao.yongchao.com.wanbao.ui.main.activity

import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_community_search.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.db.user
import wanbao.yongchao.com.wanbao.ui.main.adapter.FragmentAdapter
import wanbao.yongchao.com.wanbao.ui.main.adapter.HotTopicAdapter
import wanbao.yongchao.com.wanbao.ui.main.fragment.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.HotTopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.SearchTopicBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.CommunitySearchPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.CommunitySearchView
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.utils.utils.Toast

class CommunitySearchActivity:BaseActivity(),CommunitySearchView {
    override fun getHotTopicRequest(data: HotTopicBean) {
        if (data.data.size>0){
            layout_hot_search.visibility=View.VISIBLE
            adapter=HotTopicAdapter(data.data)
            var manager=LinearLayoutManager(this)
            manager.orientation=LinearLayout.VERTICAL
            recy_hot_search.layoutManager=manager
            recy_hot_search.adapter=adapter

            adapter.setOnItemClickListener { mAdapter, view, position ->
                intentUtils.intentTopicDetails(adapter.data[position].id.toString(),adapter.data[position].name)
            }
        }else{
            layout_hot_search.visibility=View.GONE
        }
    }

    private var historyList=ArrayList<String>()
    private lateinit var adapter:HotTopicAdapter
    private var fragments=ArrayList<BaseFragment>()
    private var titles=ArrayList<String>()
    var mFragmentAdapter: FragmentAdapter? = null
    private val presenter by lazy { CommunitySearchPresenter(this,this,this) }

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_community_search

    override fun setActivityTitle() {

    }

    override fun initActivityData() {

        presenter.getHotTopic()

        titles.add("内容")
        titles.add("用户")
        titles.add("话题")
        titles.add("地点")
        titles.add("活动")
        titles.add("地标")

        var mCache = ACache.get(this)
        mCache.getAsString("HistorySearch")
        if (mCache.getAsString("HistorySearch") != null && !"".equals(mCache.getAsString("HistorySearch"))) {
            historyList = Gson().fromJson(mCache.getAsString("HistorySearch"), ArrayList<String>()::class.java)
            if (historyList.size>0) {
                layout_history.visibility = View.VISIBLE
                history.setList(historyList)
            }else{
                layout_history.visibility= View.GONE
            }
        }else{
            layout_history.visibility= View.GONE
        }

//        if(historyList.size>0){
//            layout_history.visibility= View.VISIBLE
//            history.setList(historyList)
//        }else{
//            layout_history.visibility= View.GONE
//        }



    }

    override fun clickListener() {

        Click.viewClick(tv_cancel).subscribe {
            finish()
        }

        Click.viewClick(iv_delete).subscribe {
            ShowDialog.showCustomDialogNoTitle(this,"继续操作将清空历史搜索记录","清空","取消",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            dialog.dismiss()
                            historyList.clear()
                            var mCache = ACache.get(this@CommunitySearchActivity)
                            mCache.put("HistorySearch","")
                            layout_history.visibility=View.GONE
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
            })

        }

        history.setOnItemClickListener { position, text ->
            layout_history.visibility=View.GONE
            layout_hot_search.visibility=View.GONE
            layout_result.visibility=View.VISIBLE
            edit_search.setText(text)
            setFragment(text)
        }


        edit_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId==EditorInfo.IME_ACTION_SEARCH){
                if (edit_search.text!=null&&edit_search.text.toString().isNotEmpty()){
                    if (!historyList.contains(edit_search.text.toString())){
                        historyList.add(edit_search.text.toString())
                    }
                    layout_history.visibility=View.GONE
                    layout_hot_search.visibility=View.GONE
                    layout_result.visibility=View.VISIBLE
                    setFragment(edit_search.text.toString())
                }else{
                    Toast.Tips("请输入搜索内容")
                }
            }
            return@setOnEditorActionListener false
        }

    }


    fun setFragment(str:String){
        if (fragments.size>0){
            fragments=ArrayList<BaseFragment>()
            fragments.add(SearchCommunityFragment(str))
            fragments.add(SearchUserFragment(str))
            fragments.add(SearchTopicFragment(str))
            fragments.add(SearchAddressFragment(str))
            fragments.add(SearchActivityFragment(str))
            fragments.add(SearchLandMarkFragment(str))
            mFragmentAdapter!!.notifyDataSetChanged()
        }else{
            fragments.add(SearchCommunityFragment(str))
            fragments.add(SearchUserFragment(str))
            fragments.add(SearchTopicFragment(str))
            fragments.add(SearchAddressFragment(str))
            fragments.add(SearchActivityFragment(str))
            fragments.add(SearchLandMarkFragment(str))
            titles.forEach {
                searchTab.addTab(searchTab.newTab().setText(it))
            }
            mFragmentAdapter = FragmentAdapter(supportFragmentManager, fragments, titles)
            vp_search.adapter = mFragmentAdapter
            searchTab.setupWithViewPager(vp_search)
            searchTab.setTabsFromPagerAdapter(mFragmentAdapter)
        }


//        vp_search.setOffscreenPageLimit(3)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (historyList.size>0) {
            var mCache = ACache.get(this)
            mCache.put("HistorySearch", Gson().toJson(historyList))
        }
    }

    override fun onResume() {
        super.onResume()
        if (user.getWantId()!=""){
            if (vp_search.currentItem==4){
                (fragments[4] as SearchActivityFragment).setUpdataWant(user.getWantId())
            }
        }
    }

}