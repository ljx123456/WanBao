package wanbao.yongchao.com.wanbao.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by Administrator on 2017/10/19 0019.
 */

open abstract class FatherFragment : Fragment() {

    var contentView: View? = null
    var mContext: Context? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.mContext = activity
        if (contentView == null) {
            contentView = inflater.inflate(layoutID(), container, false)
            onCreateFragment(contentView)
            removeViewGroup()
        }
        return contentView
    }

    override fun getView(): View? {
        return contentView
    }

    private fun removeViewGroup() {
        var viewGroup: ViewGroup? = contentView?.parent as? ViewGroup
        if (viewGroup!=null) {
            viewGroup?.removeView(contentView)
        }
    }

    protected abstract fun layoutID(): Int


    protected abstract fun onCreateFragment(contentView: View?)
}
