package wanbao.yongchao.com.wanbao.utils

import android.view.LayoutInflater
import android.view.View
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext


object layoutUtils {

    fun getNoneCommunity(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_community, null)
    }
//
    fun getNoneSearch(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_search, null)
    }

    fun getNoneFans(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_fans, null)
    }

    fun getNoneFocus(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_focus, null)
    }

    fun getNoneMineFans(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_mine_fans, null)
    }

    fun getNoneMineFocus(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_mine_focus, null)
    }

    fun getNoneCollect(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_collect, null)
    }

    fun getNoneWant(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_want, null)
    }

    fun getNoneBlacklist(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_blacklist, null)
    }

    fun getNoneNoticeFans(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_notice_fans, null)
    }

    fun getNoneNoticeLike(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_notice_like, null)
    }

    fun getNoneNoticeAt(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_notice_at, null)
    }

    fun getNoneNoticeComment(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_notice_comment, null)
    }

    fun getNoneActivities(): View {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_none_activities, null)
    }

}