package wanbao.yongchao.com.wanbao.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import wanbao.yongchao.com.wanbao.R;


public class LoadDialog extends Dialog {


    private String tipMsg;
    private boolean canNotCancel;
    private TextView mShowMessage;
    private ImageView imageView;
    private static LoadDialog loadDialog;
    private Context context;


    public LoadDialog(final Context ctx, boolean canNotCancel, String tipMsg) {
        super(ctx);

        this.canNotCancel = canNotCancel;
        this.tipMsg = tipMsg;
        this.context=ctx;
        this.getContext().setTheme(android.R.style.Theme_InputMethod);
        setContentView(R.layout.dialog_loading_layout);
        if (!TextUtils.isEmpty(this.tipMsg)) {
            mShowMessage = findViewById(R.id.show_message);
            mShowMessage.setText(this.tipMsg);
        }
        imageView = findViewById(R.id.logingImage);
//        Glide.with(ctx).load(R.drawable.animation_loading).into(imageView);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.animation_loading)
                .error(R.drawable.animation_loading)
                .priority(Priority.HIGH);
        Glide.with(ctx)
                .load(R.drawable.animation_loading)
                .apply(options)
                .into(imageView);
        Window window = getWindow();
        //透明背景
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;
        window.setAttributes(attributesParams);
        window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

//        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canNotCancel) {
                Toast.makeText(getContext(), tipMsg, Toast.LENGTH_SHORT).show();
                return true;
            }
            try {
                dismiss();
//                try {
//                    if (context instanceof MainActivity){
//
//                    }else if (context instanceof LoginActivity){
//
//                    } else if (context instanceof PasswordLoginActivity){
//
//                    }
//                    else {
//                        ((Activity)context).finish();
//                    }

//                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    public static void show(Context context) {
        show(context, null, false);
    }


    public static void show(Context context, String message) {
        show(context, message, false);
    }


    private static void show(final Context context, String message, boolean isCancel) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            Log.e("点击","isShow");
            return;
        }
        loadDialog = new LoadDialog(context, isCancel, message);
        Log.e("点击","newShow");
        loadDialog.show();
    }

    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }

            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }
                }

                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadDialog = null;
        }
    }
}
