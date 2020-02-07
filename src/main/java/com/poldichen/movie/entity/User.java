package com.poldichen.movie.entity;

import lombok.*;

/**
 * @author poldi.chen
 * @className User
 * @description TODO
 * @date 2019/8/11 15:41
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String display;

    @Getter
    @Setter
    private String email;
}
