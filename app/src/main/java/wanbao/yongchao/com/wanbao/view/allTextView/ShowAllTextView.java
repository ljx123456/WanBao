package wanbao.yongchao.com.wanbao.view.allTextView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import wanbao.yongchao.com.wanbao.utils.utils.AtColorUtil;

public class ShowAllTextView extends TextView {

    private CallBack callBack;

    public interface CallBack{
        void complete();
    }

    public void setCallBack(CallBack callBack){
        this.callBack=callBack;
    }

    /**全文按钮点击事件*/
    private ShowAllSpan.OnAllSpanClickListener onAllSpanClickListener;
    private int maxShowLines = 0;  //最大显示行数

    public ShowAllTextView(Context context) {
        super(context);
    }

    public ShowAllTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**调用此方法才有效果*/
    public void setMyText(String text,String str) {
        super.setText(text);
        post(new Runnable() {
            @Override
            public void run() {
                addEllipsisAndAllAtEnd(str);
            }
        });

    }

    /**调用此方法才有效果*/
    public void setMyText(int resId,String str){
        setMyText(getContext().getResources().getText(resId).toString(),str);
    }

    /**超过规定行数时, 在文末添加 "...全文"*/
    private void addEllipsisAndAllAtEnd(String str){
        if (maxShowLines > 0 && maxShowLines < getLineCount()) {
            try {
//                this.setVisibility(GONE);
                int moreWidth = getTheTextNeedWidth(getPaint(), "..."+str);
                /**加上...全文 长度超过了textView的宽度, 则多减去5个字符*/
                if (getLayout().getLineRight(maxShowLines - 1) + moreWidth >= getLayout().getWidth()){
                    this.setText(getText().subSequence(0, getLayout().getLineEnd(maxShowLines - 1) - 5));
                    /**避免减5个字符后还是长度还是超出了,这里再减4个字符*/
                    if (getLayout().getLineRight(maxShowLines - 1) + moreWidth >= getLayout().getWidth()){
                        this.setText(getText().subSequence(0, getLayout().getLineEnd(maxShowLines - 1) - 4));
                    }
                }else {
                    this.setText(getText().subSequence(0, getLayout().getLineEnd(maxShowLines - 1)));
                }
                if (getText().toString().endsWith("\n") && getText().length() >= 1){
                    this.setText(getText().subSequence(0, getText().length() - 1));
                }
                this.append("...");
                SpannableString sb = new SpannableString(str);
                sb.setSpan(new ShowAllSpan(getContext(), onAllSpanClickListener), 0, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                this.append(sb);
                callBack.complete();
//                this.setVisibility(VISIBLE);
            }catch (Exception e){}
        }
    }

    public void setOnAllSpanClickListener(ShowAllSpan.OnAllSpanClickListener onAllSpanClickListener) {
        this.onAllSpanClickListener = onAllSpanClickListener;
    }

    public int getMaxShowLines() {
        return maxShowLines;
    }

    public void setMaxShowLines(int maxShowLines) {
        this.maxShowLines = maxShowLines;
    }

    //实现span的点击
    private ClickableSpan mPressedSpan = null;
    private boolean result = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CharSequence text = getText();
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPressedSpan = getPressedSpan(this, spannable, event);
                if (mPressedSpan != null){
                    if (mPressedSpan instanceof ShowAllSpan){
                        ((ShowAllSpan) mPressedSpan).setPressed(true);
                    }
                    Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan), spannable.getSpanEnd(mPressedSpan));
                    result = true;
                }else {
                    result = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                ClickableSpan mClickSpan = getPressedSpan(this, spannable, event);
                if (mPressedSpan != null && mPressedSpan != mClickSpan){
                    if (mPressedSpan instanceof ShowAllSpan){
                        ((ShowAllSpan) mPressedSpan).setPressed(false);
                    }
                    mPressedSpan = null;
                    Selection.removeSelection(spannable);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mPressedSpan != null){
                    if (mPressedSpan instanceof ShowAllSpan){
                        ((ShowAllSpan) mPressedSpan).setPressed(false);
                    }
                    mPressedSpan.onClick(this);
                }
                mPressedSpan = null;
                Selection.removeSelection(spannable);
                break;
        }
        return result;

    }

    private ClickableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {

        ClickableSpan mTouchSpan = null;

        int x = (int) event.getX();
        int y = (int) event.getY();
        x -= textView.getTotalPaddingLeft();
        x += textView.getScrollX();
        y -= textView.getTotalPaddingTop();
        y += textView.getScrollY();
        Layout layout = getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        ShowAllSpan[] spans = spannable.getSpans(off, off, ShowAllSpan.class);
        if (spans != null && spans.length > 0){
            mTouchSpan = spans[0];
        }
        return mTouchSpan;
    }


    /** 计算指定画笔下指定字符串需要的宽度*/
    public  int getTheTextNeedWidth(Paint thePaint, String text) {
        float[] widths = new float[text.length()];
        thePaint.getTextWidths(text, widths);
        int length = widths.length, nowLength = 0;
        for (int i = 0; i < length; i++) {
            nowLength += widths[i];
        }
        return nowLength;
    }
}
