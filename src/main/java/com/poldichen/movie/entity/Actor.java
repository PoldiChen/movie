package com.poldichen.movie.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author poldi.chen
 * @className Actor
 * @description TODO
 * @date 2020/2/18 22:07
 **/
public class Actor {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Date birthDate;

    @Getter
    @Setter
    private String nationality;

    @Getter
    @Setter
    private int search;

    @Getter
    @Setter
    private List<Cover> covers;
}
