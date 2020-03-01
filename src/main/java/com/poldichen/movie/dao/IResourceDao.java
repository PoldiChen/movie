package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Resource;
import org.apache.ibatis.annotations.Param;

public interface IResourceDao {

    public int createOne(@Param("resource")Resource resource);

    public int update(@Param("id") int id, @Param("resource") Resource resource);

}
