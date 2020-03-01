package com.poldichen.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poldichen.movie.common.Constants;
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
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private Date birthDate;

    @Getter
    @Setter
    private String nationality;

    @Getter
    @Setter
    private int search;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date createAt;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date updateAt;

    @Getter
    @Setter
    private List<Picture> covers;
}
