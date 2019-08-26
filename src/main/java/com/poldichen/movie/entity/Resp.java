package com.poldichen.movie.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author poldi.chen
 * @className Resp
 * @description TODO
 * @date 2019/8/26 14:21
 **/
public class Resp {

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private Object data;

    public Resp() {
        this.code = 0;
        this.message = "OK";
    }
}
