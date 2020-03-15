package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Celebrity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ICelebrityDao {

    List<Celebrity> getAll(Map<String, String> paramsMap);

    Celebrity getById(@Param("id") int id);

    int createOne(@Param("celebrity") Celebrity celebrity);

    int update(@Param("id") int id, @Param("celebrity") Celebrity celebrity);

    int deleteCelebrityCover(@Param("celebrity_id") int celebrityId);

    int addCelebrityCover(@Param("celebrity_id") int celebrityId, @Param("picture_id") int pictureId);
}
