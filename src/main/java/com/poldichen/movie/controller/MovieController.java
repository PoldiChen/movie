package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.service.inter.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author poldi.chen
 * @className MovieController
 * @description TODO
 * @date 2019/8/26 14:18
 **/
@RestController
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @RequestMapping(value="/movie", method = RequestMethod.GET)
    public Resp getAll(@RequestParam(value = "pageNum") int pageNum,
                       @RequestParam(value = "pageSize") int pageSize,
                       @RequestParam(value = "code", required = false) String movieCode,
                       @RequestParam(value = "name", required = false) String movieName) {
        Resp resp = new Resp();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("code", movieCode);
        paramsMap.put("name", movieName);
        List<Movie> movies = movieService.getAll(paramsMap, pageNum, pageSize);
        resp.setData(movies);
        return resp;
    }

    @RequestMapping(value="/movie/{id}", method = RequestMethod.GET)
    public Resp getById(@PathVariable int id) {
        Resp resp = new Resp();
        Movie movie = movieService.getById(id);
        resp.setData(movie);
        return resp;
    }

    @RequestMapping(value="/movie", method = RequestMethod.POST)
    public Resp createOne(@RequestBody String movieStr) {
        System.out.println("MovieController@createOne");
        System.out.println(movieStr);
        Resp resp = new Resp();
        Movie movie = JSON.parseObject(movieStr, new TypeReference<Movie>(){});
        int movieId = movieService.createOne(movie);
        resp.setData(movieId);
        return resp;
    }

    @RequestMapping(value="/movie/{id}", method = RequestMethod.PUT)
    public Resp update(@PathVariable int id, @RequestBody String movieStr) {
        Resp resp = new Resp();
        Movie movie = JSON.parseObject(movieStr, new TypeReference<Movie>(){});
        int result = movieService.update(id, movie);
        resp.setData(result);
        return resp;
    }

    @RequestMapping(value="/movie/{movieId}/actor", method = RequestMethod.PUT)
    public Resp updateMovieActor(@PathVariable("movieId") int movieId, @RequestBody String paramStr) {
        System.out.println("MovieController@updateMovieActor");
        Resp resp = new Resp();
        JSONObject jsonObject = JSON.parseObject(paramStr);
        JSONArray actorIds = jsonObject.getJSONArray("actor_id");
        int result = movieService.updateMovieActor(movieId, JSONObject.parseArray(actorIds.toJSONString(), Integer.class));
        resp.setData(result);
        return resp;
    }

    @RequestMapping(value="/movie/{movieId}/resource", method = RequestMethod.PUT)
    public Resp updateMovieResource(@PathVariable("movieId") int movieId, @RequestBody String paramStr) {
        Resp resp = new Resp();
        JSONObject jsonObject = JSON.parseObject(paramStr);
        JSONArray resourceIds = jsonObject.getJSONArray("resource_id");
        int result = movieService.updateMovieResource(movieId, JSONObject.parseArray(resourceIds.toJSONString(), Integer.class));
        resp.setData(result);
        return resp;
    }

}
