package com.poldichen.movie.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author poldi.chen
 * @className BaiduApiUtil
 * @description TODO
 * @date 2020/3/15 9:40
 **/
public class BaiduApiUtil {

    public static void main(String[] args) {
        System.out.println(getAccessToken());
    }

    public static String getAccessToken() {
        String url = "https://aip.baidubce.com/oauth/2.0/token";
        String grantType = "client_credentials";
        String clientId = "FHGTLCiWFCrua6g3ylQL6n71";
        String clientSecret = "3EanIKBWTr1E6OafLXqYVhZzLqbGhkTO";

        url += "?grant_type=" + grantType + "&client_id=" + clientId + "&client_secret=" + clientSecret;

        String response = HttpClientUtil.doGet(url, new HashMap<>());

        return response;
    }
}
