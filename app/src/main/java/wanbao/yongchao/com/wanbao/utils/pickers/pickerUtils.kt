package wanbao.yongchao.com.wanbao.utils.pickers

import android.graphics.Color
import cn.qqtheme.framework.picker.DatePicker
import cn.qqtheme.framework.picker.DateTimePicker
import cn.qqtheme.framework.picker.OptionPicker
import cn.qqtheme.framework.picker.TimePicker
import cn.qqtheme.framework.util.ConvertUtils
import cn.qqtheme.framework.widget.WheelView
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext
import wanbao.yongchao.com.wanbao.utils.utils.Click
import java.util.*

object pickerUtils {

//    fun joinData(): List<String> {
//        var list = ArrayList<String>()
//        list.add("公司职员")
//        list.add("企业CEO")
//        list.add("企业高管")
//        list.add("私营业主")
//        list.add("军人")
//        list.add("警察")
//        list.add("医生")
//        list.add("演绎人员")
//        list.add("教师")
//        list.add("空乘")
//        list.add("模特")
//        list.add("互联网")
//        list.add("投资人")
//        list.add("媒体工作者")
//        list.add("健身教练")
//        list.add("公务员")
//        list.add("自由职业")
//        list.add("其他")
//        return list
//    }
//
//    fun MoneyData(): List<String> {
//        var list = ArrayList<String>()
//        list.add("5000以下")
//        list.add("5000-8000")
//        list.add("8000-12000")
//        list.add("12000-15000")
//        list.add("15000-20000")
//        list.add("20000以上")
//        return list
//    }


    fun showPicker(picker: OptionPicker) {
        picker.setCanceledOnTouchOutside(true)
        picker.setDividerRatio(WheelView.DividerConfig.WRAP)
        picker.setOffset(3)
        picker.setSelectedIndex(1)
        picker.setSubmitTextColor(Color.parseColor("#281AF1"))
        picker.setSubmitTextSize(16)
        picker.setCancelTextColor(Color.parseColor("#50272121"))
        picker.setCancelTextSize(16)
        picker.setTopLineVisible(false)
        picker.setLineSpaceMultiplier(3.toFloat())
        picker.setTextColor(Color.parseColor("#272121"))
        picker.setUseWeight(false)
        picker.setDividerVisible(false)
        picker.setCycleDisable(true)
        picker.setTextSize(13)
    }


    fun showPicker(picker: DatePicker) {
        picker.setCanceledOnTouchOutside(true)
        picker.setDividerRatio(WheelView.DividerConfig.FILL)
        picker.setOffset(3)
        picker.setSubmitTextColor(Color.parseColor("#d9ffffff"))
        picker.setSubmitTextSize(14)
        picker.setCancelTextColor(Color.parseColor("#a6ffffff"))
        picker.setCancelTextSize(14)
        picker.setTopLineVisible(false)
        picker.setTopPadding(ConvertUtils.toPx(getContext(), 10f))
        picker.setLineSpaceMultiplier(3.toFloat())
        val c = Calendar.getInstance()//可以对每个时间域单独修改
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val date = c.get(Calendar.DATE)
        picker.setTextColor(Color.parseColor("#a6ffffff"))
        picker.setTextPadding(12)
        picker.setLabelTextColor(Color.parseColor("#a6ffffff"))
        picker.setUseWeight(true)
        picker.setRangeEnd(year, month, date)
        picker.setRangeStart(1950, 1, 1)
        picker.setDividerVisible(false)
        picker.setContentPadding(24,0)
        picker.setOnWheelListener(object :DatePicker.OnWheelListener{
            override fun onMonthWheeled(p0: Int, p1: String?) {

            }

            override fun onYearWheeled(p0: Int, p1: String?) {

            }

            override fun onDayWheeled(p0: Int, p1: String?) {

            }
        })
//        picker.setDividerColor(Color.parseColor("#FCC725"))
//        picker.setDividerRatio(WheelView.DividerConfig.FILL)
        picker.setCycleDisable(false)
        picker.setTextSize(18)
    }

    fun showTimePicker(picker: TimePicker) {
        picker.setCanceledOnTouchOutside(true)
        picker.setDividerRatio(WheelView.DividerConfig.FILL)
        picker.setOffset(3)
        picker.setSubmitTextColor(Color.parseColor("#d9ffffff"))
        picker.setSubmitTextSize(14)
        picker.setCancelTextColor(Color.parseColor("#a6ffffff"))
        picker.setCancelTextSize(14)
        picker.setTopLineVisible(false)
        picker.setTopPadding(ConvertUtils.toPx(getContext(), 10f))
        picker.setLineSpaceMultiplier(3.toFloat())
//        val c = Calendar.getInstance()//可以对每个时间域单独修改
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH) + 1
//        val date = c.get(Calendar.DATE)
        picker.setTextColor(Color.parseColor("#a6ffffff"))
        picker.setTextPadding(30)
        picker.setLabelTextColor(Color.parseColor("#a6ffffff"))
        picker.setUseWeight(false)
//        picker.setRangeEnd()
        picker.setRangeStart(1, 0)
        picker.setDividerVisible(false)
        picker.setContentPadding(40,0)

        picker.setOnWheelListener(object :TimePicker.OnWheelListener{
            override fun onHourWheeled(p0: Int, p1: String?) {

            }

            override fun onMinuteWheeled(p0: Int, p1: String?) {

            }

        })
//        picker.setDividerColor(Color.parseColor("#FCC725"))
//        picker.setDividerRatio(WheelView.DividerConfig.FILL)
        picker.setCycleDisable(false)
        picker.setTextSize(18)
    }

    fun showCustomTimePicker(picker: TimeCustomPicker){
        picker.setCanceledOnTouchOutside(true)
        picker.setDividerRatio(WheelView.DividerConfig.FILL)
        picker.setOffset(3)
        picker.setSubmitTextColor(Color.parseColor("#d9ffffff"))
        picker.setSubmitTextSize(14)
        picker.setCancelTextColor(Color.parseColor("#a6ffffff"))
        picker.setCancelTextSize(14)
        picker.setTopLineVisible(false)
        picker.setTopPadding(ConvertUtils.toPx(getContext(), 10f))
        picker.setLineSpaceMultiplier(3.toFloat())

        picker.setLabel("月","日","时")

        picker.setTextColor(Color.parseColor("#a6ffffff"))
        picker.setTextPadding(30)
        picker.setLabelTextColor(Color.parseColor("#a6ffffff"))
        picker.setUseWeight(false)
//        picker.setRangeEnd()
        picker.setRangeStartTime(1, 1,0)
//        picker.setTimeRangeStart(1,0)
        picker.setDividerVisible(false)
        picker.setContentPadding(40,0)

        picker.setOnWheelListener(object :TimeCustomPicker.OnWheelListener{
            override fun onMonthWheeled(var1: Int, var2: String?) {

            }

            override fun onDayWheeled(var1: Int, var2: String?) {

            }

            override fun onHourWheeled(var1: Int, var2: String?) {

            }
        })

        picker.setCycleDisable(false)
        picker.setTextSize(18)
    }



//    //  1已婚2未婚3离异4单身
//    fun MarriageData(): List<String> {
//        var list = ArrayList<String>()
//        list.add("已婚")
//        list.add("未婚")
//        list.add("离异")
//        list.add("单身")
//        return list
//    }


}