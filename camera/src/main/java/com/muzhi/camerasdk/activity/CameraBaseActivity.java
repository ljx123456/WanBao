package com.muzhi.camerasdk.activity;


import com.muzhi.camerasdk.R;
import com.muzhi.camerasdk.library.utils.MResource;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public abstract class CameraBaseActivity extends FragmentActivity {

    protected Context mContext;
    protected TextView  mActionBarTitle;
    protected ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
//			XLog.i(XLog.BASE, "onCreate fixOrientation when Oreo, result = " + result);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//不可横屏幕
        super.onCreate(savedInstanceState);
        mContext = this;




//		SystemBarTintManager tintManager = new SystemBarTintManager(this);
//		tintManager.setStatusBarTintEnabled(true);
//		tintManager.setStatusBarTintResource(R.color.camerasdk_action_bar);
//

    }

    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }


    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public void setActionBarTitle(String s) {
        if (mActionBarTitle == null) {
            mActionBarTitle = (TextView) findViewById(MResource.getIdRes(mContext, "camerasdk_actionbar_title"));
        }
        mActionBarTitle.setText(s);
    }

    //显示返回按钮
    public void showLeftIcon() {
        if (btn_back == null) {
            btn_back = (ImageView) findViewById(MResource.getIdRes(mContext, "camerasdk_btn_back"));
        }
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }


}
