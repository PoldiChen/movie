package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Actor;
import com.poldichen.movie.entity.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMovieDao {

    public List<Movie> getAll();

    public List<Movie> getByName(@Param("movieName") String movieName);

    public Movie getById(@Param("id") int id);

    public int createOne(@Param("movie") Movie movie);

    public int update(@Param("id") int id, @Param("movie") Movie movie);

    public int deleteMovieActor(@Param("movie_id") int movieId);

    public int addMovieActor(@Param("movie_id") int movieId, @Param("actor_id") int actorId);

    public int deleteMovieCover(@Param("movie_id") int movieId);

    public int addMovieCover(@Param("movie_id") int movieId, @Param("picture_id") int pictureId);

    public int deleteMovieCoverDetail(@Param("movie_id") int movieId);

    public int addMovieCoverDetail(@Param("movie_id") int movieId, @Param("picture_id") int pictureId);


    public int deleteMovieScreenshot(@Param("movie_id") int movieId);

    public int addMovieScreenshot(@Param("movie_id") int movieId, @Param("picture_id") int pictureId);

    public int deleteMovieResource(@Param("movie_id") int movieId);

    public int addMovieResource(@Param("movie_id") int movieId, @Param("resource_id") int resourceId);
}
