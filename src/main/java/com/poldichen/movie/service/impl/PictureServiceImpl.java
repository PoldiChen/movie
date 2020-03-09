package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.IPictureDao;
import com.poldichen.movie.dao.IResourceDao;
import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.entity.Resource;
import com.poldichen.movie.service.inter.IPictureService;
import com.poldichen.movie.service.inter.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author poldi.chen
 * @className PictureServiceImpl
 * @description TODO
 * @date 2020/3/1 15:40
 **/
@Service
public class PictureServiceImpl implements IPictureService {

    @Autowired
    private IPictureDao pictureDao;

    @Override
    public List<Picture> getAll(String fileName) {
        return pictureDao.getAll(fileName);
    }

    @Override
    public int createOne(Picture picture) {
        pictureDao.createOne(picture);
        return picture.getId();
    }

    @Override
    public int update(int id, Picture picture) {
        return pictureDao.update(id, picture);
    }
}
