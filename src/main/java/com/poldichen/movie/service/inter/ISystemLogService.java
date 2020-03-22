package com.poldichen.movie.service.inter;

import com.github.pagehelper.PageInfo;
import com.poldichen.movie.entity.SystemLog;

import java.util.List;

public interface ISystemLogService {

    int createOne(SystemLog systemLog);

    PageInfo<SystemLog> getByType(String type, int pageSize, int pageNum);

}
