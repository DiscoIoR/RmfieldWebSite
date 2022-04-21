package cn.rmfield.website.utils;

import java.util.Calendar;

public class TSdemo {

    public static void main(String[] args){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(1647331247L *1000);
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH)+1);
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
    }

}
