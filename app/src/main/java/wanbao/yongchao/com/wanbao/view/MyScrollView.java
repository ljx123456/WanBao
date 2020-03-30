package wanbao.yongchao.com.wanbao.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import static android.view.FrameMetrics.ANIMATION_DURATION;

public class MyScrollView extends ScrollView {
    private View mView;

    private int mLastY; //上一次y轴方向操作的坐标位置

    private Rect mRect = new Rect(); // 用来记录临界状态的左上右下

    private boolean isFinishAnimation = true; //判断是否结束动画

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        //关键点在这
//        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.onInterceptTouchEvent(ev);
//    }

    /**
     * 获取子视图
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            mView = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mView == null || !isFinishAnimation) {
            return super.onTouchEvent(event);
        }
        int eventY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = eventY - mLastY; // 微小的偏移量
                if (isNeedMove()) {
                    if (mRect.isEmpty()) {
                        mRect.set(mView.getLeft(),mView.getTop(),mView.getRight(),mView.getBottom()); //记录临界状态的左上右下
                    }
                    mView.layout(mView.getLeft(),mView.getTop() + dy / 2, mView.getRight(),mView.getBottom() + dy / 2);
                }
                mLastY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                //使用平移动画
                int translateY = mView.getBottom() - mRect.bottom;
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -translateY);
                translateAnimation.setDuration(200);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        isFinishAnimation = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isFinishAnimation = true;
                        mView.clearAnimation(); //清除动画
                        mView.layout(mRect.left,mRect.top,mRect.right,mRect.bottom);
                        mRect.setEmpty();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                //启动动画
                mView.startAnimation(translateAnimation);
                break;
        }

        return super.onTouchEvent(event);
    }

    private boolean isNeedMove() {
        int measuredHeight = mView.getMeasuredHeight(); //获取子视图的高度
        int scrollViewMeasuredHeight = this.getMeasuredHeight(); //获取布局的高度
        Log.e("TAG","measuredHeight" + measuredHeight);
        Log.e("TAG","scrollViewMeasuredHeight" + scrollViewMeasuredHeight);
        int dy = measuredHeight - scrollViewMeasuredHeight; //dy>= 0的
        int scrollY = this.getScrollY(); //获取用户在y轴方向上的偏移量往上滑+ 往下滑-
        if (scrollY <= 0 || scrollY >= dy) {
            return true; //按照自定义的DefinitionScrollView方式处理
        }
        //其他处在临界范围内的返回false 表示仍按照ScrollView的方式处理
        return false;
    }
}

