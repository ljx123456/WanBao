package wanbao.yongchao.com.wanbao.ui.main.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import kotlinx.android.synthetic.main.dialog_screen_ye.*
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.base.BaseDialogFragment
import wanbao.yongchao.com.wanbao.utils.utils.Click

@SuppressLint("ValidFragment")
class YeScreenDialog(val screen:YeScreen,val type:String):BaseDialogFragment() {
    override fun setLayoutID(): Int = R.layout.dialog_screen_ye

    override fun isFullScreen(): Boolean = true

    override fun setLayoutData() {
        if (type!=""){
            when(type){
                "美食"->{
                    tv_sort1.setTextColor(Color.parseColor("#fcc725"))
                }

                "旅游"->{
                    tv_sort2.setTextColor(Color.parseColor("#fcc725"))
                }

                "娱乐"->{
                    tv_sort3.setTextColor(Color.parseColor("#fcc725"))
                }

                "生活"->{
                    tv_sort4.setTextColor(Color.parseColor("#fcc725"))
                }

                "文化"->{
                    tv_sort5.setTextColor(Color.parseColor("#fcc725"))
                }

//                "文化"->{
//                    tv_sort6.setTextColor(Color.parseColor("#fcc725"))
//                }
            }
        }else{
            tv_sort1.setTextColor(Color.parseColor("#d9ffffff"))
            tv_sort2.setTextColor(Color.parseColor("#d9ffffff"))
            tv_sort3.setTextColor(Color.parseColor("#d9ffffff"))
            tv_sort4.setTextColor(Color.parseColor("#d9ffffff"))
            tv_sort5.setTextColor(Color.parseColor("#d9ffffff"))
//            tv_sort6.setTextColor(Color.parseColor("#d9ffffff"))
        }
    }

    override fun clickListener() {
        Click.viewClick(dialogOver).subscribe { dismiss() }

        Click.viewClick(tv_sort1).subscribe {
            screen.setSort1()
            dismiss()
        }

        Click.viewClick(tv_sort2).subscribe {
            screen.setSort2()
            dismiss()
        }

        Click.viewClick(tv_sort3).subscribe {
            screen.setSort3()
            dismiss()
        }

        Click.viewClick(tv_sort4).subscribe {
            screen.setSort4()
            dismiss()
        }

        Click.viewClick(tv_sort5).subscribe {
            screen.setSort5()
            dismiss()
        }

//        Click.viewClick(tv_sort6).subscribe {
//            screen.setSort6()
//            dismiss()
//        }
    }

    interface YeScreen{
        fun setSort1()
        fun setSort2()
        fun setSort3()
        fun setSort4()
        fun setSort5()
//        fun setSort6()
    }
}