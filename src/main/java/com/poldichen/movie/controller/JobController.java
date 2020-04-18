package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSONObject;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.job.MovieJob;
import com.poldichen.movie.job.PictureJob;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author poldi.chen
 * @className JobController
 * @description TODO
 * @date 2020/4/18 11:41
 **/
public class JobController {

    @RequestMapping(value = "/job/movie", method = RequestMethod.POST)
    public Resp createMovieJob(@RequestBody String param) {
        Resp resp = new Resp();
        JSONObject jsonObject = JSONObject.parseObject(param);
        int indexEnd = jsonObject.getInteger("index_end");
        MovieJob.executeGetList(indexEnd);
        return resp;
    }

    @RequestMapping(value = "/job/picture", method = RequestMethod.POST)
    public Resp createPictureJob(@RequestBody String param) {
        Resp resp = new Resp();
        JSONObject jsonObject = JSONObject.parseObject(param);
        String directory = jsonObject.getString("directory");
        String keyWord = jsonObject.getString("key_word");
        PictureJob.executeDownloadPicture(directory, keyWord);
        return resp;
    }
}
