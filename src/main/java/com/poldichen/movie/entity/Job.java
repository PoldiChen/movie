package com.poldichen.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poldichen.movie.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author poldi.chen
 * @className Job
 * @description TODO
 * @date 2020/5/30 20:41
 **/
public class Job {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String jobName;

    @Getter
    @Setter
    private String className;

    @Getter
    @Setter
    private String arguments;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date createAt;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date updateAt;
}
