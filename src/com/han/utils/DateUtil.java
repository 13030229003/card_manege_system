package com.han.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);
        return time;
    }

    public static String getNowTime1(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String time = sdf.format(date);
        return time;
    }

    public static String getNowTime2(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = sdf.format(date);
        return time;
    }
//    public static void getTimeInterval()
public static String getTimeInterval(String starTime,String endTime) {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
       int i=0;
        try {
            Date star = dft.parse(starTime);//开始时间
            Date endDay=dft.parse(endTime);//结束时间
            Date nextDay=star;
            while(nextDay.before(endDay)){//当明天不在结束时间之前是终止循环
                Calendar cld = Calendar.getInstance();
                cld.setTime(star);
                cld.add(Calendar.DATE, 1);
                star = cld.getTime();
                //获得下一天日期字符串
                nextDay = star;
                i++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return String.valueOf(i);
    }
}
