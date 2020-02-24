package com.poldichen.movie.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author poldi.chen
 * @className Cover
 * @description TODO
 * @date 2020/2/24 22:10
 **/
public class Cover {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    private String fileName;

    @Getter
    @Setter
    private String extension;

    @Getter
    @Setter
    private int size;
}
