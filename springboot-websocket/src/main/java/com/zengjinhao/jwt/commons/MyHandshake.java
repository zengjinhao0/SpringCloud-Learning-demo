package com.zengjinhao.jwt.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Service
public class MyHandshake implements HandshakeInterceptor {
    //拦截器（握手）

    //初次握手访问前
    //握手之前干啥，常用来注册用户信息，绑定 WebSocketSession
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            // 从session中获取到当前登录的用户信息. 作为socket的账号信息. session的的WEBSOCKET_USERNAME信息,在用户打开页面的时候设置.
            String userName = (String) servletRequest.getSession().getAttribute("WEBSOCKET_USERNAME");
            attributes.put("WEBSOCKET_USERNAME", userName);
        }
        return true;
    }
    //初次握手访问后
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
