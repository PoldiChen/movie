package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Movie;

import java.util.List;
import java.util.Map;

public interface IMovieService {

    List<Movie> getAll(Map<String, String> paramsMap, int pageNum, int pageSize);

    Movie getById(int id);

    int createOne(Movie movie);

    int update(int id, Movie movie);

    int updateMovieActor(int movieId, List<Integer> actorIds);

    int updateMovieResource(int movieId, List<Integer> resourceIds);
}
