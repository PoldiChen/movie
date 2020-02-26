package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Actor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IActorDao {

    public List<Actor> getAll();

    public List<Actor> getByName(@Param("actorName") String actorName);

    public Actor getById(@Param("id") int id);

    public int createOne(@Param("actor") Actor actor);

    public int update(@Param("id") int id, @Param("actor") Actor actor);
}
