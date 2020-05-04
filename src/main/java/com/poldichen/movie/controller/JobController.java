package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSONObject;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.job.JobAnnotation;
import com.poldichen.movie.job.MovieJob;
import com.poldichen.movie.job.PictureJob;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author poldi.chen
 * @className JobController
 * @description TODO
 * @date 2020/4/18 11:41
 **/
public class JobController {

    public Resp getAllJobs() {
        Resp resp = new Resp();
        Reflections reflections = new Reflections("reflection.*");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(JobAnnotation.class);

        List<String> className = new ArrayList<>();
        try {
            for (Class<?> clazz : classes){
                className.add(clazz.getCanonicalName());
            }
            resp.setData(className);
        } catch (Exception e) {
            resp.setCode(-1);
            resp.setMessage(e.getMessage());
        }

        return resp;
    }

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
