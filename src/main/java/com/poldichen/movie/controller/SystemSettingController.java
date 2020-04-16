package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.entity.SystemSetting;
import com.poldichen.movie.service.inter.ISystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author poldi.chen
 * @className SystemSettingController
 * @description TODO
 * @date 2020/4/16 22:22
 **/
@RestController
public class SystemSettingController {

    @Autowired
    private ISystemSettingService systemSettingService;

    @RequestMapping(value = "/system_setting", method = RequestMethod.POST)
    public Resp createOne(@RequestBody String systemSettingStr) {
        Resp resp = new Resp();
        SystemSetting systemSetting = JSON.parseObject(systemSettingStr, new TypeReference<SystemSetting>(){});
        int systemSettingId = systemSettingService.createOne(systemSetting);
        resp.setData(systemSettingId);
        return resp;
    }

    @RequestMapping(value = "/system_setting", method = RequestMethod.GET)
    public Resp getAll() {
        Resp resp = new Resp();
        List<SystemSetting> systemSettings = systemSettingService.getAll();
        resp.setData(systemSettings);
        return resp;
    }

    @RequestMapping(value = "/system_setting/{id}", method = RequestMethod.PUT)
    public Resp update(@PathVariable int id, @RequestBody String systemSettingStr) {
        Resp resp = new Resp();
        SystemSetting systemSetting = JSON.parseObject(systemSettingStr, new TypeReference<SystemSetting>(){});
        int result = systemSettingService.update(id, systemSetting);
        resp.setData(result);
        return resp;
    }
}
