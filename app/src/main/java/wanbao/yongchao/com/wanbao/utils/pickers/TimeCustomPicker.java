package wanbao.yongchao.com.wanbao.utils.pickers;

import android.app.Activity;


public class TimeCustomPicker extends DateTimePicker {
    public TimeCustomPicker(Activity activity) {
        this(activity, 0);
    }

    public TimeCustomPicker(Activity activity, int mode) {
        super(activity, mode, 3);
    }

    /** @deprecated */
    @Deprecated
    public final void setLabel(String yearLabel, String monthLabel, String dayLabel, String hourLabel, String minuteLabel) {
        super.setLabel(yearLabel, monthLabel, dayLabel, hourLabel, minuteLabel);
    }

    public void setLabel( String monthLabel, String dayLabel,String hourLabel) {
        super.setLabel("", monthLabel, dayLabel, hourLabel, "");
    }

    /** @deprecated */
    @Deprecated
    public final void setDateRangeStart(int startYear, int startMonth, int startDay) {
        super.setDateRangeStart(startYear, startMonth, startDay);
    }

    /** @deprecated */
    @Deprecated
    public final void setDateRangeEnd(int endYear, int endMonth, int endDay) {
        super.setDateRangeEnd(endYear, endMonth, endDay);
    }

    /** @deprecated */
    @Deprecated
    public final void setDateRangeStart(int startYearOrMonth, int startMonthOrDay) {
        super.setDateRangeStart(startYearOrMonth, startMonthOrDay);
    }

    /** @deprecated */
    @Deprecated
    public final void setDateRangeEnd(int endYearOrMonth, int endMonthOrDay) {
        super.setDateRangeEnd(endYearOrMonth, endMonthOrDay);
    }

    /** @deprecated */
    @Deprecated
    public void setTimeRangeStart(int startHour, int startMinute) {
        throw new UnsupportedOperationException("Time range nonsupport");
    }

    /** @deprecated */
    @Deprecated
    public void setTimeRangeEnd(int endHour, int endMinute) {
        throw new UnsupportedOperationException("Time range nonsupport");
    }

    /** @deprecated */
    @Deprecated
    public void setRange(int startYear, int endYear) {
        super.setRange(startYear, endYear);
    }

    public void setRangeStart(int startYear, int startMonth, int startDay) {
        super.setDateRangeStart(startYear, startMonth, startDay);
    }

    public void setRangeStartTime( int startMonth, int startDay,int hour) {
        super.setDateRangeStartTime( startMonth, startDay,hour);
    }

    public void setRangeEnd(int endYear, int endMonth, int endDay) {
        super.setDateRangeEnd(endYear, endMonth, endDay);
    }

    public void setRangeStart(int startYearOrMonth, int startMonthOrDay) {
        super.setDateRangeStart(startYearOrMonth, startMonthOrDay);
    }

    public void setRangeEnd(int endYearOrMonth, int endMonthOrDay) {
        super.setDateRangeEnd(endYearOrMonth, endMonthOrDay);
    }

    /** @deprecated */
    @Deprecated
    public final void setSelectedItem(int year, int month, int day, int hour, int minute) {
        super.setSelectedItem(year, month, day, hour, minute);
    }

    /** @deprecated */
    @Deprecated
    public final void setSelectedItem(int yearOrMonth, int monthOrDay, int hour, int minute) {
        super.setSelectedItem(yearOrMonth, monthOrDay, hour, minute);
    }

    public void setSelectedItem(int year, int month, int day) {
        super.setSelectedItem(year, month, day, 0, 0);
    }
    public void setSelectedHourItem(int month, int day, int hour) {
        super.setSelectedItem(0, month, day, hour, 0);
    }

    public void setSelectedItem(int yearOrMonth, int monthOrDay) {
        super.setSelectedItem(yearOrMonth, monthOrDay, 0, 0);
    }

    /** @deprecated */
    @Deprecated
    public final void setOnWheelListener(DateTimePicker.OnWheelListener onWheelListener) {
        super.setOnWheelListener(onWheelListener);
    }

    public void setOnWheelListener(final TimeCustomPicker.OnWheelListener listener) {
        if (null != listener) {
            super.setOnWheelListener(new DateTimePicker.OnWheelListener() {
                public void onYearWheeled(int index, String year) {

                }

                public void onMonthWheeled(int index, String month) {
                    listener.onMonthWheeled(index, month);
                }

                public void onDayWheeled(int index, String day) {
                    listener.onDayWheeled(index, day);
                }

                public void onHourWheeled(int index, String hour) {
                    listener.onHourWheeled(index, hour);
                }

                public void onMinuteWheeled(int index, String minute) {
                }
            });
        }
    }

    /** @deprecated */
    @Deprecated
    public final void setOnDateTimePickListener(OnDateTimePickListener listener) {
        super.setOnDateTimePickListener(listener);
    }

    public void setOnDatePickListener(final TimeCustomPicker.OnDatePickListener listener) {
        if (null != listener) {
            if (listener instanceof TimeCustomPicker.OnMonthDayHourPickListener) {
                super.setOnDateTimePickListener(new OnYearMonthDayTimePickListener() {
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        ((TimeCustomPicker.OnMonthDayHourPickListener)listener).onDatePicked(month, day ,hour);
                    }
                });
            } else if (listener instanceof TimeCustomPicker.OnYearMonthPickListener) {
                super.setOnDateTimePickListener(new OnYearMonthTimePickListener() {
                    public void onDateTimePicked(String year, String month, String hour, String minute) {
                        ((TimeCustomPicker.OnYearMonthPickListener)listener).onDatePicked(year, month);
                    }
                });
            } else if (listener instanceof TimeCustomPicker.OnMonthDayPickListener) {
                super.setOnDateTimePickListener(new OnMonthDayTimePickListener() {
                    public void onDateTimePicked(String month, String day, String hour, String minute) {
                        ((TimeCustomPicker.OnMonthDayPickListener)listener).onDatePicked(month, day);
                    }
                });
            }

        }
    }

    public interface OnWheelListener {
        void onMonthWheeled(int var1, String var2);

        void onDayWheeled(int var1, String var2);

        void onHourWheeled(int var1, String var2);
    }

    public interface OnMonthDayPickListener extends TimeCustomPicker.OnDatePickListener {
        void onDatePicked(String var1, String var2);
    }

    public interface OnYearMonthPickListener extends TimeCustomPicker.OnDatePickListener {
        void onDatePicked(String var1, String var2);
    }

    public interface OnMonthDayHourPickListener extends TimeCustomPicker.OnDatePickListener {
        void onDatePicked(String var1, String var2, String var3);
    }

    protected interface OnDatePickListener {
    }
}