package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.entity.Resource;
import org.apache.ibatis.annotations.Param;

public interface IPictureDao {

    public int createOne(@Param("picture") Picture picture);

    public int update(@Param("id") int id, @Param("picture") Picture picture);
}
