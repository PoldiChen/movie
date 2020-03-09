package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Actor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IActorDao {

    List<Actor> getAll();

    List<Actor> getByName(@Param("actorName") String actorName);

    Actor getById(@Param("id") int id);

    int createOne(@Param("actor") Actor actor);

    int update(@Param("id") int id, @Param("actor") Actor actor);

    int deleteActorCover(@Param("actor_id") int actorId);

    int addActorCover(@Param("actor_id") int actorId, @Param("picture_id") int pictureId);
}
