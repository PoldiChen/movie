package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.service.inter.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Resp getAll() {
        Resp resp = new Resp();
        List<Movie> movies = movieService.getAll();
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

}