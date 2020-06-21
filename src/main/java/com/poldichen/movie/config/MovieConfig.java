package com.poldichen.movie.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author poldi.chen
 * @className MovieConfig
 * @description TODO
 * @date 2020/6/21 21:08
 **/
@Component
public class MovieConfig {

    @Value("${movie.host}")
    @Getter
    @Setter
    private String host;

    @Value("${movie.url-resource}")
    @Getter
    @Setter
    private String urlResource;

}
