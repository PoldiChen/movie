package com.poldichen.movie.filter;

import com.poldichen.movie.util.TokenUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 * @author zhaoxinguo on 2017/9/13.
 */

/**
 * @author poldi.chen
 * @className JwtAuthenticationFilter
 * @description TODO
 * @date 2019/8/20 10:13
 **/

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private String jwtPrefix = "marker";
    private String jwtSecret = "123456";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("JwtAuthenticationFilter@doFilterInternal");
        System.out.println(request.getRequestURL());
        String token = TokenUtil.getToken(request);
        System.out.println(token);
        if (token == null || !token.startsWith(jwtPrefix + "-")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    @Bean
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = TokenUtil.getToken(request);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token.replace(jwtPrefix + "-", ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

}
