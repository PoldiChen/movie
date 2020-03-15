package com.poldichen.movie.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Picture;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author poldi.chen
 * @className MovieApiUtil
 * @description TODO
 * @date 2020/3/1 14:17
 **/
public class MovieApiUtil {

    private static final String URL_ADD_MOVIE = "http://localhost:8080/movie";
    private static final String URL_SEARCH_MOVIE = "http://localhost:8080/movie";
    private static final String URL_UPDATE_MOVIE_RESOURCE = "http://localhost:8080/movie";

    private static final String URL_SEARCH_ACTOR = "http://localhost:8080/celebrity";
    private static final String URL_ADD_ACTOR = "http://localhost:8080/celebrity";
    private static final String URL_UPDATE_ACTOR = "http://localhost:8080/celebrity";

    private static final String URL_ADD_PICTURE = "http://localhost:8080/picture";
    private static final String URL_SEARCH_PICTURE = "http://localhost:8080/picture";

    private static final String URL_ADD_SYSTEM_LOG = "http://localhost:8080/system_log";

    private static final String URL_ADD_RESOURCE = "http://localhost:8080/resource";

    private static final String AUTH
            = "marker-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWNrIiwiZXhwIjoxNTg0MjY5ODkxfQ.Z_5sZqrnYJ3lFyHacAEt7gbWUKk4MZ00K3QcJLH0PMGGuAvZ030mjrWBBeCsvRfHJ_X4iarR4w-m3rvMepZQEg";


    public static int updateActor(int actorId, Map<String, Object> params) {
        String response = HttpClientUtil.doPut(URL_UPDATE_ACTOR + "/" + actorId, AUTH, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static int updateMovieResource(int movieId, Map<String, Object> params) {
        String response = HttpClientUtil.doPut(URL_UPDATE_MOVIE_RESOURCE + "/" + movieId + "/resource", AUTH, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static int addSystemLog(Map<String, Object> params) {
        String response = HttpClientUtil.doPost(URL_ADD_SYSTEM_LOG, AUTH, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static int addResource(Map<String, Object> params) {
        String response = HttpClientUtil.doPost(URL_ADD_RESOURCE, AUTH, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static List<Picture> getPicture(String fileName) {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Authorization", AUTH);
        String response = HttpClientUtil.doGet(URL_SEARCH_PICTURE + "?file_name=" + fileName, headerParams);
        JSONObject jsonObject = JSONObject.parseObject(response);
        List<Picture> pictures = JSON.parseObject(jsonObject.getString("data"), new TypeReference<List<Picture>>(){});
        return pictures;
    }

    public static List<Movie> getAllMovie() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Authorization", AUTH);
        String response = HttpClientUtil.doGet(URL_SEARCH_MOVIE, headerParams);
        JSONObject jsonObject = JSONObject.parseObject(response);
        List<Movie> movies = JSON.parseObject(jsonObject.getString("data"), new TypeReference<List<Movie>>(){});
        return movies;
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

    public static int isActorExist(String code) {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Authorization", AUTH);
        String response = HttpClientUtil.doGet(URL_SEARCH_ACTOR + "?pageSize=1&pageNum=1&code=" + code, headerParams);
        JSONObject responseObject = JSONObject.parseObject(response);
        JSONObject data = responseObject.getJSONObject("data");
        JSONArray list = data.getJSONArray("list");
        if (list.size() == 0) {
            return 0;
        } else {
            JSONObject actorObject = JSONObject.parseObject(list.get(0).toString());
            return actorObject.getInteger("id");
        }
    }

    public static int isMovieExist(String movieCode) {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Authorization", AUTH);
        String response = HttpClientUtil.doGet(URL_SEARCH_MOVIE + "?pageSize=1&pageNum=1&code=" + movieCode, headerParams);
        JSONObject responseObject = JSONObject.parseObject(response);
        JSONObject data1 = responseObject.getJSONObject("data");
        JSONArray list = data1.getJSONArray("list");
        if (list.size() == 0) {
            return 0;
        } else {
            JSONObject movieObject = JSONObject.parseObject(list.get(0).toString());
            return movieObject.getInteger("id");
        }
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
