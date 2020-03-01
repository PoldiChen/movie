package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.entity.Resource;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.service.inter.IPictureService;
import com.poldichen.movie.service.inter.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author poldi.chen
 * @className PictureController
 * @description TODO
 * @date 2020/3/1 15:42
 **/
@RestController
public class PictureController {

    @Autowired
    private IPictureService pictureService;

    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public Resp createOne(@RequestBody String pictureStr) {
        System.out.println("PictureController@createOne");
        System.out.println(pictureStr);
        Resp resp = new Resp();
        Picture picture = JSON.parseObject(pictureStr, new TypeReference<Picture>(){});
        System.out.println(picture);
        int pictureId = pictureService.createOne(picture);
        resp.setData(pictureId);
        return resp;
    }

    @RequestMapping(value = "/picture/{id}", method = RequestMethod.PUT)
    public Resp update(@PathVariable int id, @RequestBody String pictureStr) {
        Resp resp = new Resp();
        Picture picture = JSON.parseObject(pictureStr, new TypeReference<Picture>(){});
        int result = pictureService.update(id, picture);
        resp.setData(result);
        return resp;
    }
}
