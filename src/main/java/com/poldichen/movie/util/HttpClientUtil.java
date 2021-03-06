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
