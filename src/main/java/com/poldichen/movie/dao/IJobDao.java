package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Job;
import com.poldichen.movie.entity.Resource;
import org.apache.ibatis.annotations.Param;

public interface IJobDao {

    int createOne(@Param("job") Job job);

    int update(@Param("id") int id, @Param("job") Job job);

}
