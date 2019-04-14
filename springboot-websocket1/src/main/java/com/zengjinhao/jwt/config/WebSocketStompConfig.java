package com.zengjinhao.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * STOMP协议的WebStocket
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {


    /**
     * @EnableWebSocketMessageBroker注解表示开启使用STOMP协议来传输基于代理的消息，Broker就是代理的意思。
     * registerStompEndpoints方法表示注册STOMP协议的节点，并指定映射的URL
     * addEndpoint(“/simple”).withSockJS(); 用来注册STOMP协议节点，同时指定使用SockJS
     * configureMessageBroker方法用来配置消息代理，由于我们是实现推送功能，这里的消息代理是/topic
     * @param stompEndpointRegistry
     */


    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //注册STOMP协议的节点，并指定映射的URL
        stompEndpointRegistry.addEndpoint("/simple/info")
                .setAllowedOrigins("*") //解决跨域问题
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //来配置消息代理
        registry.enableSimpleBroker("/topic");
    }
}
