package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMovieDao {

    public List<Movie> getAll();

    public Movie getById(@Param("id") int id);

    public int createOne(@Param("movie") Movie movie);

    public int addMovieActor(@Param("movie_id") int movieId, @Param("actor_id") int actorId);
}
