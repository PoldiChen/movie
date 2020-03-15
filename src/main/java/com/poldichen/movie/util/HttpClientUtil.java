package com.poldichen.movie.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author poldi.chen
 * @className HttpClientUtil
 * @description TODO
 * @date 2020/2/23 14:42
 **/
public class HttpClientUtil {

    public static void main(String[] args) {
        String auth = "marker-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWNrIiwiZXhwIjoxNTgzMDczMjM1fQ.AVGNXMIyw0zl3IbT9Nsj7vKMINSr6uzOaK-TTa90jeDHhepwyFusvKXyx4hg8vzhtAFonhLJfKu5_AfSoBa0YA";
//        doGet("http://114.67.87.197:8080/movie", auth);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "ttt");
        params.put("publish_date", "2020-02-23");
        doPost("http://114.67.87.197:8080/movie", auth, params);
    }

    public static String doGet(String url, Map<String, String> headerParams) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Set<String> keySet = headerParams.keySet();
        for (String key : keySet) {
            headers.add(key, headerParams.get(key));
        }

        headers.add("Cookie", "douban-fav-remind=1; bid=Wl_fK9wCXuo; ll=\"118282\"; _vwo_uuid_v2=D77F3D0D111DEED08EEDBD3EB46B0DB7B|05d12f5c3309186995b611c476f4566c; __yadk_uid=Dg4ep2cNWG0hrgZw24cwgZcQ1TvDAecw; trc_cookie_storage=taboola%2520global%253Auser-id%3D82a14e1d-d7d3-4112-b618-dac3bca1ab77-tuct1afd52c; __utmv=30149280.6253; __gads=ID=b43ec049b9384d4b:T=1566317147:S=ALNI_MZGFSV3f3_LPI0lyudZx4yQy2sCyw; douban-profile-remind=1; push_noty_num=0; push_doumail_num=0; viewed=\"5453891\"; gr_user_id=c728252b-5062-4b91-bac1-d9745ca3e996; dbcl2=\"62533792:7RiaOxk+CPI\"; __utmz=30149280.1584156452.54.9.utmcsr=movie.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/celebrity/1003494/; __utmz=223695111.1584156470.49.8.utmcsr=search.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/movie/subject_search; ck=fnep; __utmc=30149280; __utmc=223695111; ct=y; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1584201921%2C%22https%3A%2F%2Fsearch.douban.com%2Fmovie%2Fsubject_search%3Fsearch_text%3D%25E4%25B8%2580%26cat%3D1002%22%5D; _pk_ses.100001.4cf6=*; __utma=30149280.1396546215.1519473633.1584192003.1584201921.57; __utmb=30149280.0.10.1584201921; __utma=223695111.834493428.1565358274.1584192003.1584201921.52; __utmb=223695111.0.10.1584201921; _pk_id.100001.4cf6=97291528141a9588.1565358273.52.1584202015.1584193704.");

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String responseStr = response.getBody();
//        System.out.println(responseStr);
        return responseStr;
    }

    public static String doPost(String url, String auth, Map<String, Object> requestBody) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);
        headers.add("content-type", "text/html;charset=utf-8");
        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(requestBody), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String responseStr = response.getBody();
        return responseStr;
    }

    public static String doPut(String url, String auth, Map<String, Object> requestBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);
        headers.add("content-type", "text/html;charset=utf-8");
        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(requestBody), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        String responseStr = response.getBody();
        return responseStr;
    }

}
