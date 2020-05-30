package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Job;
import com.poldichen.movie.entity.Resource;

public interface IJobService {

    int createOne(Job job);

    int update(int id, Job job);
}
