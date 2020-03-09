package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.Resource;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.service.inter.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author poldi.chen
 * @className ResourceController
 * @description TODO
 * @date 2020/3/1 15:42
 **/
@RestController
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public Resp createOne(@RequestBody String resourceStr) {
        Resp resp = new Resp();
        Resource resource = JSON.parseObject(resourceStr, new TypeReference<Resource>(){});
        int resourceId = resourceService.createOne(resource);
        resp.setData(resourceId);
        return resp;
    }

    @RequestMapping(value = "/resource/{id}", method = RequestMethod.PUT)
    public Resp update(@PathVariable int id, @RequestBody String resourceStr) {
        Resp resp = new Resp();
        Resource resource = JSON.parseObject(resourceStr, new TypeReference<Resource>(){});
        int result = resourceService.update(id, resource);
        resp.setData(result);
        return resp;
    }
}
