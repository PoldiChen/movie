package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Resource;
import org.apache.ibatis.annotations.Param;

public interface IResourceDao {

    int createOne(@Param("resource")Resource resource);

    int update(@Param("id") int id, @Param("resource") Resource resource);

}
