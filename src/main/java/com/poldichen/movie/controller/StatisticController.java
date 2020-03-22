package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.Resource;
import com.poldichen.movie.entity.Resp;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author poldi.chen
 * @className StatisticController
 * @description TODO
 * @date 2020/3/15 18:21
 **/
@RestController
public class StatisticController {

    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public Resp getStatistic() {
        Resp resp = new Resp();
        Map<String, String> data = new HashMap<>();
        data.put("user", "1");
        data.put("movie", "375");
        data.put("celebrity", "4433");
        resp.setData(data);
        return resp;
    }

}
