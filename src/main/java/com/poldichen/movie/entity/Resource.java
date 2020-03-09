package com.poldichen.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poldichen.movie.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author poldi.chen
 * @className Resource
 * @description TODO
 * @date 2020/3/1 15:26
 **/
public class Resource {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String showName;

    @Getter
    @Setter
    private String size;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private Date shareDate;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date createAt;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date updateAt;

}
