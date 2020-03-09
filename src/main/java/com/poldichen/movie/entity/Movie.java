package com.poldichen.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poldichen.movie.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private String code;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private Date publishDate;

    @Getter
    @Setter
    private String length;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String director;

    @Getter
    @Setter
    private String producer;

    @Getter
    @Setter
    private String publisher;

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
    private List<Video> videos;

    @Getter
    @Setter
    private List<Actor> actors;

    @Getter
    @Setter
    private List<Picture> covers;

    @Getter
    @Setter
    private List<Picture> coverDetails;

    @Getter
    @Setter
    private List<Picture> screenshots;

    @Getter
    @Setter
    private List<Resource> resources;
}
