package wanbao.yongchao.com.wanbao.ui.main.fragment

import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_community.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseFragment
import wanbao.yongchao.com.wanbao.ui.main.adapter.FragmentAdapter
import wanbao.yongchao.com.wanbao.ui.main.adapter.TopicAdapter
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.TopicBean
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Click

class CommunityFragment :BaseFragment(){
    var mFragmentAdapter: FragmentAdapter? = null
    var fragments = ArrayList<CommunityHotFragment>()
    private lateinit var adapter: TopicAdapter
    var list=ArrayList<TopicBean>()
    var list1=ArrayList<String>()


    override fun openEventBus(): Boolean = true

    override fun setLayoutTitle() {

    }

    override fun initFragmentData() {
        var titles = ArrayList<String>()
        titles.add("热门")
        titles.add("广场")
        titles.add("附近")
        titles.add("同城")
        titles.add("关注")
//        fragments.add(CommunityHotFragment("热门"))
//        fragments.add(CommunityHotFragment("附近"))
//        fragments.add(CommunityFollowFragment("关注"))


        titles.forEach {
            communityTab.addTab(communityTab.newTab().setText(it))
            fragments.add(CommunityHotFragment(it))
        }
        mFragmentAdapter = FragmentAdapter(childFragmentManager, fragments, titles)
        vp_community.adapter = mFragmentAdapter
        communityTab.setupWithViewPager(vp_community)
        communityTab.setTabsFromPagerAdapter(mFragmentAdapter)
        vp_community.setOffscreenPageLimit(2)

//        setList()
//        adapter=TopicAdapter(list)
//        var manager= TopLayoutManager(activity)
//        manager.orientation= LinearLayout.HORIZONTAL
//        recy_hot_topic.layoutManager=manager
//        recy_hot_topic.adapter=adapter
//        chooseTopic.setList(list1)

    }

    override fun setFragmentListener() {



//        vp_community.setOnPageChangeListener(object :ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(p0: Int) {
//
//            }
//
//            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
//
//            }
//
//            override fun onPageSelected(p0: Int) {
//                if (p0==4){
//                    layout_topic.visibility=View.GONE
//                }else{
//                    layout_topic.visibility=View.VISIBLE
//                }
//            }
//        })

//        adapter.setOnItemClickListener { mAdapter, view, position ->
//
//            if (adapter.data[position].flag==true){
//                adapter.data[position].flag = false
//                adapter.notifyDataSetChanged()
//                chooseTopic.cancelAllSelectedItems()
//            }else {
//                adapter.data[position].flag = true
//                adapter.data.forEach {
//                    if (adapter.data[position].text != it.text) {
//                        it.flag = false
//                    }
//                }
//                adapter.notifyDataSetChanged()
//                chooseTopic.cancelAllSelectedItems()
//                chooseTopic.setIndexItemSelected(position)
//                recy_hot_topic.smoothScrollToPosition(position)
//            }
//        }
//
//        Click.viewClick(iv_topic_down).subscribe {
//            layout_content.visibility= View.GONE
//            scroll_topic.visibility= View.VISIBLE
//
//        }
//
//        Click.viewClick(iv_topic_top).subscribe {
//            layout_content.visibility= View.VISIBLE
//            scroll_topic.visibility= View.GONE
//        }
//
//        chooseTopic.setOnItemClickListener { position, text ->
//            if (list[position].flag==true){
//                chooseTopic.cancelAllSelectedItems()
//                adapter.data[position].flag=false
//                adapter.notifyDataSetChanged()
//            }else {
//                adapter.data.forEachIndexed { index, topicBean ->
//                    if (position == index) {
//                        topicBean.flag = true
//                    } else {
//                        topicBean.flag = false
//                    }
//                }
//                adapter.notifyDataSetChanged()
//                recy_hot_topic.smoothScrollToPosition(position)
//            }
//            layout_content.visibility= View.VISIBLE
//            scroll_topic.visibility= View.GONE
//        }

//        scroll.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
//            override fun onScrollChange(p0: NestedScrollView?, p1: Int, p2: Int, p3: Int, p4: Int) {
//                if (scroll.getScrollY() == 0) {
//                    //顶部
////                    (fragments[communityTab.selectedTabPosition] as CommunityHotFragment).setScroll(false)
//                }else if (scroll.getScrollY()<0){
//                    (fragments[communityTab.selectedTabPosition] as CommunityHotFragment).setScroll(false)
//                }
//
//                var  contentView = scroll.getChildAt(0)
//                if (contentView != null && contentView.getMeasuredHeight() == (scroll.getScrollY() + scroll.getHeight())) {
//                    //底部
//                    (fragments[communityTab.selectedTabPosition] as CommunityHotFragment).setScroll(true)
//                }
//            }
//        })
        Click.viewClick(layout_search).subscribe {
            intentUtils.intentCommunitySearch()
        }

    }

    override fun layoutID(): Int = R.layout.fragment_community

    fun setRelease(){
        if (mFragmentAdapter!=null&&vp_community!=null) {
            show()
            vp_community.currentItem = 4
            (fragments[vp_community.currentItem] as CommunityHotFragment).setRelease()
        }
    }


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    fun  dip2px(context: Context, dipValue:Float) :Float{
        val scale = context.getResources().getDisplayMetrics().density;
        return  (dipValue * scale + 0.5f)
    }

    fun  px2dip(context: Context, dipValue:Float) :Float{
        val scale = context.getResources().getDisplayMetrics().density;
        return  (dipValue / scale + 0.5f)
    }

    fun scrollTop(){
        fragments[vp_community.currentItem].scrollTop()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun event(message:String){
        if (message=="1"){
            fragments[vp_community.currentItem].setLike(false)
        }else if (message=="2"){
            fragments[vp_community.currentItem].setLike(true)
        }
    }

}