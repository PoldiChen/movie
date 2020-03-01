package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Actor;
import com.poldichen.movie.entity.Resource;

public interface IResourceService {

    public int createOne(Resource resource);

    public int update(int id, Resource resource);
}
