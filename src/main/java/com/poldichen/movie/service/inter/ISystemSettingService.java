package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.SystemSetting;

import java.util.List;

public interface ISystemSettingService {

    int createOne(SystemSetting systemSetting);

    List<SystemSetting> getAll();

    int update(int id, SystemSetting systemSetting);
}
