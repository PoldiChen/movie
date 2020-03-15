package com.poldichen.movie.util;

/**
 * @author poldi.chen
 * @className EncodeUtil
 * @description TODO
 * @date 2020/3/14 9:36
 **/
public class EncodeUtil {

    public static void main(String[] args) {
        System.out.println(unicodeToCn("\\u5267\\u60c5"));
    }

    public static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }
}
