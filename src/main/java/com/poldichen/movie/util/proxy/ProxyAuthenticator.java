package com.poldichen.movie.util.proxy;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @author poldi.chen
 * @className ProxyAuthenticator
 * @description TODO
 * @date 2020/3/15 15:52
 **/
public class ProxyAuthenticator extends Authenticator {

    private String user, password;

    public ProxyAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }
}
