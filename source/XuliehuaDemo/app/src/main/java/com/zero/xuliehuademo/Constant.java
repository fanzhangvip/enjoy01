package com.zero.xuliehuademo;

public class Constant {
    public static long start;
    public static long end;
    public static long tempResult;
    public static long result;
    public static int MODE = 0;//mode = 0 empty 1 perParcelable 2 Serializable
    public static final long TEST_TIME = 10;
    public static final long TEST_CYCLE = 50;
    public static final long TEST_MOUNT = 100;

    public static final int REQ_CDOE = 0x01;

    public static long result (){
        tempResult = tempResult + end-start;
        return tempResult;
    }

    public static long perTime(){
        result = tempResult/TEST_CYCLE;
        tempResult = 0;
        return result;
    }
}
