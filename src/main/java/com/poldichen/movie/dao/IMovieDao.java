package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IMovieDao {

    List<Movie> getAll(Map<String, String> paramsMap);

    Movie getById(@Param("id") int id);

    int createOne(@Param("movie") Movie movie);

    int update(@Param("id") int id, @Param("movie") Movie movie);

    int deleteMovieActor(@Param("movie_id") int movieId);
    int addMovieActor(@Param("movie_id") int movieId, @Param("celebrity_id") int celebrityId);

    int deleteMovieDirector(@Param("movie_id") int movieId);
    int addMovieDirector(@Param("movie_id") int movieId, @Param("celebrity_id") int celebrityId);

    int deleteMovieWriter(@Param("movie_id") int movieId);
    int addMovieWriter(@Param("movie_id") int movieId, @Param("celebrity_id") int celebrityId);

    int deleteMovieCover(@Param("movie_id") int movieId);
    int addMovieCover(@Param("movie_id") int movieId, @Param("picture_id") int pictureId);

    int deleteMovieCoverDetail(@Param("movie_id") int movieId);
    int addMovieCoverDetail(@Param("movie_id") int movieId, @Param("picture_id") int pictureId);

    int deleteMovieScreenshot(@Param("movie_id") int movieId);
    int addMovieScreenshot(@Param("movie_id") int movieId, @Param("picture_id") int pictureId);

    int deleteMovieResource(@Param("movie_id") int movieId);
    int addMovieResource(@Param("movie_id") int movieId, @Param("resource_id") int resourceId);
}
