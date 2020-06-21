package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.poldichen.movie.config.MovieConfig;
import com.poldichen.movie.entity.Job;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.job.JobAnnotation;
import com.poldichen.movie.job.MovieListJob;
import com.poldichen.movie.job.PictureJob;
import com.poldichen.movie.service.inter.IJobService;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.scope.MethodScope;

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

    @Autowired
    private IJobService jobService;

    private static final String JOB_PACKAGE = "com.poldichen.movie.job.*";
    private static final String METHOD_DECLARE = "executeDeclare";
    private static final String METHOD_EXECUTE = "execute";

    @Autowired
    private MovieConfig movieConfig;


    @RequestMapping(value="/job", method = RequestMethod.GET)
    public Resp getAllJobs() {
        System.out.println("JobController@getAllJobs");
        System.out.println(movieConfig.getHost());
        Resp resp = new Resp();
        Reflections reflections = new Reflections(JOB_PACKAGE);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(JobAnnotation.class);

        List<Map<String, Object>> jobs = new ArrayList<>();
        try {
            for (Class<?> clazz : classes) {
                Map<String, Object> job = new HashMap<>();
                job.put("job_name", clazz.getCanonicalName());
                List<Map<String, String>> parameterList = new ArrayList<>();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.getName().equals(METHOD_DECLARE)) {
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

        String jobName = jsonObject.getString("job_name");
        String jobArgs = jsonObject.getString("job_params");

        try {
            Class clazz = Class.forName(jobName);
            System.out.println(clazz.getName());
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(METHOD_EXECUTE)) {
                    method.invoke(clazz.newInstance(), jobArgs);
                    Job job = new Job();
                    job.setJobName(jobName);
                    job.setClassName(jobName);
                    job.setArguments(jobArgs);
                    job.setStatus("0");
                    int jobId = jobService.createOne(job);
                    resp.setData(jobId);
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCode(-1);
            resp.setMessage(e.getMessage());
        }

        return resp;
    }

    public static void main(String[] args) {
        try {

            Class clazz = Class.forName("com.poldichen.movie.job.MovieListJob");
            System.out.println(clazz.getName());

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("executeReal")) {
                    method.invoke(clazz.newInstance(), "aaa");
                }
//                System.out.println(method.getName());
            }

//            Method method = clazz.getMethod("executeReal", String[].class);
//            method.invoke(clazz, new String[]{"aaa"});

//            Class clazz = Class.forName("com.poldichen.movie.job.CelebrityJob");
//            Method[] methods = clazz.getMethods();
//            for (Method method : methods) {
//                if (method.getName().equals("execute")) {
//                    method.invoke(clazz, "a", "b");
//                }
//            }
//            Method method = clazz.getMethod("execute", String.class, String.class);
//            method.invoke(clazz, "a", "b");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
