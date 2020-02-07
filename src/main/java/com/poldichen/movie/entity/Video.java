package com.poldichen.movie.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author poldi.chen
 * @className Video
 * @description TODO
 * @date 2020/2/7 15:08
 **/
public class Video {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int size;

    @Getter
    @Setter
    private String extension;

}
