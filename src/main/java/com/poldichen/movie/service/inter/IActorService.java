package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Actor;

import java.util.List;

public interface IActorService {

    public List<Actor> getAll(String actorName);

    public Actor getById(int id);

    public int createOne(Actor actor);

    public int update(int id, Actor actor);
}
