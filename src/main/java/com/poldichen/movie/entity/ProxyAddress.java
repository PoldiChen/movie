package com.poldichen.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poldichen.movie.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author poldi.chen
 * @className ProxyAddress
 * @description TODO
 * @date 2020/3/22 14:05
 **/
public class ProxyAddress {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String locate;

    @Getter
    @Setter
    private String protocol;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private int port;

    @Getter
    @Setter
    private String speed;

    @Getter
    @Setter
    private String connectTime;

    @Getter
    @Setter
    private String availableTime;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date verifyTime;

    @Getter
    @Setter
    private int available;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date createAt;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date updateAt;

}
