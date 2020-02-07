package com.poldichen.movie.dao;

import com.poldichen.movie.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author poldi.chen
 * @className IUserDao
 * @description TODO
 * @date 2019/8/18 21:20
 **/
public interface IUserDao {

    int createOne(@Param("user") User user);

    User getByName(@Param("userName") String userName);

    int countUsers();

}
