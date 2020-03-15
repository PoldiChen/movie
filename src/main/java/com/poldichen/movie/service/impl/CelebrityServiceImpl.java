package com.poldichen.movie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.poldichen.movie.dao.ICelebrityDao;
import com.poldichen.movie.entity.Celebrity;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.service.inter.ICelebrityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author poldi.chen
 * @className CelebrityServiceImpl
 * @description TODO
 * @date 2020/2/18 22:14
 **/
@Service
public class CelebrityServiceImpl implements ICelebrityService {

    @Autowired
    private ICelebrityDao celebrityDao;

    @Override
    public PageInfo<Celebrity> getAll(Map<String, String> paramsMap, int pageNum, int pageSize) {
        PageInfo<Celebrity> pageInfo
                = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
                () -> celebrityDao.getAll(paramsMap)
        );
        return pageInfo;
    }

    @Override
    public Celebrity getById(int id) {
        return celebrityDao.getById(id);
    }

    @Override
    public int createOne(Celebrity celebrity) {
        celebrityDao.createOne(celebrity);
        int actorId = celebrity.getId();
        List<Picture> covers = celebrity.getCovers();
        for (Picture picture : covers) {
            celebrityDao.addCelebrityCover(actorId, picture.getId());
        }
        return celebrity.getId();
    }

    @Override
    public int update(int id, Celebrity celebrity) {
        celebrityDao.update(id, celebrity);
        celebrityDao.deleteCelebrityCover(id);
        List<Picture> covers = celebrity.getCovers();
        for (Picture picture : covers) {
            celebrityDao.addCelebrityCover(id, picture.getId());
        }
        return 1;
    }
}
