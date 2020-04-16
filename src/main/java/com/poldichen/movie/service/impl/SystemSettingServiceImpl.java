package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.ISystemSettingDao;
import com.poldichen.movie.entity.SystemSetting;
import com.poldichen.movie.service.inter.ISystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author poldi.chen
 * @className SystemSettingServiceImpl
 * @description TODO
 * @date 2020/4/16 22:20
 **/
@Service
public class SystemSettingServiceImpl implements ISystemSettingService {

    @Autowired
    private ISystemSettingDao systemSettingDao;


    @Override
    public int createOne(SystemSetting systemSetting) {
        return systemSettingDao.createOne(systemSetting);
    }

    @Override
    public List<SystemSetting> getAll() {
        return systemSettingDao.getAll();
    }

    @Override
    public int update(int id, SystemSetting systemSetting) {
        return systemSettingDao.update(id, systemSetting);
    }
}
