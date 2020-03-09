package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPictureDao {

    int createOne(@Param("picture") Picture picture);

    int update(@Param("id") int id, @Param("picture") Picture picture);

    List<Picture> getAll(@Param("file_name") String fileName);
}
