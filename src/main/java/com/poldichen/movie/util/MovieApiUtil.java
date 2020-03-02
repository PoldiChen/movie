package com.poldichen.movie.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author poldi.chen
 * @className MovieApiUtil
 * @description TODO
 * @date 2020/3/1 14:17
 **/
public class MovieApiUtil {

    private static final String URL_ADD_MOVIE = "http://localhost:8080/movie";

    private static final String URL_SEARCH_ACTOR = "http://localhost:8080/actor";

    private static final String URL_ADD_ACTOR = "http://localhost:8080/actor";

    private static final String URL_ADD_PICTURE = "http://localhost:8080/picture";

    private static final String AUTH = "marker-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWNrIiwiZXhwIjoxNTgzNDM0MzM2fQ.EhHGVyHWXAXb7IJ1lZN6FTX3Ex83QU_NmLINF5OEYjVv3tqmj9-49GZ3maJodIUC1ZrdzCXQI3pGarId5zvpfQ";

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("path", "/");
        params.put("file_name", "test.jpg");
        System.out.println(addPicture(params));
    }

    public static int addPicture(Map<String, Object> params) {
        String response = HttpClientUtil.doPost(URL_ADD_PICTURE, AUTH, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static String addMovie(Map<String, Object> params) {
        return HttpClientUtil.doPost(URL_ADD_MOVIE, AUTH, params);
    }

    public static int addActor(Map<String, Object> params) {
        String response = HttpClientUtil.doPost(URL_ADD_ACTOR, AUTH, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static boolean isActorExist(String actorName) {
        String response = HttpClientUtil.doGet(URL_SEARCH_ACTOR + "?name=" + actorName, AUTH);
        JSONObject responseObject = JSONObject.parseObject(response);
        JSONArray data = responseObject.getJSONArray("data");
        return data.size() > 0;
    }

    public static void getAuth(String userName, String password) {
        String url = "http://localhost:8080/login";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        System.out.println(responseHeaders.get("Authorization"));
//        HttpClientUtil.doPost(url, "", params);
    }
}
