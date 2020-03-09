package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Actor;

import java.util.List;

public interface IActorService {

    List<Actor> getAll(String actorName);

    Actor getById(int id);

    int createOne(Actor actor);

    int update(int id, Actor actor);
}
