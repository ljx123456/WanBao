package wanbao.yongchao.com.wanbao.utils.pop;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.ScreenUtils;

import wanbao.yongchao.com.wanbao.R;


/**
 * Created by Administrator on 2015/8/3.
 */
public class PopupWindowHelper {

    private View popupView;
    private PopupWindow mPopupWindow;
    private Context context;
    private static final int TYPE_WRAP_CONTENT = 0, TYPE_MATCH_PARENT = 1;

    public PopupWindowHelper(View view,Context context) {
        popupView = view;
        this.context=context;
    }

    public void showAsDropDown(View anchor) {
        initPopupWindow(TYPE_MATCH_PARENT);
        mPopupWindow.showAsDropDown(anchor);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        initPopupWindow(12);
        mPopupWindow.showAsDropDown(anchor, -220, -20);
    }

    public void showAsDropDownLocation(View anchor, int xoff, int yoff) {//控件下方，位置随意
        initPopupWindow(TYPE_MATCH_PARENT);
        mPopupWindow.showAsDropDown(anchor, xoff, yoff);
    }


    public void showAtLocation(View parent, int gravity, int x, int y) {
        initPopupWindow(TYPE_WRAP_CONTENT);
        mPopupWindow.showAtLocation(parent, gravity, x, y);
    }
    public void showMatchAtLocation(View parent, int gravity, int x, int y) {
        initPopupWindow(3);
        mPopupWindow.showAtLocation(parent, gravity, x, y);
    }

    public void dismiss() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void showAsPopUp(View anchor) {
        showAsPopUp(anchor, 0, 0);
    }

    public void showAsPopUp(View anchor, int xoff, int yoff) {
        initPopupWindow(TYPE_WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.AnimationUpPopup);
        popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int height = popupView.getMeasuredHeight();
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, location[0] + xoff, location[1] - height + yoff);
    }

    public void showFromBottom(View anchor) {
        initPopupWindow(TYPE_MATCH_PARENT);
        mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
        mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
    }
    public void showFromBottomMatch(View anchor) {
        initPopupWindow(3);
        mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
        mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
    }

    public void showFromTop(View anchor) {
        initPopupWindow(TYPE_MATCH_PARENT);
        mPopupWindow.setAnimationStyle(R.style.AnimationFromTop);
        mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, 0, getStatusBarHeight());
    }



    /**
     * touch outside dismiss the popupwindow, default is ture
     *
     * @param isCancelable
     */
    public void setCancelable(boolean isCancelable) {
        if (isCancelable) {
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
        } else {
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setFocusable(false);
        }
    }

    public void initPopupWindow(int type) {
        if (type == TYPE_WRAP_CONTENT) {
            mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (type == TYPE_MATCH_PARENT) {
            mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        else if (type == 3) {
            mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
//            mPopupWindow = new PopupWindow(popupView, ScreenUtils.getScreenWidth() / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow = new PopupWindow(popupView, dip2px(context,100f), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
    }

    private int getStatusBarHeight() {
        return Math.round(25 * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    private int dip2px(Context context, Float dipValue){
        Float scale = context.getResources().getDisplayMetrics().density;
        return  (int)(dipValue * scale + 0.5f);
    }
}
