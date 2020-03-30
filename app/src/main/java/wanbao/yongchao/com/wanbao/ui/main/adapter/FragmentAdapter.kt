package wanbao.yongchao.com.wanbao.ui.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class FragmentAdapter(fm: FragmentManager, var fragments: List<Fragment>, var titles: List<String>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles.get(position)
    }

    fun setNewFragments(fragments: List<Fragment>){
        this.fragments=fragments
        notifyDataSetChanged()
    }
}