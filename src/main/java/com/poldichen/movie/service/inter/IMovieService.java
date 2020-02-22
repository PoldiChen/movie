package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Movie;

import java.util.List;

public interface IMovieService {

    public List<Movie> getAll(String movieName);

    public Movie getById(int id);

    public int createOne(Movie movie);

    public int addMovieActor(int movieId, int actorId);
}
