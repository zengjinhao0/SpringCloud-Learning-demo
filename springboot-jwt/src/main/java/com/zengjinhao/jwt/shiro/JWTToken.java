package com.zengjinhao.jwt.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

//
//public class JWTToken extends UsernamePasswordToken {
//
//    // 密钥
//    private String token;
//
//    public JWTToken(String username, char[] password,
//                    boolean rememberMe, String host,String token) {
//        super(username, password, rememberMe, host);
//        this.token = token;
//    }
//
//
//
//    @Override
//    public Object getPrincipal() {
//        return token;
//    }
//
//    @Override
//    public Object getCredentials() {
//        return token;
//    }
//}
