package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.poldichen.movie.entity.Celebrity;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.job.MovieSingleJob;
import com.poldichen.movie.service.inter.ICelebrityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author poldi.chen
 * @className CelebrityController
 * @description TODO
 * @date 2020/2/18 22:15
 **/
@RestController
public class CelebrityController {

    private static final Logger logger = LoggerFactory.getLogger(CelebrityController.class);

    @Autowired
    private ICelebrityService celebrityService;

    @RequestMapping(value = "/celebrity", method = RequestMethod.GET)
    public Resp getAll(@RequestParam(value = "pageNum") int pageNum,
                       @RequestParam(value = "pageSize") int pageSize,
                       @RequestParam(value = "code", required = false) String celebrityCode,
                       @RequestParam(value = "name", required = false) String celebrityName) {
        logger.info("CelebrityController@getAll");
        Resp resp = new Resp();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("code", celebrityCode);
        paramsMap.put("name", celebrityName);
        PageInfo<Celebrity> pageInfo = celebrityService.getAll(paramsMap, pageNum, pageSize);
        resp.setData(pageInfo);
        return resp;
    }

    @RequestMapping(value = "/celebrity/{id}", method = RequestMethod.GET)
    public Resp getById(@PathVariable int id) {
        Resp resp = new Resp();
        Celebrity celebrity = celebrityService.getById(id);
        resp.setData(celebrity);
        return resp;
    }

    @RequestMapping(value = "/celebrity", method = RequestMethod.POST)
    public Resp createOne(@RequestBody String celebrityStr) {
        Resp resp = new Resp();
        Celebrity celebrity = JSON.parseObject(celebrityStr, new TypeReference<Celebrity>(){});
        int celebrityId = celebrityService.createOne(celebrity);
        resp.setData(celebrityId);
        return resp;
    }

    @RequestMapping(value = "/celebrity/{id}", method = RequestMethod.PUT)
    public Resp update(@PathVariable int id, @RequestBody String celebrityStr) {
        Resp resp = new Resp();
        Celebrity celebrity = JSON.parseObject(celebrityStr, new TypeReference<Celebrity>(){});
        int result = celebrityService.update(id, celebrity);
        resp.setData(result);
        return resp;
    }
}
