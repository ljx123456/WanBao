package wanbao.yongchao.com.wanbao.base

import android.content.DialogInterface
import android.view.View
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.event.LoginStateChangeEvent

import com.blankj.utilcode.util.ActivityUtils

import org.greenrobot.eventbus.EventBus
import wanbao.yongchao.com.wanbao.utils.dialog.LoadDialog


open abstract class BaseFragment : FatherFragment() {


    override fun onCreateFragment(contentView: View?) {
        openActivityEventBus()
        setLayoutTitle()
        initFragmentData()
        setFragmentListener()
//        //注册sdk的event用于接收各种event事件
        JMessageClient.registerEventReceiver(this)
    }

    protected abstract fun openEventBus(): Boolean

    protected abstract fun setLayoutTitle()

    protected abstract fun initFragmentData()

    protected abstract fun setFragmentListener()

    fun show() {
        LoadDialog.show(mContext)
    }

    fun dismiss() {
        LoadDialog.dismiss(mContext)
    }

    private fun openActivityEventBus() {
        if (openEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    private fun closeActivityEventBus() {
        if (openEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        closeActivityEventBus()
        JMessageClient.unRegisterEventReceiver(this)
    }

    fun onEventMainThread(event: LoginStateChangeEvent) {
//        val reason = event.reason
//        val myInfo = event.myInfo
//        if (myInfo != null) {
//            val path: String
//            val avatar = myInfo.avatarFile
//            if (avatar != null && avatar.exists()) {
//                path = avatar.absolutePath
//            } else {
//                path = FileHelper.getUserAvatarPath(myInfo.userName)
//            }
//            SharePreferenceManager.setCachedUsername(myInfo.userName)
//            SharePreferenceManager.setCachedAvatarPath(path)
//            //            JMessageClient.logout();
//            var dialog= OtherLoginDialog()
//            dialog.show(activity!!.supportFragmentManager,"")
//            DbUtils.DelUser()
//            DbUtils.delMerchat()
//            DrinkUtils.deleteALLDrinks()
//            ServeUtils.deleteALLServe()
//            OrderServeUtils.deleteAllOrder()
////                    JMessageClient.logout()
//            user.setNum("0")
//            user.setOrderNo("")
//            user.setType("1")
//            user.setRoomType("0")
//            user.setBrokerType("0")
//            user.setYueID("1")
//            user.setYueName("")
//            user.setOrderID("")
//            user.setCityID("1")
//            user.setCity("")
//            user.setRoomMoney("0.00")
//        }
////        when (reason) {
////            LoginStateChangeEvent.Reason.user_logout ->
////
////                //                JMessageClient.logout();
////                finish()
////            LoginStateChangeEvent.Reason.user_password_change -> {
////                val intent = Intent(this@BaseActivity, LoginActivity::class.java)
////                startActivity(intent)
////            }
////        }
//        //                View.OnClickListener listener = new View.OnClickListener() {
//        //                    @Override
//        //                    public void onClick(View v) {
//        //                        switch (v.getId()) {
//        //                            case R2.id.jmui_cancel_btn:
//        //                                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
//        //                                startActivity(intent);
//        //                                break;
//        //                            case R2.id.jmui_commit_btn:
//        //                                JMessageClient.login(SharePreferenceManager.getCachedUsername(), SharePreferenceManager.getCachedPsw(), new BasicCallback() {
//        //                                    @Override
//        //                                    public void gotResult(int responseCode, String responseMessage) {
//        //                                        if (responseCode == 0) {
//        //                                            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
//        //                                            startActivity(intent);
//        //                                        }
//        //                                    }
//        //                                });
//        //                                break;
//        //                        }
//        //                    }
//        //                };
//        //                dialog = DialogCreator.createLogoutStatusDialog(BaseActivity.this, "您的账号在其他设备上登陆", listener);
//        //                dialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
//        //                dialog.setCanceledOnTouchOutside(false);
//        //                dialog.show();
    }


}
