package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.IResourceDao;
import com.poldichen.movie.entity.Resource;
import com.poldichen.movie.service.inter.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author poldi.chen
 * @className ResourceServiceImpl
 * @description TODO
 * @date 2020/3/1 15:40
 **/
@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    private IResourceDao resourceDao;

    @Override
    public int createOne(Resource resource) {
        resourceDao.createOne(resource);
        return resource.getId();
    }

    @Override
    public int update(int id, Resource resource) {
        return resourceDao.update(id, resource);
    }
}
