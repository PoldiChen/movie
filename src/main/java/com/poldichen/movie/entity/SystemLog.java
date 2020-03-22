package com.poldichen.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poldichen.movie.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author poldi.chen
 * @className SystemLog
 * @description TODO
 * @date 2020/3/8 10:18
 **/
public class SystemLog {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String logId;

    @Getter
    @Setter
    private String level;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String detail;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date createAt;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date updateAt;
}
