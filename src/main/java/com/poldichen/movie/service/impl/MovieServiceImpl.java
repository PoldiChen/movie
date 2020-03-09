package com.poldichen.movie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.poldichen.movie.dao.IMovieDao;
import com.poldichen.movie.entity.Actor;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.entity.Resource;
import com.poldichen.movie.service.inter.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<Movie> getAll(Map<String, String> paramsMap, int pageNum, int pageSize) {
        PageInfo<Movie> pageInfo
                = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
                        () -> movieDao.getAll(paramsMap)
        );
        return pageInfo.getList();
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
        return 1;
    }

    @Override
    public int updateMovieResource(int movieId, List<Integer> resourceIds) {
        movieDao.deleteMovieResource(movieId);
        for (int resourceId : resourceIds) {
            movieDao.addMovieResource(movieId, resourceId);
        }
        return 1;
    }


}
