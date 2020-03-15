package com.poldichen.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poldichen.movie.common.Constants;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

/**
 * @author poldi.chen
 * @className Celebrity
 * @description TODO
 * @date 2020/2/18 22:07
 **/
public class Celebrity {

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
    private String englishName;

    @Getter
    @Setter
    private String gender;

    @Getter
    @Setter
    private String height;

    @Getter
    @Setter
    private String profession;

    @Getter
    @Setter
    private String birthDate;

    @Getter
    @Setter
    private String passAwayDate;

    @Getter
    @Setter
    private String age;

    @Getter
    @Setter
    private String constellation;

    @Getter
    @Setter
    private String nationality;

    @Getter
    @Setter
    private String birthPlace;

    @Getter
    @Setter
    private String hobby;

    @Getter
    @Setter
    private String description;

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

    /**
     * 关联属性
     */
    @Getter
    @Setter
    private List<Picture> covers;
}
