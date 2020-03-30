package wanbao.yongchao.com.wanbao.view.allTextView;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class ShowAllSpan extends ClickableSpan {

    private OnAllSpanClickListener clickListener;
    private boolean isPressed = false;
    private Context context;
    private String name;

    public ShowAllSpan(Context context, OnAllSpanClickListener clickListener){
        this.context = context;
        this.clickListener = clickListener;
    }

    public ShowAllSpan(Context context, String name,OnAllSpanClickListener clickListener){
        this.context = context;
        this.clickListener = clickListener;
        this.name=name;
    }

    @Override
    public void onClick(View widget) {
        if (clickListener != null){
            if (name!=null&&name!=""){
                clickListener.onClickAt(widget,name);
            }else {
                clickListener.onClick(widget);
            }
        }
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public interface OnAllSpanClickListener{
        void onClick(View widget);
        void onClickAt(View widget,String name);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
//        if (isPressed){//点击颜色变化
//            ds.bgColor = context.getResources().getColor(android.R.color.darker_gray);
//        }else {
//            ds.bgColor = context.getResources().getColor(android.R.color.transparent);
//        }
        ds.setColor(Color.parseColor("#FCC725"));
        ds.setUnderlineText(false);
    }
}
