package com.enjoy.zero.libzhujie01.enumdemo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class EnumDemo {

    //先定义 常量
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    //注解枚举
    @MyIntDef({SUNDAY, MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeekDays {}


    @WeekDays
    private static int mCurrentDay = SUNDAY;

    public static void setCurrentDay(@WeekDays int currentDay) {
        mCurrentDay = currentDay;
    }

    public static void main(String ... args){
            //TODO:

        setCurrentDay(10);
    }
}
