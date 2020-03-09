package com.poldichen.movie.dao;

import com.poldichen.movie.entity.SystemLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISystemLogDao {

    int createOne(@Param("systemLog") SystemLog systemLog);

    List<SystemLog> getByType(@Param("type") String type);

}
