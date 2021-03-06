package com.cgtrc.bym.a10001store.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BYM on 2016/1/31.
 */
public class DataUtil {
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

    public static SimpleDateFormat getSimpleDateFormat() {
        return sdf;
    }

    public static String getCurrentTime(){
        return sdf.format(new Date());
    }

}
