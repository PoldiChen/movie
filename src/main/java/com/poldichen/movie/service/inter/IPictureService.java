package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.Picture;

import java.util.List;

public interface IPictureService {

    int createOne(Picture picture);

    int update(int id, Picture picture);

    List<Picture> getAll(String fileName);
}
