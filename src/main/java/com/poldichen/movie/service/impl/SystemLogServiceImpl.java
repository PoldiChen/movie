package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.ISystemLogDao;
import com.poldichen.movie.entity.SystemLog;
import com.poldichen.movie.service.inter.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author poldi.chen
 * @className SystemLogServiceImpl
 * @description TODO
 * @date 2020/3/8 10:25
 **/
@Service
public class SystemLogServiceImpl implements ISystemLogService {

    @Autowired
    private ISystemLogDao systemLogDao;

    @Override
    public int createOne(SystemLog systemLog) {
        return systemLogDao.createOne(systemLog);
    }

    @Override
    public List<SystemLog> getByType(String type) {
        return systemLogDao.getByType(type);
    }
}
