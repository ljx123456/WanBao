package wanbao.yongchao.com.wanbao.view.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NonScrollGridView extends GridView {

    public NonScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollGridView(Context context) {
        super(context);
    }

    public NonScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}