package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.IMovieDao;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.service.inter.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author poldi.chen
 * @className MovieServiceImpl
 * @description TODO
 * @date 2019/8/26 14:18
 **/
@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    private IMovieDao movieDao;

    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
