package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.IUserDao;
import com.poldichen.movie.entity.User;
import com.poldichen.movie.service.inter.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author poldi.chen
 * @className UserServiceImpl
 * @description TODO
 * @date 2019/8/20 9:49
 **/
@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public int createOne(User user) {
        return userDao.createOne(user);
    }

    @Override
    public User getByName(String userName) {
        return userDao.getByName(userName);
    }
}
