package wanbao.yongchao.com.wanbao.ui.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import wanbao.yongchao.com.wanbao.R;

public class SelectMapDialog extends Dialog {

    private View layoutView;
    private TextView gaodeBtn;
    private TextView baiduBtn;
    private TextView tencentBtn;
    private ImageView dialogOver;
    private SelectMapDialogFace dialogFace;

    public void setDialogFace(SelectMapDialogFace dialogFace) {
        this.dialogFace = dialogFace;
    }

    public SelectMapDialog(@NonNull Context context) {
        super(context, R.style.MyNewAlertDialog);
        initCameraDialog(context);
    }

    private void initCameraDialog(Context mContext) {
        layoutView = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_map, null);
        gaodeBtn = (TextView) layoutView.findViewById(R.id.tv_gaode);
        baiduBtn = (TextView) layoutView.findViewById(R.id.tv_baidu);
        tencentBtn = (TextView) layoutView.findViewById(R.id.tv_tencent);
        dialogOver =  layoutView.findViewById(R.id.iv_close);
        setContentView(layoutView);
        initDialogWindow();
        setOnClickListener();
    }

    private void initDialogWindow() {
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void showDialog() {
        show();
    }


    private void setOnClickListener() {
        gaodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogFace != null) {
                    dialogFace.gaodeBtn();
                    dismiss();
                }
            }
        });

        dialogOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        baiduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogFace != null) {
                    dialogFace.baiduBtn();
                    dismiss();
                }
            }
        });

        tencentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogFace != null) {
                    dialogFace.tencentBtn();
                    dismiss();
                }
            }
        });
    }

    public interface SelectMapDialogFace {
        void gaodeBtn();

        void baiduBtn();

        void  tencentBtn();
    }
}
