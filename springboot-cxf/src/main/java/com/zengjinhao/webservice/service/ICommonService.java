package com.zengjinhao.webservice.service;


import com.zengjinhao.webservice.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * ICommonService
 */
@WebService(name = "CommonService", // 暴露服务名称
        targetNamespace = "http://model.webservice.zengjinhao.com/"// 命名空间,一般是接口的包名倒序
)
public interface ICommonService {
    @WebMethod
//    @WebResult(name = "String", targetNamespace = "")
    public String sayHello(@WebParam(name = "userName") String name);

    @WebMethod
//    @WebResult(name = "String", targetNamespace = "")
    public User getUser(@WebParam(name = "userName") String name);
}
