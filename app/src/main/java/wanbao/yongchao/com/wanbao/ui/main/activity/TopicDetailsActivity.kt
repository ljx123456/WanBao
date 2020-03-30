package wanbao.yongchao.com.wanbao.ui.main.activity

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Handler
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_topic_details.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseActivity
import wanbao.yongchao.com.wanbao.db.ACache
import wanbao.yongchao.com.wanbao.db.GreenDaoHelper
import wanbao.yongchao.com.wanbao.ui.main.dialog.LoginDialog
import wanbao.yongchao.com.wanbao.utils.utils.Click
import wanbao.yongchao.com.wanbao.ui.main.fragment.TopicFragment
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.CommunityBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.HomePageCommunityBody
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.HomePageCommunityPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.presenter.TopicDetailsPresenter
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.HomePageCommunityView
import wanbao.yongchao.com.wanbao.ui.main.mvp.view.TopicDetailsView
import wanbao.yongchao.com.wanbao.utils.dialog.ShareDialog
import wanbao.yongchao.com.wanbao.utils.dialog.ShowDialog
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.Toast


class TopicDetailsActivity:BaseActivity(), TopicDetailsView {
    override fun getTopicDetailRequest(data: CommunityBean) {
        if (data.data.size>0){
            if (data.data[0].topicDynamicNum>=200){
                tv_topic_num.visibility=View.VISIBLE
                tv_topic_num.text="累计动态：${data.data[0].topicDynamicNum}条"
            }else{
                tv_topic_num.visibility=View.GONE
            }
        }
    }


    private lateinit var fragment:TopicFragment
    private val presenter by lazy { TopicDetailsPresenter(this,this,this) }
    private var isLogin=false

    override fun openEventBus(): Boolean = false

    override fun getActivityLayout(): Int = R.layout.activity_topic_details

    override fun setActivityTitle() {
        tv_title.setText(intent.getStringExtra("topic"))
        tv_topic.setText(intent.getStringExtra("topic"))
    }

    override fun initActivityData() {

        var user = GreenDaoHelper.getDaoSessions().userDBDao
        var business= GreenDaoHelper.getDaoSessions().businessDBDao
        if (user != null||business!=null) {
            var info = user.loadAll()
            var inf = business.loadAll()
            if (info != null && info.size > 0) {
                isLogin=true
            }else if (inf != null && inf.size > 0) {
                isLogin=true
            }else{
                isLogin=false
            }
        } else{
            isLogin=false
        }

        presenter.getTopicDetails(CommunityBody(intent.getStringExtra("id"),1,1))

        fragment = TopicFragment("话题",intent.getStringExtra("id"))
        var transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_recy, fragment)
        transaction.commit()



//        ImageLoad.setMaskImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572671416795&di=460096682ecc0e48ea1a11aa643b1026&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F7%2F57fdfd339a7a5.jpg",iv_topic,this)



    }

    override fun clickListener() {
        refresh.setOnRefreshListener {
            var handler=Handler().postDelayed(Runnable {
                fragment.setRefresh()
                refresh.isRefreshing=false
            }, 1000)
        }
        var h=dip2px(this@TopicDetailsActivity,95f)

        scroll.setOnScrollChangeListener(object :NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView, p1: Int, p2: Int, p3: Int, p4: Int) {
                if (p2 == 0) {
                    //顶部
//                    manage.setmCanVerticalScroll(false)
                    iv_back.visibility=View.VISIBLE
                }else{
                    iv_back.visibility=View.GONE
                }

                var  contentView = scroll.getChildAt(0)
                if (contentView != null && contentView.getMeasuredHeight() == (scroll.getScrollY() + scroll.getHeight())) {
                    //底部
//                    manage.setmCanVerticalScroll(true)
                    Log.e("测试","滑动到底部了")
                    fragment.setLoadMore()//调用刷新控件对应的加载更多方法
                }


                var alpha=0f

                if (p2>=h){
                    alpha=1f
//                    manage.setmCanVerticalScroll(true)
                }else{
                    alpha=p2/h
//                    manage.setmCanVerticalScroll(false)
                }

                layout_title.alpha=alpha
                layout_title.visibility=View.VISIBLE
            }
        })

        Click.viewClick(iv_topic).subscribe {
            finish()
        }

        Click.viewClick(iv_title_back).subscribe { finish() }


        Click.viewClick(iv_add_topic).subscribe {
            if (isLogin) {
                var mCache = ACache.get(this)
                mCache.getAsString("HistoryRelease")
                if (mCache.getAsString("HistoryRelease") != null && !"".equals(mCache.getAsString("HistoryRelease"))) {
                    ShowDialog.showCustomDialogNoTitle(this, "当前有未发布的动态草稿，是否编辑重发？", "去编辑", "放弃草稿", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    dialog.dismiss()
                                    intentUtils.intentRelease()
                                }
                                DialogInterface.BUTTON_NEGATIVE -> {
                                    dialog.dismiss()
//                                var mCache = ACache.
                                    mCache.put("HistoryRelease", "")
                                    intentUtils.intentRelease(intent.getStringExtra("id"), intent.getStringExtra("topic"))
                                }
                            }
                        }
                    })
                } else {
                    intentUtils.intentRelease(intent.getStringExtra("id"), intent.getStringExtra("topic"))
                }
            }else{
                var dialog= LoginDialog()
                dialog.show(supportFragmentManager,"")
            }

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

}