package com.poldichen.movie.util;

import com.poldichen.movie.common.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author poldi.chen
 * @className TimeUtil
 * @description TODO
 * @date 2020/4/18 11:33
 **/
public class TimeUtil {

    public static String getTimeStr() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIME_FORMAT_STR);
        return simpleDateFormat.format(now);
    }

    public static void main(String[] args) {
        System.out.println(getTimeStr());
    }
}
