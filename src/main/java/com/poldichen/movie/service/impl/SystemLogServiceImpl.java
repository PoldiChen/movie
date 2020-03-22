package com.poldichen.movie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.poldichen.movie.dao.ISystemLogDao;
import com.poldichen.movie.entity.Celebrity;
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
    public PageInfo<SystemLog> getByType(String type, int pageSize, int pageNum) {
        PageInfo<SystemLog> pageInfo
                = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
                () -> systemLogDao.getByType(type)
        );
        return pageInfo;
    }
}
