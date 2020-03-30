package wanbao.yongchao.com.wanbao.utils.utils

import android.graphics.Color
import android.text.*
import android.text.TextUtils.isEmpty
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllSpan
import java.util.regex.Pattern

object AtColorUtil{

    fun atMatcher(string: String): ArrayList<String> {
        var list = ArrayList<String>()
        if (!isEmpty(string)) {
            val reg = "@[\\u4e00-\\u9fa5_a-zA-Z0-9]{2,16}+ &h;"
            val compile = Pattern.compile(reg)
            val matcher = compile.matcher(string)
            while (matcher.find()) {
                var group = matcher.group()
                group = group.substring(0, group.length - 4)
                list.add(group)
            }
        }
        return list
    }
    fun getShowAllText(text:String,onAllSpanClickListener: ShowAllSpan.OnAllSpanClickListener):SpannableStringBuilder{
        var list=atMatcher(text)
        var str=text
        list.forEach {
//            Log.e("测试拆分",it)
//            str=str.replace("${it} &h;","<font color='#FCC725'>${it} </font>")
            str=str.replace("${it} &h;","${it} ")
        }

        var spannable=SpannableStringBuilder(str)

        list.forEach {
//            spannable.setSpan(TextClick(list[0].substring(1,list[0].length-1)),str.indexOf(list[0]),str.indexOf(list[0])+list[0].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(ShowAllSpan(getContext(),it.substring(1,it.length), onAllSpanClickListener),str.indexOf(it),str.indexOf(it)+it.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        }



//        Log.e("测试",str)
        return spannable
    }

    fun getDetailsText(text:String):SpannableStringBuilder{
        var list=atMatcher(text)
        var str=text
        list.forEach {
//            Log.e("测试拆分",it)
//            str=str.replace("${it} &h;","<font color='#FCC725'>${it} </font>")
            str=str.replace("${it} &h;","${it} ")
        }

        var spannable=SpannableStringBuilder(str)

        list.forEach {
            //            spannable.setSpan(TextClick(list[0].substring(1,list[0].length-1)),str.indexOf(list[0]),str.indexOf(list[0])+list[0].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(TextClick(it.substring(1,it.length-1)),str.indexOf(it),str.indexOf(it)+it.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        }



//        Log.e("测试",str)
        return spannable
    }

    private class TextClick(val name:String) : ClickableSpan() {
        override fun onClick(widget: View) {
            //在此处理点击事件
//            Log.e("测试",name)
//            Toast.Tips("点击了:"+name)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = Color.parseColor("#FCC725")
        }
    }
}