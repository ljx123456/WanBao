package wanbao.yongchao.com.wanbao.ui.login.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import wanbao.yongchao.com.wanbao.R;

public class AgreementDialog extends Dialog {
    /**
     * 显示的图片
     */
//    private ImageView imageIv ;

    /**
     * 显示的标题
     */
    private TextView titleTv ;

    /**
     * 显示的消息
     */
    private TextView messageTv ;

    /**
     * 确认和取消按钮
     */
    private Button negtiveBn ,positiveBn;

    /**
     * 按钮之间的分割线
     */
//    private View columnLineView ;
    public AgreementDialog(Context context) {
        super(context, R.style.Dialog);
    }

    /**
     * 都是内容数据
     */
    private String message;
    private String title;
    private String positive,negtive ;
    private int imageResId = -1 ;

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register_agreement);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        //如果用户自定了title和message
//        if (!TextUtils.isEmpty(title)) {
//            titleTv.setText(title);
//            titleTv.setVisibility(View.VISIBLE);
//        }else {
//            titleTv.setVisibility(View.GONE);
//        }
//        if (!TextUtils.isEmpty(message)) {
//            messageTv.setText(message);
//        }
//        //如果设置按钮的文字
//        if (!TextUtils.isEmpty(positive)) {
//            positiveBn.setText(positive);
//        }else {
//            positiveBn.setText("确定");
//        }
//        if (!TextUtils.isEmpty(negtive)) {
//            negtiveBn.setText(negtive);
//        }else {
//            negtiveBn.setText("取消");
//        }
//
//        if (imageResId!=-1){
//            imageIv.setImageResource(imageResId);
//            imageIv.setVisibility(View.VISIBLE);
//        }else {
//            imageIv.setVisibility(View.GONE);
//        }
//        /**
//         * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
//         */
//        if (isSingle){
//            columnLineView.setVisibility(View.GONE);
//            negtiveBn.setVisibility(View.GONE);
//        }else {
//            negtiveBn.setVisibility(View.VISIBLE);
//            columnLineView.setVisibility(View.VISIBLE);
//        }
        //处理协议点击事件与颜色
        //这个一定要记得设置，不然点击不生效
        messageTv.setText("感谢您使用晚豹APP！\n我们非常重视您的个人信息和隐私保护。为了更好地保证您的个人权益，在您注册我们的产品时，请您认真阅读《晚豹APP注册协议》和《晚豹APP隐私政策》的全部内容，点击同意并接受全部条款后开始使用我们的产品和服务");
        SpannableStringBuilder spannable = new SpannableStringBuilder(messageTv.getText().toString());
        messageTv.setMovementMethod(LinkMovementMethod.getInstance());
        spannable.setSpan(new TextClick(),61,72 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TextClick2(),73,84 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        messageTv.setText(spannable);
    }

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            if ( onClickBottomListener!= null) {
                onClickBottomListener.onAgreement1Click();
            }
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#25A3FC"));
        }
    }

    private class TextClick2 extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            if ( onClickBottomListener!= null) {
                onClickBottomListener.onAgreement2Click();
            }
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#25A3FC"));
        }
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        negtiveBn = (Button) findViewById(R.id.btn_no);
        positiveBn = (Button) findViewById(R.id.btn_yes);
//        titleTv = (TextView) findViewById(R.id.title);
        messageTv = (TextView) findViewById(R.id.tv_content);
//        imageIv = (ImageView) findViewById(R.id.image);
//        columnLineView = findViewById(R.id.column_line);
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;
    public AgreementDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }
    public interface OnClickBottomListener{
        /**
         * 点击确定按钮事件
         */
        public void onPositiveClick();
        /**
         * 点击取消按钮事件
         */
        public void onNegtiveClick();

        /**
         * 点击协议1事件
         */
        public void onAgreement1Click();


        /**
         * 点击协议2事件
         */
        public void onAgreement2Click();
    }

    public String getMessage() {
        return message;
    }

    public AgreementDialog setMessage(String message) {
        this.message = message;
        return this ;
    }

    public String getTitle() {
        return title;
    }

    public AgreementDialog setTitle(String title) {
        this.title = title;
        return this ;
    }

    public String getPositive() {
        return positive;
    }

    public AgreementDialog setPositive(String positive) {
        this.positive = positive;
        return this ;
    }

    public String getNegtive() {
        return negtive;
    }

    public AgreementDialog setNegtive(String negtive) {
        this.negtive = negtive;
        return this ;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public AgreementDialog setSingle(boolean single) {
        isSingle = single;
        return this ;
    }

    public AgreementDialog setImageResId(int imageResId) {
        this.imageResId = imageResId;
        return this ;
    }

}
