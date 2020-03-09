package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Resource;

public interface IResourceService {

    int createOne(Resource resource);

    int update(int id, Resource resource);
}
