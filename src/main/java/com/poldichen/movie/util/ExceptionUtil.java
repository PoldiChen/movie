package com.poldichen.movie.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author poldi.chen
 * @className ExceptionUtil
 * @description TODO
 * @date 2020/4/18 11:25
 **/
public class ExceptionUtil {

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
