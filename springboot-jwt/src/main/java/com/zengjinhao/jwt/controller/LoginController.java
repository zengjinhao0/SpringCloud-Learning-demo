package com.zengjinhao.jwt.controller;

import com.zengjinhao.jwt.api.model.BaseResponse;
import com.zengjinhao.jwt.api.model.LoginParam;
import com.zengjinhao.jwt.common.util.JWTUtil;
import com.zengjinhao.jwt.exception.UnauthorizedException;
import com.zengjinhao.jwt.model.ManagerInfo;
import com.zengjinhao.jwt.service.ManagerInfoService;
import com.zengjinhao.jwt.shiro.ShiroKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登录接口类
 */
@RestController
@RequestMapping(value = "/api")
public class LoginController {

    @Resource
    private ManagerInfoService managerInfoService;

    private static final Logger _logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestHeader(name="Content-Type", defaultValue = "application/json") String contentType,
                                      @RequestBody LoginParam loginParam) {
        _logger.info("用户请求登录获取Token");
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        ManagerInfo user = managerInfoService.findByUsername(username);
        //随机数盐
        String salt = user.getSalt();
        //原密码加密（通过username + salt作为盐）
        String encodedPassword = ShiroKit.md5(password, username + salt);
        if (user.getPassword().equals(encodedPassword)) {
            return new BaseResponse<>(true, "Login success", JWTUtil.sign(username, encodedPassword));
        } else {
            throw new UnauthorizedException();
        }
    }




}