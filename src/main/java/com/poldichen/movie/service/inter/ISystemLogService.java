package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.SystemLog;

import java.util.List;

public interface ISystemLogService {

    int createOne(SystemLog systemLog);

    List<SystemLog> getByType(String type);

}
