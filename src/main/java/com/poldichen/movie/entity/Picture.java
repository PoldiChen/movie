package com.poldichen.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poldichen.movie.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author poldi.chen
 * @className Picture
 * @description TODO
 * @date 2020/2/24 22:10
 **/
public class Picture {

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
    private String url;

    @Getter
    @Setter
    private String extension;

    @Getter
    @Setter
    private int size;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date createAt;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date updateAt;
}
