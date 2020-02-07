package com.poldichen.movie.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author poldi.chen
 * @className TokenUtil
 * @description TODO
 * @date 2019/8/24 17:07
 **/
public class TokenUtil {

    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.equals("")) {
            token = request.getHeader("authorization");
        }
        return token;
    }
}
