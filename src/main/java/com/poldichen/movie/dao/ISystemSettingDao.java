package com.poldichen.movie.dao;

import com.poldichen.movie.entity.SystemSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISystemSettingDao {

    int createOne(@Param("systemSetting") SystemSetting systemSetting);

    int update(@Param("id") int id, @Param("systemSetting") SystemSetting systemSetting);

    List<SystemSetting> getAll();

}
