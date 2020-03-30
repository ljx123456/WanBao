package wanbao.yongchao.com.wanbao.JMessage;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.blankj.utilcode.util.NetworkUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import jiguang.chat.R2;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.activity.SearchContactsActivity;
import jiguang.chat.application.JGApplication;
import jiguang.chat.utils.DialogCreator;
import jiguang.chat.utils.SortConvList;
import jiguang.chat.utils.SortTopConvList;
import wanbao.yongchao.com.wanbao.R;
import wanbao.yongchao.com.wanbao.ui.news.activity.NoticeActivity;
import wanbao.yongchao.com.wanbao.utils.utils.Toast;


/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class ConversationListController implements View.OnClickListener,View.OnLongClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ConversationListView mConvListView;
    private ConversationListFragment mContext;
    private int mWidth;
    private ConversationListAdapter mListAdapter;
    private List<Conversation> mDatas = new ArrayList<Conversation>();
    private Dialog mDialog;

    public ConversationListController(ConversationListView listView, ConversationListFragment context,
                                      int width) {
        this.mConvListView = listView;
        this.mContext = context;
        this.mWidth = width;
        initConvListAdapter();
    }

    List<Conversation> topConv = new ArrayList<>();
    List<Conversation> forCurrent = new ArrayList<>();
    List<Conversation> delFeedBack = new ArrayList<>();

    private void initConvListAdapter() {
        forCurrent.clear();
        topConv.clear();
        delFeedBack.clear();
        int i = 0;
        mDatas = JMessageClient.getConversationList();
        if (mDatas != null && mDatas.size() > 0) {
            Log.e("测试消息 大小",JMessageClient.getConversationList().size()+"");
            Log.e("测试","有消息");
            mConvListView.setNullConversation(true);
            SortConvList sortConvList = new SortConvList();
            Collections.sort(mDatas, sortConvList);
            for (Conversation con : mDatas) {
                if (con.getTargetId().equals("feedback_Android")) {
                    delFeedBack.add(con);
                }
                if (!TextUtils.isEmpty(con.getExtra())) {
                    forCurrent.add(con);
                }
            }
            topConv.addAll(forCurrent);
            mDatas.removeAll(forCurrent);
            mDatas.removeAll(delFeedBack);

        } else {
            mConvListView.setNullConversation(false);
        }
        if (topConv != null && topConv.size() > 0) {
            SortTopConvList top = new SortTopConvList();
            Collections.sort(topConv, top);
            for (Conversation conv : topConv) {
                mDatas.add(i, conv);
                i++;
            }
        }
        mListAdapter = new ConversationListAdapter(mContext.getActivity(), mDatas, mConvListView);
        mConvListView.setConvListAdapter(mListAdapter);
    }


    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
////            case R2.id.create_group_btn:
//////                mContext.showPopWindow();
////                break;
//            case R2.id.search_title:
//                Intent intent = new Intent();
//                intent.setClass(mContext.getActivity(), SearchContactsActivity.class);
//                mContext.startActivity(intent);
//                break;
//        }
        if (v.getId()==R.id.layout_notice){
//            Toast.INSTANCE.Tips("点击了");
            Intent intent = new Intent();
            intent.setClass(mContext.getActivity(), NoticeActivity.class);
            mContext.startActivity(intent);
        }else {

        }
    }

    @Override
    public boolean onLongClick(View v) {

        if (v.getId()==R.id.layout_notice){
//            Toast.INSTANCE.Tips("长按了");
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.jmui_delete_conv_ll) {
                        mConvListView.dismissNoticHeaderView();

                        mListAdapter.notifyDataSetChanged();
                        mDialog.dismiss();
                    }
                }
            };
            mDialog = DialogCreator.createDelNoticeDialog(mContext.getActivity(), listener);
            mDialog.show();
            mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
        }else {

        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击会话条目
        if(NetworkUtils.isConnected()) {
            if (position > 0) {
                //这里-3是减掉添加的三个headView
                Intent intent = new Intent();
                Conversation conv = mDatas.get(position - 3);
                intent.putExtra(JGApplication.CONV_TITLE, conv.getTitle());
                //群聊
                if (conv.getType() == ConversationType.group) {
                    if (mListAdapter.includeAtMsg(conv)) {
                        intent.putExtra("atMsgId", mListAdapter.getAtMsgId(conv));
                    }

                    if (mListAdapter.includeAtAllMsg(conv)) {
                        intent.putExtra("atAllMsgId", mListAdapter.getatAllMsgId(conv));
                    }
                    long groupId = ((GroupInfo) conv.getTargetInfo()).getGroupID();
                    intent.putExtra(JGApplication.GROUP_ID, groupId);
                    intent.putExtra(JGApplication.DRAFT, getAdapter().getDraft(conv.getId()));
                    intent.setClass(mContext.getActivity(), ChatActivity.class);
                    mContext.getActivity().startActivity(intent);
                    return;
                    //单聊
                } else {
                    String targetId = ((UserInfo) conv.getTargetInfo()).getUserName();
                    intent.putExtra(JGApplication.TARGET_ID, targetId);
                    intent.putExtra(JGApplication.TARGET_APP_KEY, conv.getTargetAppKey());
                    intent.putExtra(JGApplication.DRAFT, getAdapter().getDraft(conv.getId()));
                }
                intent.setClass(mContext.getActivity(), ChatActivity.class);
                mContext.getContext().startActivity(intent);

            }
        }
    }

    public ConversationListAdapter getAdapter() {
        return mListAdapter;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        if (position >=3) {
            final Conversation conv = mDatas.get(position - 3);
            if (conv != null) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.jmui_delete_conv_ll) {
                            if (conv.getType() == ConversationType.group) {
                                JMessageClient.deleteGroupConversation(((GroupInfo) conv.getTargetInfo()).getGroupID());
                            } else {
                                JMessageClient.deleteSingleConversation(((UserInfo) conv.getTargetInfo()).getUserName());
                            }
                            mDatas.remove(position - 3);
                            if (mDatas.size() > 0) {
                                mConvListView.setNullConversation(true);
                            } else {
                                mConvListView.setNullConversation(false);
                            }
                            mListAdapter.notifyDataSetChanged();
                            mDialog.dismiss();
                        }
                    }
                };
                mDialog = DialogCreator.createDelConversationDialog(mContext.getActivity(), listener, TextUtils.isEmpty(conv.getExtra()));
                mDialog.show();
                mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
            }
        }
        return true;
    }

    public void setNull(){
        mConvListView.setNullConversation(false);
    }


}
