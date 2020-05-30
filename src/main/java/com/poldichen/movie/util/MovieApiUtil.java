package com.poldichen.movie.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.entity.ProxyAddress;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    private static final String URL_ADD_PROXY_ADDRESS = "http://localhost:8080/proxy_address";
    private static final String URL_GET_PROXY_ADDRESS = "http://localhost:8080/proxy_address";

    private static String auth = "empty";

    public static int updateActor(int actorId, Map<String, Object> params) {
        String response = HttpClientUtil.doPut(URL_UPDATE_ACTOR + "/" + actorId, auth, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static int updateMovieResource(int movieId, Map<String, Object> params) {
        String response = HttpClientUtil.doPut(URL_UPDATE_MOVIE_RESOURCE + "/" + movieId + "/resource", auth, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static int addSystemLog(Map<String, Object> params) {
        System.out.println("addSystemLog");
        System.out.println(auth);
        String response = HttpClientUtil.doPost(URL_ADD_SYSTEM_LOG, auth, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static int addProxyAddress(Map<String, Object> params) {
        String response = HttpClientUtil.doPost(URL_ADD_PROXY_ADDRESS, auth, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static int addResource(Map<String, Object> params) {
        String response = HttpClientUtil.doPost(URL_ADD_RESOURCE, auth, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static List<Picture> getPicture(String fileName) {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Authorization", auth);
        String response = HttpClientUtil.doGet(URL_SEARCH_PICTURE + "?file_name=" + fileName, headerParams);
        JSONObject jsonObject = JSONObject.parseObject(response);
        List<Picture> pictures = JSON.parseObject(jsonObject.getString("data"), new TypeReference<List<Picture>>(){});
        return pictures;
    }

    public static List<ProxyAddress> getProxyAddress() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Authorization", auth);
        String response = HttpClientUtil.doGet(URL_GET_PROXY_ADDRESS, headerParams);
        JSONObject jsonObject = JSONObject.parseObject(response);
        List<ProxyAddress> proxyAddresses = JSON.parseObject(jsonObject.getString("data"), new TypeReference<List<ProxyAddress>>(){});
        return proxyAddresses;
    }

    public static List<Movie> getAllMovie() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Authorization", auth);
        String response = HttpClientUtil.doGet(URL_SEARCH_MOVIE, headerParams);
        JSONObject jsonObject = JSONObject.parseObject(response);
        List<Movie> movies = JSON.parseObject(jsonObject.getString("data"), new TypeReference<List<Movie>>(){});
        return movies;
    }

    public static int addPicture(Map<String, Object> params) {
        String response = HttpClientUtil.doPost(URL_ADD_PICTURE, auth, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static String addMovie(Map<String, Object> params) {
        return HttpClientUtil.doPost(URL_ADD_MOVIE, auth, params);
    }

    public static int addActor(Map<String, Object> params) {
        String response = HttpClientUtil.doPost(URL_ADD_ACTOR, auth, params);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int result = jsonObject.getInteger("data");
        return result;
    }

    public static int isActorExist(String code, String name) {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Authorization", auth);
        String url = URL_SEARCH_ACTOR + "?pageSize=1&pageNum=1";
        if (code != null) {
            url += "&code=" + code;
        }
        if (name != null) {
            url += "&name=" + name;
        }
        String response = HttpClientUtil.doGet(url, headerParams);
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
        headerParams.put("Authorization", auth);
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
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);
        headers.add("Authorization", "");
        headers.add("content-type", "text/html;charset=utf-8");
        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(params), headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/login", HttpMethod.POST, requestEntity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        auth = responseHeaders.get("Authorization").toString().replace("[", "").replace("]", "");
        System.out.println("getAuth");
        System.out.println(auth);
    }

}
