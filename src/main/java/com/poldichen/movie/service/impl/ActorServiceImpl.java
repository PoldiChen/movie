package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.IActorDao;
import com.poldichen.movie.entity.Actor;
import com.poldichen.movie.service.inter.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author poldi.chen
 * @className ActorServiceImpl
 * @description TODO
 * @date 2020/2/18 22:14
 **/
@Service
public class ActorServiceImpl implements IActorService {

    @Autowired
    private IActorDao actorDao;

    @Override
    public List<Actor> getAll() {
        return actorDao.getAll();
    }

    @Override
    public Actor getById(int id) {
        return actorDao.getById(id);
    }

    @Override
    public int createOne(Actor actor) {
        return actorDao.createOne(actor);
    }
}