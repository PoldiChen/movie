package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.User;

/**
 * @author poldi.chen
 * @className IUserService
 * @description TODO
 * @date 2019/8/20 9:49
 **/
public interface IUserService {

    public int createOne(User user);

    public User getByName(String userName);
}
