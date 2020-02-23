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


/**
 * @author poldi.chen
 * @className HttpClientUtil
 * @description TODO
 * @date 2020/2/23 14:42
 **/
public class HttpClientUtil {

    public static void main(String[] args) {
        String auth = "marker-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWNrIiwiZXhwIjoxNTgyNDQ0NjczfQ.iguY3PZsBuMKSBY6ZEDMhTIixilK8KGiPPaTa-SdmIAlawPAC2-mimgVOLHEi3xeYiqFBRDQtp7oz_HOkrI37Q";
//        doGet("http://114.67.87.197:8080/movie", auth);

        Map<String, String> params = new HashMap<>();
        params.put("name", "ttt");
        params.put("publish_date", "2020-02-23");
        doPost("http://114.67.87.197:8080/movie", auth, params);
    }

    public static void doGet(String url, String auth) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String responseStr = response.getBody();
        System.out.println(responseStr);
    }

    public static void doPost(String url, String auth, Map<String, String> requestBody) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);
        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(requestBody), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String responseStr = response.getBody();
        System.out.println(responseStr);

    }

}
