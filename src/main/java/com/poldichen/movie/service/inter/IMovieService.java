package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Movie;

import java.util.List;

public interface IMovieService {

    public List<Movie> getAll(String movieName);

    public Movie getById(int id);

    public int createOne(Movie movie);

    public int update(int id, Movie movie);

    public int updateMovieActor(int movieId, List<Integer> actorIds);
}
