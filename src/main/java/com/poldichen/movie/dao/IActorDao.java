package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Actor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IActorDao {

    public List<Actor> getAll();

    public Actor getById(@Param("id") int id);

    public int createOne(@Param("actor") Actor actor);
}
