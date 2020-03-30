package wanbao.yongchao.com.wanbao.utils.pickers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import cn.qqtheme.framework.picker.WheelPicker;
import cn.qqtheme.framework.util.DateUtils;
import cn.qqtheme.framework.util.LogUtils;
import cn.qqtheme.framework.widget.WheelView;

public class DateTimePicker extends WheelPicker {
    public static final int NONE = -1;
    public static final int YEAR_MONTH_DAY = 0;
    public static final int YEAR_MONTH = 1;
    public static final int MONTH_DAY = 2;
    public static final int HOUR_24 = 3;
    /** @deprecated */
    @Deprecated
    public static final int HOUR_OF_DAY = 3;
    public static final int HOUR_12 = 4;
    /** @deprecated */
    @Deprecated
    public static final int HOUR = 4;
    private ArrayList<String> years;
    private ArrayList<String> months;
    private ArrayList<String> days;
    private ArrayList<String> hours;
    private ArrayList<String> minutes;
    private String yearLabel;
    private String monthLabel;
    private String dayLabel;
    private String hourLabel;
    private String minuteLabel;
    private int selectedYearIndex;
    private int selectedMonthIndex;
    private int selectedDayIndex;
    private String selectedHour;
    private String selectedMinute;
    private DateTimePicker.OnWheelListener onWheelListener;
    private DateTimePicker.OnDateTimePickListener onDateTimePickListener;
    private int dateMode;
    private int timeMode;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private int textSize;
    private boolean resetWhileWheel;

    public DateTimePicker(Activity activity, int timeMode) {
        this(activity, 0, timeMode);
    }

    public DateTimePicker(Activity activity, int dateMode, int timeMode) {
        super(activity);
        this.years = new ArrayList();
        this.months = new ArrayList();
        this.days = new ArrayList();
        this.hours = new ArrayList();
        this.minutes = new ArrayList();
        this.yearLabel = "年";
        this.monthLabel = "月";
        this.dayLabel = "日";
        this.hourLabel = "时";
        this.minuteLabel = "分";
        this.selectedYearIndex = 0;
        this.selectedMonthIndex = 0;
        this.selectedDayIndex = 0;
        this.selectedHour = "";
        this.selectedMinute = "";
        this.dateMode = 0;
        this.timeMode = 3;
        this.startYear = 2010;
        this.startMonth = 1;
        this.startDay = 1;
        this.endYear = 2020;
        this.endMonth = 12;
        this.endDay = 31;
        this.startMinute = 0;
        this.endMinute = 59;
        this.textSize = 16;
        this.resetWhileWheel = true;
        if (dateMode == -1 && timeMode == -1) {
            throw new IllegalArgumentException("The modes are NONE at the same time");
        } else {
            if (dateMode == 0 && timeMode != -1) {
                if (this.screenWidthPixels < 720) {
                    this.textSize = 14;
                } else if (this.screenWidthPixels < 480) {
                    this.textSize = 12;
                }
            }

            this.dateMode = dateMode;
            if (timeMode == 4) {
                this.startHour = 1;
                this.endHour = 12;
            } else {
                this.startHour = 0;
                this.endHour = 23;
            }

            this.timeMode = timeMode;
        }
    }

    public void setResetWhileWheel(boolean resetWhileWheel) {
        this.resetWhileWheel = resetWhileWheel;
    }

    /** @deprecated */
    @Deprecated
    public void setRange(int startYear, int endYear) {
        if (this.dateMode == -1) {
            throw new IllegalArgumentException("Date mode invalid");
        } else {
            this.startYear = startYear;
            this.endYear = endYear;
            this.initYearData();
        }
    }

    public void setDateRangeStart(int startYear, int startMonth, int startDay) {
        if (this.dateMode == -1) {
            throw new IllegalArgumentException("Date mode invalid");
        } else {
            this.startYear = startYear;
            this.startMonth = startMonth;
            this.startDay = startDay;
            this.initYearData();
        }
    }

    public void setDateRangeStartTime(int startMonth, int startDay,int startHour) {
        if (this.dateMode == -1) {
            throw new IllegalArgumentException("Date mode invalid");
        } else {
//            this.startYear = startYear;
            this.startMonth = startMonth;
            this.startDay = startDay;
            this.startHour=startHour;
            this.initYearData();
        }
    }

    public void setDateRangeEnd(int endYear, int endMonth, int endDay) {
        if (this.dateMode == -1) {
            throw new IllegalArgumentException("Date mode invalid");
        } else {
            this.endYear = endYear;
            this.endMonth = endMonth;
            this.endDay = endDay;
            this.initYearData();
        }
    }



    public void setDateRangeStart(int startYearOrMonth, int startMonthOrDay) {
        if (this.dateMode == -1) {
            throw new IllegalArgumentException("Date mode invalid");
        } else if (this.dateMode == 0) {
            throw new IllegalArgumentException("Not support year/month/day mode");
        } else {
            if (this.dateMode == 1) {
                this.startYear = startYearOrMonth;
                this.startMonth = startMonthOrDay;
            } else if (this.dateMode == 2) {
                int year = Calendar.getInstance(Locale.CHINA).get(1);
                this.startYear = this.endYear = year;
                this.startMonth = startYearOrMonth;
                this.startDay = startMonthOrDay;
            }

            this.initYearData();
        }
    }

    public void setDateRangeEnd(int endYearOrMonth, int endMonthOrDay) {
        if (this.dateMode == -1) {
            throw new IllegalArgumentException("Date mode invalid");
        } else if (this.dateMode == 0) {
            throw new IllegalArgumentException("Not support year/month/day mode");
        } else {
            if (this.dateMode == 1) {
                this.endYear = endYearOrMonth;
                this.endMonth = endMonthOrDay;
            } else if (this.dateMode == 2) {
                this.endMonth = endYearOrMonth;
                this.endDay = endMonthOrDay;
            }

            this.initYearData();
        }
    }

    public void setTimeRangeStart(int startHour, int startMinute) {
        if (this.timeMode == -1) {
            throw new IllegalArgumentException("Time mode invalid");
        } else {
            boolean illegal = false;
            if (startHour < 0 || startMinute < 0 || startMinute > 59) {
                illegal = true;
            }

            if (this.timeMode == 4 && (startHour == 0 || startHour > 12)) {
                illegal = true;
            }

            if (this.timeMode == 3 && startHour >= 24) {
                illegal = true;
            }

            if (illegal) {
                throw new IllegalArgumentException("Time out of range");
            } else {
                this.startHour = startHour;
                this.startMinute = startMinute;
                this.initHourData();
            }
        }
    }

    public void setTimeRangeEnd(int endHour, int endMinute) {
        if (this.timeMode == -1) {
            throw new IllegalArgumentException("Time mode invalid");
        } else {
            boolean illegal = false;
            if (endHour < 0 || endMinute < 0 || endMinute > 59) {
                illegal = true;
            }

            if (this.timeMode == 4 && (endHour == 0 || endHour > 12)) {
                illegal = true;
            }

            if (this.timeMode == 3 && endHour >= 24) {
                illegal = true;
            }

            if (illegal) {
                throw new IllegalArgumentException("Time out of range");
            } else {
                this.endHour = endHour;
                this.endMinute = endMinute;
                this.initHourData();
            }
        }
    }

    public void setLabel(String yearLabel, String monthLabel, String dayLabel, String hourLabel, String minuteLabel) {
        this.yearLabel = yearLabel;
        this.monthLabel = monthLabel;
        this.dayLabel = dayLabel;
        this.hourLabel = hourLabel;
        this.minuteLabel = minuteLabel;
    }

    public void setSelectedItem(int year, int month, int day, int hour, int minute) {
        if (this.dateMode != 0) {
            throw new IllegalArgumentException("Date mode invalid");
        } else {
            LogUtils.verbose(this, "change months and days while set selected");
            this.changeMonthData(year);
            this.changeDayData(year, month);
            this.selectedYearIndex = this.findItemIndex(this.years, year);
            this.selectedMonthIndex = this.findItemIndex(this.months, month);
            this.selectedDayIndex = this.findItemIndex(this.days, day);
            if (this.timeMode != -1) {
                this.selectedHour = DateUtils.fillZero(hour);
                this.selectedMinute = DateUtils.fillZero(minute);
            }

        }
    }

    public void setSelectedItem(int yearOrMonth, int monthOrDay, int hour, int minute) {
        if (this.dateMode == 0) {
            throw new IllegalArgumentException("Date mode invalid");
        } else {
            if (this.dateMode == 2) {
                LogUtils.verbose(this, "change months and days while set selected");
                int year = Calendar.getInstance(Locale.CHINA).get(1);
                this.startYear = this.endYear = year;
                this.changeMonthData(year);
                this.changeDayData(year, yearOrMonth);
                this.selectedMonthIndex = this.findItemIndex(this.months, yearOrMonth);
                this.selectedDayIndex = this.findItemIndex(this.days, monthOrDay);
            } else if (this.dateMode == 1) {
                LogUtils.verbose(this, "change months while set selected");
                this.changeMonthData(yearOrMonth);
                this.selectedYearIndex = this.findItemIndex(this.years, yearOrMonth);
                this.selectedMonthIndex = this.findItemIndex(this.months, monthOrDay);
            }

            if (this.timeMode != -1) {
                this.selectedHour = DateUtils.fillZero(hour);
                this.selectedMinute = DateUtils.fillZero(minute);
            }

        }
    }

    public void setOnWheelListener(DateTimePicker.OnWheelListener onWheelListener) {
        this.onWheelListener = onWheelListener;
    }

    public void setOnDateTimePickListener(DateTimePicker.OnDateTimePickListener listener) {
        this.onDateTimePickListener = listener;
    }

    public String getSelectedYear() {
        if (this.dateMode != 0 && this.dateMode != 1) {
            return "";
        } else {
            if (this.years.size() <= this.selectedYearIndex) {
                this.selectedYearIndex = this.years.size() - 1;
            }

            return (String)this.years.get(this.selectedYearIndex);
        }
    }

    public String getSelectedMonth() {
        if (this.dateMode != -1) {
            if (this.months.size() <= this.selectedMonthIndex) {
                this.selectedMonthIndex = this.months.size() - 1;
            }

            return (String)this.months.get(this.selectedMonthIndex);
        } else {
            return "";
        }
    }

    public String getSelectedDay() {
        if (this.dateMode != 0 && this.dateMode != 2) {
            return "";
        } else {
            if (this.days.size() <= this.selectedDayIndex) {
                this.selectedDayIndex = this.days.size() - 1;
            }

            return (String)this.days.get(this.selectedDayIndex);
        }
    }

    public String getSelectedHour() {
        return this.timeMode != -1 ? this.selectedHour : "";
    }

    public String getSelectedMinute() {
        return this.timeMode != -1 ? this.selectedMinute : "";
    }

    @NonNull
    protected View makeCenterView() {
        if ((this.dateMode == 0 || this.dateMode == 1) && this.years.size() == 0) {
            LogUtils.verbose(this, "init years before make view");
            this.initYearData();
        }

        int selectedYear;
        if (this.dateMode != -1 && this.months.size() == 0) {
            LogUtils.verbose(this, "init months before make view");
            selectedYear = DateUtils.trimZero(this.getSelectedYear());
            this.changeMonthData(selectedYear);
        }

        if ((this.dateMode == 0 || this.dateMode == 2) && this.days.size() == 0) {
            LogUtils.verbose(this, "init days before make view");
            if (this.dateMode == 0) {
                selectedYear = DateUtils.trimZero(this.getSelectedYear());
            } else {
                selectedYear = Calendar.getInstance(Locale.CHINA).get(1);
            }

            int selectedMonth = DateUtils.trimZero(this.getSelectedMonth());
            this.changeDayData(selectedYear, selectedMonth);
        }

        if (this.timeMode != -1 && this.hours.size() == 0) {
            LogUtils.verbose(this, "init hours before make view");
            this.initHourData();
        }

        if (this.timeMode != -1 && this.minutes.size() == 0) {
            LogUtils.verbose(this, "init minutes before make view");
            this.changeMinuteData(DateUtils.trimZero(this.selectedHour));
        }

        LinearLayout layout = new LinearLayout(this.activity);
        layout.setOrientation(0);
        layout.setGravity(17);
//        WheelView yearView = this.createWheelView();
        final WheelView monthView = this.createWheelView();
        final WheelView dayView = this.createWheelView();
        WheelView hourView = this.createWheelView();
        final WheelView minuteView = this.createWheelView();
        TextView labelView;
//        if (this.dateMode == 0 || this.dateMode == 1) {
//            yearView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0F));
//            yearView.setItems(this.years, this.selectedYearIndex);
//            yearView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
//                public void onSelected(int index) {
////                    DateTimePicker.this.selectedYearIndex = index;
////                    String selectedYearStr = (String)DateTimePicker.this.years.get(DateTimePicker.this.selectedYearIndex);
////                    if (DateTimePicker.this.onWheelListener != null) {
////                        DateTimePicker.this.onWheelListener.onYearWheeled(DateTimePicker.this.selectedYearIndex, selectedYearStr);
////                    }
//
//                    LogUtils.verbose(this, "change months after year wheeled");
//                    if (DateTimePicker.this.resetWhileWheel) {
//                        DateTimePicker.this.selectedMonthIndex = 0;
//                        DateTimePicker.this.selectedDayIndex = 0;
//                    }
//
////                    int selectedYear = DateUtils.trimZero(selectedYearStr);
////                    DateTimePicker.this.changeMonthData(selectedYear);
//                    monthView.setItems(DateTimePicker.this.months, DateTimePicker.this.selectedMonthIndex);
//                    if (DateTimePicker.this.onWheelListener != null) {
//                        DateTimePicker.this.onWheelListener.onMonthWheeled(DateTimePicker.this.selectedMonthIndex, (String)DateTimePicker.this.months.get(DateTimePicker.this.selectedMonthIndex));
//                    }
//
//                    DateTimePicker.this.changeDayData(2020,DateUtils.trimZero((String)DateTimePicker.this.months.get(DateTimePicker.this.selectedMonthIndex)));
//                    dayView.setItems(DateTimePicker.this.days, DateTimePicker.this.selectedDayIndex);
//                    if (DateTimePicker.this.onWheelListener != null) {
//                        DateTimePicker.this.onWheelListener.onDayWheeled(DateTimePicker.this.selectedDayIndex, (String)DateTimePicker.this.days.get(DateTimePicker.this.selectedDayIndex));
//                    }
//
//                }
//            });
//            layout.addView(yearView);
//            if (!TextUtils.isEmpty(this.yearLabel)) {
//                labelView = this.createLabelView();
//                labelView.setTextSize((float)this.textSize);
//                labelView.setText(this.yearLabel);
//                layout.addView(labelView);
//            }
//        }

        if (this.dateMode != -1) {
            monthView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0F));
            monthView.setItems(this.months, this.selectedMonthIndex);
            monthView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                public void onSelected(int index) {
                    DateTimePicker.this.selectedMonthIndex = index;
                    String selectedMonthStr = (String)DateTimePicker.this.months.get(DateTimePicker.this.selectedMonthIndex);
                    if (DateTimePicker.this.onWheelListener != null) {
                        DateTimePicker.this.onWheelListener.onMonthWheeled(DateTimePicker.this.selectedMonthIndex, selectedMonthStr);
                    }

                    if (DateTimePicker.this.dateMode == 0 || DateTimePicker.this.dateMode == 2) {
                        LogUtils.verbose(this, "change days after month wheeled");
                        if (DateTimePicker.this.resetWhileWheel) {
                            DateTimePicker.this.selectedDayIndex = 0;
                        }

                        int selectedYear;
                        if (DateTimePicker.this.dateMode == 0) {
                            selectedYear = DateUtils.trimZero(DateTimePicker.this.getSelectedYear());
                        } else {
                            selectedYear = Calendar.getInstance(Locale.CHINA).get(1);
                        }

                        DateTimePicker.this.changeDayData(selectedYear, DateUtils.trimZero(selectedMonthStr));
                        dayView.setItems(DateTimePicker.this.days, DateTimePicker.this.selectedDayIndex);
                        if (DateTimePicker.this.onWheelListener != null) {
                            DateTimePicker.this.onWheelListener.onDayWheeled(DateTimePicker.this.selectedDayIndex, (String)DateTimePicker.this.days.get(DateTimePicker.this.selectedDayIndex));
                        }
                    }

                }
            });
            layout.addView(monthView);
            if (!TextUtils.isEmpty(this.monthLabel)) {
                labelView = this.createLabelView();
                labelView.setTextSize((float)this.textSize);
                labelView.setText(this.monthLabel);
                layout.addView(labelView);
            }
        }

        if (this.dateMode == 0 || this.dateMode == 2) {
            dayView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0F));
            dayView.setItems(this.days, this.selectedDayIndex);
            dayView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                public void onSelected(int index) {
                    DateTimePicker.this.selectedDayIndex = index;
                    if (DateTimePicker.this.onWheelListener != null) {
                        DateTimePicker.this.onWheelListener.onDayWheeled(DateTimePicker.this.selectedDayIndex, (String)DateTimePicker.this.days.get(DateTimePicker.this.selectedDayIndex));
                    }

                }
            });
            layout.addView(dayView);
            if (!TextUtils.isEmpty(this.dayLabel)) {
                labelView = this.createLabelView();
                labelView.setTextSize((float)this.textSize);
                labelView.setText(this.dayLabel);
                layout.addView(labelView);
            }
        }

        if (this.timeMode != -1) {
            hourView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0F));
            hourView.setItems(this.hours, this.selectedHour);
            hourView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                public void onSelected(int index) {
                    DateTimePicker.this.selectedHour = (String)DateTimePicker.this.hours.get(index);
                    if (DateTimePicker.this.onWheelListener != null) {
                        DateTimePicker.this.onWheelListener.onHourWheeled(index, DateTimePicker.this.selectedHour);
                    }

                    LogUtils.verbose(this, "change minutes after hour wheeled");
                    DateTimePicker.this.changeMinuteData(DateUtils.trimZero(DateTimePicker.this.selectedHour));
                    minuteView.setItems(DateTimePicker.this.minutes, DateTimePicker.this.selectedMinute);
                }
            });
            layout.addView(hourView);
            if (!TextUtils.isEmpty(this.hourLabel)) {
                labelView = this.createLabelView();
                labelView.setTextSize((float)this.textSize);
                labelView.setText(this.hourLabel);
                layout.addView(labelView);
            }

//            minuteView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0F));
//            minuteView.setItems(this.minutes, this.selectedMinute);
//            minuteView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
//                public void onSelected(int index) {
//                    DateTimePicker.this.selectedMinute = (String)DateTimePicker.this.minutes.get(index);
//                    if (DateTimePicker.this.onWheelListener != null) {
//                        DateTimePicker.this.onWheelListener.onMinuteWheeled(index, DateTimePicker.this.selectedMinute);
//                    }
//
//                }
//            });
//            layout.addView(minuteView);
//            if (!TextUtils.isEmpty(this.minuteLabel)) {
//                labelView = this.createLabelView();
//                labelView.setTextSize((float)this.textSize);
//                labelView.setText(this.minuteLabel);
//                layout.addView(labelView);
//            }
        }

        return layout;
    }

    protected void onSubmit() {
        if (this.onDateTimePickListener != null) {
            String year = this.getSelectedYear();
            String month = this.getSelectedMonth();
            String day = this.getSelectedDay();
            String hour = this.getSelectedHour();
            String minute = this.getSelectedMinute();
            switch(this.dateMode) {
                case -1:
                    ((DateTimePicker.OnTimePickListener)this.onDateTimePickListener).onDateTimePicked(hour, minute);
                    break;
                case 0:
                    ((DateTimePicker.OnYearMonthDayTimePickListener)this.onDateTimePickListener).onDateTimePicked(year, month, day, hour, minute);
                    break;
                case 1:
                    ((DateTimePicker.OnYearMonthTimePickListener)this.onDateTimePickListener).onDateTimePicked(year, month, hour, minute);
                    break;
                case 2:
                    ((DateTimePicker.OnMonthDayTimePickListener)this.onDateTimePickListener).onDateTimePicked(month, day, hour, minute);
            }

        }
    }

    private int findItemIndex(ArrayList<String> items, int item) {
        int index = Collections.binarySearch(items, item, new Comparator<Object>() {
            public int compare(Object lhs, Object rhs) {
                String lhsStr = lhs.toString();
                String rhsStr = rhs.toString();
                lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1) : lhsStr;
                rhsStr = rhsStr.startsWith("0") ? rhsStr.substring(1) : rhsStr;

                try {
                    return Integer.parseInt(lhsStr) - Integer.parseInt(rhsStr);
                } catch (NumberFormatException var6) {
                    var6.printStackTrace();
                    return 0;
                }
            }
        });
        if (index < 0) {
            throw new IllegalArgumentException("Item[" + item + "] out of range");
        } else {
            return index;
        }
    }

    private void initYearData() {
        this.years.clear();
        int index;
        if (this.startYear == this.endYear) {
            this.years.add(String.valueOf(this.startYear));
        } else if (this.startYear < this.endYear) {
            for(index = this.startYear; index <= this.endYear; ++index) {
                this.years.add(String.valueOf(index));
            }
        } else {
            for(index = this.startYear; index >= this.endYear; --index) {
                this.years.add(String.valueOf(index));
            }
        }

        if (!this.resetWhileWheel && (this.dateMode == 0 || this.dateMode == 1)) {
            index = this.years.indexOf(DateUtils.fillZero(Calendar.getInstance().get(1)));
            if (index == -1) {
                this.selectedYearIndex = 0;
            } else {
                this.selectedYearIndex = index;
            }
        }

    }

    private void changeMonthData(int selectedYear) {
        String preSelectMonth = "";
        if (!this.resetWhileWheel) {
            if (this.months.size() > this.selectedMonthIndex) {
                preSelectMonth = (String)this.months.get(this.selectedMonthIndex);
            } else {
                preSelectMonth = DateUtils.fillZero(Calendar.getInstance().get(2) + 1);
            }

            LogUtils.verbose(this, "preSelectMonth=" + preSelectMonth);
        }

        this.months.clear();
        if (this.startMonth >= 1 && this.endMonth >= 1 && this.startMonth <= 12 && this.endMonth <= 12) {
            int preSelectMonthIndex;
            if (this.startYear == this.endYear) {
                if (this.startMonth > this.endMonth) {
                    for(preSelectMonthIndex = this.endMonth; preSelectMonthIndex >= this.startMonth; --preSelectMonthIndex) {
                        this.months.add(DateUtils.fillZero(preSelectMonthIndex));
                    }
                } else {
                    for(preSelectMonthIndex = this.startMonth; preSelectMonthIndex <= this.endMonth; ++preSelectMonthIndex) {
                        this.months.add(DateUtils.fillZero(preSelectMonthIndex));
                    }
                }
            } else if (selectedYear == this.startYear) {
                for(preSelectMonthIndex = this.startMonth; preSelectMonthIndex <= 12; ++preSelectMonthIndex) {
                    this.months.add(DateUtils.fillZero(preSelectMonthIndex));
                }
            } else if (selectedYear == this.endYear) {
                for(preSelectMonthIndex = 1; preSelectMonthIndex <= this.endMonth; ++preSelectMonthIndex) {
                    this.months.add(DateUtils.fillZero(preSelectMonthIndex));
                }
            } else {
                for(preSelectMonthIndex = 1; preSelectMonthIndex <= 12; ++preSelectMonthIndex) {
                    this.months.add(DateUtils.fillZero(preSelectMonthIndex));
                }
            }

            if (!this.resetWhileWheel) {
                preSelectMonthIndex = this.months.indexOf(preSelectMonth);
                this.selectedMonthIndex = preSelectMonthIndex == -1 ? 0 : preSelectMonthIndex;
            }

        } else {
            throw new IllegalArgumentException("Month out of range [1-12]");
        }
    }

    private void changeDayData(int selectedYear, int selectedMonth) {
        int maxDays = DateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
        String preSelectDay = "";
        if (!this.resetWhileWheel) {
            if (this.selectedDayIndex >= maxDays) {
                this.selectedDayIndex = maxDays - 1;
            }

            if (this.days.size() > this.selectedDayIndex) {
                preSelectDay = (String)this.days.get(this.selectedDayIndex);
            } else {
                preSelectDay = DateUtils.fillZero(Calendar.getInstance().get(5));
            }

            LogUtils.verbose(this, "maxDays=" + maxDays + ", preSelectDay=" + preSelectDay);
        }

        this.days.clear();
        int i;
        if (selectedYear == this.startYear && selectedMonth == this.startMonth && selectedYear == this.endYear && selectedMonth == this.endMonth) {
            for(i = this.startDay; i <= this.endDay; ++i) {
                this.days.add(DateUtils.fillZero(i));
            }
        } else if (selectedYear == this.startYear && selectedMonth == this.startMonth) {
            for(i = this.startDay; i <= maxDays; ++i) {
                this.days.add(DateUtils.fillZero(i));
            }
        } else if (selectedYear == this.endYear && selectedMonth == this.endMonth) {
            for(i = 1; i <= this.endDay; ++i) {
                this.days.add(DateUtils.fillZero(i));
            }
        } else {
            for(i = 1; i <= maxDays; ++i) {
                this.days.add(DateUtils.fillZero(i));
            }
        }

        if (!this.resetWhileWheel) {
            i = this.days.indexOf(preSelectDay);
            this.selectedDayIndex = i == -1 ? 0 : i;
        }

    }

    private void initHourData() {
        this.hours.clear();
        int currentHour = 0;
        if (!this.resetWhileWheel) {
            if (this.timeMode == 3) {
                currentHour = Calendar.getInstance().get(11);
            } else {
                currentHour = Calendar.getInstance().get(10);
            }
        }

        for(int i = this.startHour; i <= this.endHour; ++i) {
            String hour = DateUtils.fillZero(i);
            if (!this.resetWhileWheel && i == currentHour) {
                this.selectedHour = hour;
            }

            this.hours.add(hour);
        }

        if (this.hours.indexOf(this.selectedHour) == -1) {
            this.selectedHour = (String)this.hours.get(0);
        }

        if (!this.resetWhileWheel) {
            this.selectedMinute = DateUtils.fillZero(Calendar.getInstance().get(12));
        }

    }

    private void changeMinuteData(int selectedHour) {
        this.minutes.clear();
        int i;
        if (this.startHour == this.endHour) {
            if (this.startMinute > this.endMinute) {
                i = this.startMinute;
                this.startMinute = this.endMinute;
                this.endMinute = i;
            }

            for(i = this.startMinute; i <= this.endMinute; ++i) {
                this.minutes.add(DateUtils.fillZero(i));
            }
        } else if (selectedHour == this.startHour) {
            for(i = this.startMinute; i <= 59; ++i) {
                this.minutes.add(DateUtils.fillZero(i));
            }
        } else if (selectedHour == this.endHour) {
            for(i = 0; i <= this.endMinute; ++i) {
                this.minutes.add(DateUtils.fillZero(i));
            }
        } else {
            for(i = 0; i <= 59; ++i) {
                this.minutes.add(DateUtils.fillZero(i));
            }
        }

        if (this.minutes.indexOf(this.selectedMinute) == -1) {
            this.selectedMinute = (String)this.minutes.get(0);
        }

    }

    public interface OnTimePickListener extends DateTimePicker.OnDateTimePickListener {
        void onDateTimePicked(String var1, String var2);
    }

    /** @deprecated */
    @Deprecated
    public interface OnMonthDayPickListener extends DateTimePicker.OnMonthDayTimePickListener {
    }

    public interface OnMonthDayTimePickListener extends DateTimePicker.OnDateTimePickListener {
        void onDateTimePicked(String var1, String var2, String var3, String var4);
    }

    /** @deprecated */
    @Deprecated
    public interface OnYearMonthPickListener extends DateTimePicker.OnYearMonthTimePickListener {
    }

    public interface OnYearMonthTimePickListener extends DateTimePicker.OnDateTimePickListener {
        void onDateTimePicked(String var1, String var2, String var3, String var4);
    }

    public interface OnYearMonthDayTimePickListener extends DateTimePicker.OnDateTimePickListener {
        void onDateTimePicked(String var1, String var2, String var3, String var4, String var5);
    }

    protected interface OnDateTimePickListener {
    }

    public interface OnWheelListener {
        void onYearWheeled(int var1, String var2);

        void onMonthWheeled(int var1, String var2);

        void onDayWheeled(int var1, String var2);

        void onHourWheeled(int var1, String var2);

        void onMinuteWheeled(int var1, String var2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DateMode {
    }
}
