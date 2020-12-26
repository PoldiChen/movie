package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.IUserDao;
import com.poldichen.movie.entity.User;
import com.poldichen.movie.entity.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author poldi.chen
 * @className UserDetailsServiceImpl
 * @description TODO
 * @date 2019/8/20 10:30
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailServiceImpl@loadUserByUsername");
        System.out.println(username);
        User user = userDao.getByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDTO userDto = new UserDTO();
        userDto.setUserName(username);
        userDto.setPassword(user.getPassword());
        return userDto;
    }

}
