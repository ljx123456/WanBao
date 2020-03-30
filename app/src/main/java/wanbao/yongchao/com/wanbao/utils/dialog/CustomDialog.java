package wanbao.yongchao.com.wanbao.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wanbao.yongchao.com.wanbao.R;


/**
 * Created by dengbo on 2016/9/4.
 */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private int id;
        private int position;
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public int getId() {
            return id;
        }

        public int getPosition() {
            return position;
        }

        public Builder setPosition(int position) {
            this.position = position;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;

        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
            dialog.setCancelable(false);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setCanceledOnTouchOutside(false);
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            if (message != null) {
                ((TextView) layout.findViewById(R.id.content)).setText(message);
            }
            if (title != null) {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
                ((TextView) layout.findViewById(R.id.title)).setVisibility(View.VISIBLE);
                layout.findViewById(R.id.view_title).setVisibility(View.VISIBLE);
            } else {
                ((TextView) layout.findViewById(R.id.title)).setVisibility(View.GONE);
                layout.findViewById(R.id.view_title).setVisibility(View.GONE);
            }

            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.no))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    (layout.findViewById(R.id.no))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                layout.findViewById(R.id.no).setVisibility(
                        View.GONE);
//                layout.findViewById(R.id.line).setVisibility(View.GONE);
            }
            if (negativeButtonText != null) {
                ((TextView) layout.findViewById(R.id.yes))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    (layout.findViewById(R.id.yes))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                layout.findViewById(R.id.yes).setVisibility(
                        View.GONE);
//                layout.findViewById(R.id.line).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
