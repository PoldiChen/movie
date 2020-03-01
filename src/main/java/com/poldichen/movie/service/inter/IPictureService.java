package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.entity.Resource;

public interface IPictureService {

    public int createOne(Picture picture);

    public int update(int id, Picture picture);
}
