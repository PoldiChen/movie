package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMovieDao {

    public List<Movie> getAll();

    public Movie getById(@Param("id") int id);
}
