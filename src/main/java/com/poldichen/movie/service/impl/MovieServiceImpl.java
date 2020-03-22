package com.poldichen.movie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.poldichen.movie.dao.IMovieDao;
import com.poldichen.movie.entity.Celebrity;
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

    public PageInfo<Movie> getAll(Map<String, String> paramsMap, int pageNum, int pageSize) {
        PageInfo<Movie> pageInfo
                = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
                        () -> movieDao.getAll(paramsMap)
        );
        return pageInfo;
    }

    public Movie getById(int id) {
        return movieDao.getById(id);
    }

    @Override
    public int createOne(Movie movie) {
        movieDao.createOne(movie);
        int movieId = movie.getId();

        List<Celebrity> actors = movie.getActors();
        for (Celebrity celebrity : actors) {
            movieDao.addMovieActor(movieId, celebrity.getId());
        }

        List<Celebrity> directors = movie.getDirectors();
        for (Celebrity celebrity : directors) {
            movieDao.addMovieDirector(movieId, celebrity.getId());
        }

        List<Celebrity> writers = movie.getWriters();
        for (Celebrity celebrity : writers) {
            movieDao.addMovieWriter(movieId, celebrity.getId());
        }

        List<Picture> covers = movie.getCovers();
        for (Picture picture : covers) {
            movieDao.addMovieCover(movieId, picture.getId());
        }

        List<Picture> coverDetails = movie.getCoverDetails();
        if (coverDetails != null) {
            for (Picture picture : coverDetails) {
                movieDao.addMovieCoverDetail(movieId, picture.getId());
            }
        }


        List<Picture> screenshots = movie.getScreenshots();
        if (screenshots != null) {
            for (Picture picture : screenshots) {
                movieDao.addMovieScreenshot(movieId, picture.getId());
            }
        }


        List<Resource> resources = movie.getResources();
        if (resources != null) {
            for (Resource resource : resources) {
                movieDao.addMovieResource(movieId, resource.getId());
            }
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
