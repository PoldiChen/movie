package com.poldichen.movie.controller;

import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.service.inter.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
