package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.IJobDao;
import com.poldichen.movie.dao.IResourceDao;
import com.poldichen.movie.entity.Job;
import com.poldichen.movie.entity.Resource;
import com.poldichen.movie.service.inter.IJobService;
import com.poldichen.movie.service.inter.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author poldi.chen
 * @className ResourceServiceImpl
 * @description TODO
 * @date 2020/3/1 15:40
 **/
@Service
public class JobServiceImpl implements IJobService {

    @Autowired
    private IJobDao jobDao;

    @Override
    public int createOne(Job job) {
        jobDao.createOne(job);
        return job.getId();
    }

    @Override
    public int update(int id, Job job) {
        return jobDao.update(id, job);
    }
}
