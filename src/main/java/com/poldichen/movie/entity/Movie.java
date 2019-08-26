package com.poldichen.movie.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author poldi.chen
 * @className Movie
 * @description TODO
 * @date 2019/8/26 14:14
 **/
public class Movie {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Date publishDate;
}
