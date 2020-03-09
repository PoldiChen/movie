package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.entity.SystemLog;
import com.poldichen.movie.service.inter.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author poldi.chen
 * @className SystemLogController
 * @description TODO
 * @date 2020/3/8 10:26
 **/
@RestController
public class SystemLogController {

    @Autowired
    private ISystemLogService systemLogService;

    @RequestMapping(value = "/system_log", method = RequestMethod.POST)
    public Resp createOne(@RequestBody String systemLogStr) {
        Resp resp = new Resp();
        SystemLog systemLog = JSON.parseObject(systemLogStr, new TypeReference<SystemLog>(){});
        int systemLogId = systemLogService.createOne(systemLog);
        resp.setData(systemLogId);
        return resp;
    }

    @RequestMapping(value = "/system_log", method = RequestMethod.GET)
    public Resp getByType(@RequestParam(value = "type") String type) {
        Resp resp = new Resp();
        List<SystemLog> systemLogs = systemLogService.getByType(type);
        resp.setData(systemLogs);
        return resp;
    }
}
