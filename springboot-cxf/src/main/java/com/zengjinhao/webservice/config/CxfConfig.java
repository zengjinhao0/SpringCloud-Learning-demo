package com.zengjinhao.webservice.config;

import com.zengjinhao.webservice.service.ICommonService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * 默认服务在 Host:port/services/*** 路径下
 * 这里相当于把Commonservice接口发布在了路径/services/CommonService下
 * wsdl文档路径为http://localhost:8080/services/CommonService?wsdl
 *如果你想自定义wsdl的访问url，那么可以在application.yml中自定义：
 * cxf:
 *   path: /services  # 替换默认的/services路径
 */
@Configuration
public class CxfConfig {
    @Autowired
    private Bus bus;

    @Autowired
    ICommonService commonService;

    /**
     * JAX-WS
     **/
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, commonService);
        endpoint.publish("/CommonService");
        return endpoint;
    }
}
