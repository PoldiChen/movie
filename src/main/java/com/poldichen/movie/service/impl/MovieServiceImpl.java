package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.IMovieDao;
import com.poldichen.movie.entity.Actor;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.entity.Resource;
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

    public List<Movie> getAll(String movieName) {
        if (movieName == null || movieName.equals("")) {
            return movieDao.getAll();
        } else {
            return movieDao.getByName(movieName);
        }
    }

    public Movie getById(int id) {
        return movieDao.getById(id);
    }

    @Override
    public int createOne(Movie movie) {
        movieDao.createOne(movie);
        int movieId = movie.getId();

        List<Actor> actors = movie.getActors();
        for (Actor actor : actors) {
            movieDao.addMovieActor(movieId, actor.getId());
        }

        List<Picture> covers = movie.getCovers();
        for (Picture picture : covers) {
            movieDao.addMovieCover(movieId, picture.getId());
        }

        List<Picture> coverDetails = movie.getCoverDetails();
        for (Picture picture : coverDetails) {
            movieDao.addMovieCoverDetail(movieId, picture.getId());
        }

        List<Picture> screenshots = movie.getScreenshots();
        for (Picture picture : screenshots) {
            movieDao.addMovieScreenshot(movieId, picture.getId());
        }

        List<Resource> resources = movie.getResources();
        for (Resource resource : resources) {
            movieDao.addMovieResource(movieId, resource.getId());
        }

        return movieId;
    }

    @Override
    public int update(int id, Movie movie) {
        return movieDao.update(id, movie);
    }

    @Override
    public int updateMovieActor(int movieId, List<Integer> actorIds) {
        movieDao.deleteMovieActor(movieId);
        for (int actorId : actorIds) {
            movieDao.addMovieActor(movieId, actorId);
        }
        return 0;
    }
}
