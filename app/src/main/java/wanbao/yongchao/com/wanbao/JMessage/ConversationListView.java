package wanbao.yongchao.com.wanbao.JMessage;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import jiguang.chat.R;
import wanbao.yongchao.com.wanbao.ui.news.utils.TagUtil;

/**
 * Created by ${chenyn} on 2017/3/13.
 */

public class ConversationListView {
    private View mConvListFragment;
    private ListView mConvListView = null;
    private TextView mTitle;
    private ImageButton mCreateGroup;

    private LinearLayout mHeader;
    private RelativeLayout mLoadingHeader;
    private ImageView mLoadingIv;
    private LinearLayout mLoadingTv;
    private Context mContext;
    private RelativeLayout mNull_conversation;
    private LinearLayout mSearch;
//    private TextView mAllUnReadMsg;
    private ConversationListFragment mFragment;

    private LinearLayout mHeaderNotice;
    private TextView tv_content;
    private TextView tv_time;
    private TextView tv_num;
    private RelativeLayout notice;
    private View v;
    private boolean hasNotice;

    public ConversationListView(View view, Context context, ConversationListFragment fragment,boolean flag) {
        this.mConvListFragment = view;
        this.mContext = context;
        this.mFragment = fragment;
        this.hasNotice=flag;
    }

    public void initModule() {
//        EventBus.getDefault().register(this);
        mConvListView = (ListView) mConvListFragment.findViewById(R.id.conv_list_view);
//        mCreateGroup = (ImageButton) mConvListFragment.findViewById(R.id.create_group_btn);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeader = (LinearLayout) inflater.inflate(R.layout.conv_list_head_view, mConvListView, false);
//        mSearchHead = (LinearLayout) inflater.inflate(R.layout.conversation_head_view, mConvListView, false);
        mHeaderNotice=  (LinearLayout) inflater.inflate(wanbao.yongchao.com.wanbao.R.layout.layout_news_header, mConvListView, false);
        mLoadingHeader = (RelativeLayout) inflater.inflate(R.layout.jmui_drop_down_list_header, mConvListView, false);
        mLoadingIv = (ImageView) mLoadingHeader.findViewById(R.id.jmui_loading_img);
        mLoadingTv = (LinearLayout) mLoadingHeader.findViewById(R.id.loading_view);
//        mSearch = (LinearLayout) mSearchHead.findViewById(R.id.search_title);

        tv_content=mHeaderNotice.findViewById(wanbao.yongchao.com.wanbao.R.id.tv_content);
        tv_time=mHeaderNotice.findViewById(wanbao.yongchao.com.wanbao.R.id.tv_time);
        tv_num=mHeaderNotice.findViewById(wanbao.yongchao.com.wanbao.R.id.tv_num);
        notice=mHeaderNotice.findViewById(wanbao.yongchao.com.wanbao.R.id.layout_notice);

        mNull_conversation =  mConvListFragment.findViewById(R.id.null_conversation);
//        mAllUnReadMsg = (TextView) mFragment.getActivity().findViewById(R.id.all_unread_number);
        mConvListView.addHeaderView(mLoadingHeader);
//        mConvListView.addHeaderView(mSearchHead);

        mConvListView.addHeaderView(mHeaderNotice);
        if (!hasNotice) {
            mHeaderNotice.setVisibility(View.GONE);
            notice.setVisibility(View.GONE);
        }else {
            mHeaderNotice.setVisibility(View.VISIBLE);
            notice.setVisibility(View.VISIBLE);
            tv_content.setText(TagUtil.INSTANCE.getContent());
            tv_time.setText(TagUtil.INSTANCE.getTime());
            if (TagUtil.INSTANCE.getNum().equals("0")){
                tv_num.setVisibility(View.GONE);
            }else {
                tv_num.setVisibility(View.VISIBLE);
                tv_num.setText(TagUtil.INSTANCE.getNum());
            }

        }
        mConvListView.addHeaderView(mHeader);
        v=inflater.inflate(wanbao.yongchao.com.wanbao.R.layout.layout_community_footer,mConvListView,false);
        mConvListView.addFooterView(v);
    }

    public void setConvListAdapter(ListAdapter adapter) {
        mConvListView.setAdapter(adapter);
    }


    public void setListener(View.OnClickListener onClickListener) {
        notice.setOnClickListener(onClickListener);
        v.setOnClickListener(onClickListener);
//        mSearch.setOnClickListener(onClickListener);
//        mCreateGroup.setOnClickListener(onClickListener);
    }

    public void setLongListener(View.OnLongClickListener onLongClickListener){
        notice.setOnLongClickListener(onLongClickListener);
    }

    public void setItemListeners(AdapterView.OnItemClickListener onClickListener) {
        mConvListView.setOnItemClickListener(onClickListener);
    }

    public void setLongClickListener(AdapterView.OnItemLongClickListener listener) {
        mConvListView.setOnItemLongClickListener(listener);
    }


    public void showHeaderView() {
        mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.VISIBLE);
        mHeader.findViewById(R.id.check_network_hit).setVisibility(View.VISIBLE);
    }

    public void dismissHeaderView() {
        mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.GONE);
        mHeader.findViewById(R.id.check_network_hit).setVisibility(View.GONE);
    }

    public void showNoticHeaderView(String content,String time,String num){
        mHeaderNotice.setVisibility(View.VISIBLE);
        notice.setVisibility(View.VISIBLE);
        tv_content.setText(content);
        tv_time.setText(time);
        if (num.equals("0")){
            tv_num.setVisibility(View.GONE);
        }else {
            tv_num.setVisibility(View.VISIBLE);
            tv_num.setText(num);
        }

    }

    public void dismissNoticHeaderView(){
        mFragment.delSystem();
        mHeaderNotice.setVisibility(View.GONE);
        notice.setVisibility(View.GONE);
    }


    public void showLoadingHeader() {
        mLoadingIv.setVisibility(View.VISIBLE);
        mLoadingTv.setVisibility(View.VISIBLE);
        AnimationDrawable drawable = (AnimationDrawable) mLoadingIv.getDrawable();
        drawable.start();
    }

    public void dismissLoadingHeader() {
        mLoadingIv.setVisibility(View.GONE);
        mLoadingTv.setVisibility(View.GONE);
    }

    public void setNullConversation(boolean isHaveConv) {
        if (isHaveConv) {
            if (mNull_conversation!=null&&mConvListView!=null) {
                mNull_conversation.setVisibility(View.GONE);
                mConvListView.setVisibility(View.VISIBLE);
            }
        } else {
            if (!TagUtil.INSTANCE.getTag() &&mNull_conversation!=null&&mConvListView!=null){
                mNull_conversation.setVisibility(View.VISIBLE);
                mConvListView.setVisibility(View.GONE);
            }
        }
    }

    public void setHasNotice(boolean flag){
        this.hasNotice=flag;
    }


    public void setUnReadMsg(final int count) {
        ThreadUtil.runInUiThread(new Runnable() {
            @Override
            public void run() {
////                if (mAllUnReadMsg != null) {
////                    if (count > 0) {
////                        mAllUnReadMsg.setVisibility(View.VISIBLE);
////                        if (count < 100) {
////                            mAllUnReadMsg.setText(count + "");
////                        } else {
////                            mAllUnReadMsg.setText("99+");
////                        }
////                    } else {
////                        mAllUnReadMsg.setVisibility(View.GONE);
////                    }
////                }
            }
        });
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(String s) {
        Log.e("测试eventbus",s);
        if (s.equals("新聊天")){
            mNull_conversation.setVisibility(View.GONE);
            mConvListView.setVisibility(View.VISIBLE);
        }
    }

}
