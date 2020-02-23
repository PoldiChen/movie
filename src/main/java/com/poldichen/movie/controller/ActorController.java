package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.Actor;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.service.inter.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author poldi.chen
 * @className ActorController
 * @description TODO
 * @date 2020/2/18 22:15
 **/
@RestController
public class ActorController {

    @Autowired
    private IActorService actorService;

    @RequestMapping(value="/actor", method = RequestMethod.GET)
    public Resp getAll(@RequestParam(value = "name", required = false) String actorName) {
        Resp resp = new Resp();
        List<Actor> actors = actorService.getAll(actorName);
        resp.setData(actors);
        return resp;
    }

    @RequestMapping(value="/actor/{id}", method = RequestMethod.GET)
    public Resp getById(@PathVariable int id) {
        Resp resp = new Resp();
        Actor actor = actorService.getById(id);
        resp.setData(actor);
        return resp;
    }

    @RequestMapping(value="/actor", method = RequestMethod.POST)
    public Resp createOne(@RequestBody String actorStr) {
        System.out.println("ActorController@createOne");
        System.out.println(actorStr);
        Resp resp = new Resp();
        Actor actor = JSON.parseObject(actorStr, new TypeReference<Actor>(){});
        int actorId = actorService.createOne(actor);
        resp.setData(actorId);
        return resp;
    }
}
