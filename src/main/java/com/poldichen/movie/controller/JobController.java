package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSONObject;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.job.JobAnnotation;
import com.poldichen.movie.job.MovieListJob;
import com.poldichen.movie.job.PictureJob;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author poldi.chen
 * @className JobController
 * @description TODO
 * @date 2020/4/18 11:41
 **/
@RestController
public class JobController {


    @RequestMapping(value="/job", method = RequestMethod.GET)
    public Resp getAllJobs() {
        Resp resp = new Resp();
        Reflections reflections = new Reflections("com.poldichen.movie.job.*");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(JobAnnotation.class);

        List<Map<String, Object>> jobs = new ArrayList<>();
        try {
            for (Class<?> clazz : classes) {
                Map<String, Object> job = new HashMap<>();
                job.put("job_name", clazz.getCanonicalName());
                List<Map<String, String>> parameterList = new ArrayList<>();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.getName().equals("execute")) {
                        Parameter[] parameters = method.getParameters();
                        for (Parameter parameter : parameters) {
                            Map<String, String> parameterMap = new HashMap<>();
                            parameterMap.put("param_name", parameter.getName());
                            parameterMap.put("param_type", parameter.getType().toString());
                            parameterList.add(parameterMap);
                        }
                    }
                }
                job.put("parameter", parameterList);

                jobs.add(job);
            }
            resp.setData(jobs);
        } catch (Exception e) {
            resp.setCode(-1);
            resp.setMessage(e.getMessage());
        }

        return resp;
    }

    @RequestMapping(value = "/job", method = RequestMethod.POST)
    public Resp createJob(@RequestBody String param) {
        Resp resp = new Resp();
        JSONObject jsonObject = JSONObject.parseObject(param);



        try {

            Class clazz = Class.forName("com.poldichen.movie.job.CelebrityJob");
            Method method = clazz.getMethod("execute");
            method.invoke(clazz, "a", "b");

        } catch (Exception e) {
            resp.setCode(-1);
            resp.setMessage(e.getMessage());
        }

        return resp;
    }

    public static void main(String[] args) {
        try {

            Class clazz = Class.forName("com.poldichen.movie.job.CelebrityJob");

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("execute")) {
                    method.invoke(clazz, "a", "b");
                }
            }

//            Method method = clazz.getMethod("execute", String.class, String.class);
//            method.invoke(clazz, "a", "b");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
