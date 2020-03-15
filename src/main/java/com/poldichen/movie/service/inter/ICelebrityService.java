package com.poldichen.movie.service.inter;

import com.github.pagehelper.PageInfo;
import com.poldichen.movie.entity.Celebrity;
import java.util.Map;

public interface ICelebrityService {

    PageInfo<Celebrity> getAll(Map<String, String> paramsMap, int pageNum, int pageSize);

    Celebrity getById(int id);

    int createOne(Celebrity celebrity);

    int update(int id, Celebrity celebrity);
}
