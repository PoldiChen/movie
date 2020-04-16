package com.poldichen.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poldichen.movie.common.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author poldi.chen
 * @className SystemSetting
 * @description TODO
 * @date 2020/4/16 22:10
 **/
public class SystemSetting {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String settingKey;

    @Getter
    @Setter
    private String settingValue;

    @Getter
    @Setter
    private String comment;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date createAt;

    @Getter
    @Setter
    @JsonFormat(pattern = Constants.TIME_FORMAT, timezone = Constants.TIME_ZONE)
    private Date updateAt;
}
