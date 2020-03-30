package wanbao.yongchao.com.wanbao.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import wanbao.yongchao.com.wanbao.db.user;
import wanbao.yongchao.com.wanbao.ui.login.activity.LoginBindPhoneActivity;
import wanbao.yongchao.com.wanbao.ui.login.activity.LoginPhoneActivity;
import wanbao.yongchao.com.wanbao.utils.utils.Toast;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private String APP_ID = "wx8730168ee272799a";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api=WXAPIFactory.createWXAPI(this,APP_ID);
        api.handleIntent(getIntent(),this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent,this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType()==ConstantsAPI.COMMAND_SENDAUTH){//登陆
            if (baseResp.errCode==0){//同意
                String code = ((SendAuth.Resp) baseResp).code;
                Log.e("测试微信code",code);
                user.INSTANCE.setWxCode(code);
//                Intent intent=new Intent();
//                intent.setClass(this, LoginPhoneActivity.class);
//                intent.putExtra("code",code);
//                startActivity(intent);
                finish();
            }else if (baseResp.errCode==-4){//拒绝
                user.INSTANCE.setWxCode("");
                Toast.INSTANCE.Tips("用户拒绝");
                finish();
            }else if (baseResp.errCode==-2){//取消
                user.INSTANCE.setWxCode("");
                Toast.INSTANCE.Tips("用户取消");
                finish();
            }else {
                user.INSTANCE.setWxCode("");
                Toast.INSTANCE.Tips("微信授权失败请稍后重试");
                finish();
            }
        }else {
            if (baseResp.errCode==0){//同意
//                String code = ((SendAuth.Resp) baseResp).code;
//                Log.e("测试微信code",code);
//                user.INSTANCE.setWxCode(code);
                finish();
            }else if (baseResp.errCode==-4){//拒绝
//                user.INSTANCE.setWxCode("");
                Toast.INSTANCE.Tips("用户拒绝");
                finish();
            }else if (baseResp.errCode==-2){//取消
//                user.INSTANCE.setWxCode("");
                Toast.INSTANCE.Tips("用户取消");
                finish();
            }else {
//                user.INSTANCE.setWxCode("");
                Toast.INSTANCE.Tips("微信授权失败请稍后重试");
                finish();
            }
        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
