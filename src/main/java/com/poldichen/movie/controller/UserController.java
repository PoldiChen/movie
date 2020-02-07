package com.poldichen.movie.controller;

import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.entity.User;
import com.poldichen.movie.service.inter.IUserService;
import com.poldichen.movie.util.TokenUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author poldi.chen
 * @className UserController
 * @description TODO
 * @date 2019/8/10 21:37
 **/
@RestController
public class UserController {

    private String jwtPrefix = "marker";
    private String jwtSecret = "123456";

    @Autowired
    private IUserService userService;

    @RequestMapping(value="/user/current_user")
    public Resp currentUser(HttpServletRequest request) {
        System.out.println("UserController@currentUser");
        Resp resp = new Resp();

        String tokenStr = TokenUtil.getToken(request);
        System.out.println(tokenStr);

        if (tokenStr == null || tokenStr.equals("")) {
            resp.setCode(-1);
            resp.setMessage("Not login");
            return resp;
        }

        String userName
                = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(tokenStr.replace(jwtPrefix + "-", ""))
                .getBody()
                .getSubject();
        User user = userService.getByName(userName);
        user.setUserName(userName);
        resp.setData(user);


        return resp;
    }

}
