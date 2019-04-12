package com.zengjinhao.jwt.controller;

import com.zengjinhao.jwt.api.model.BaseResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * 机具管理API接口类
 */
@RestController
@RequestMapping(value = "/api/v1")
public class PublicController {

    private static final Logger _logger = LoggerFactory.getLogger(PublicController.class);

    /**
     * 入网绑定查询接口
     *
     * @return 是否入网
     */

    /**
     * /login
     * 登入
     * /article
     * 所有人都可以访问，但是用户与游客看到的内容不同
     * /require_auth
     * 登入的用户才可以进行访问
     * /require_role
     * admin的角色用户才可以登入
     * /require_permission
     * 拥有view和edit权限的用户才可以访问
     * @return
     */
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    @RequiresAuthentication
    public BaseResponse join(@RequestParam("imei") String imei) {
        _logger.info("入网查询接口 start... imei=");
        Subject subject = SecurityUtils.getSubject();
        BaseResponse result = null;
        if (subject.isAuthenticated()) {
            result = new BaseResponse(true, "You are already logged in", null);
        } else {
            result = new BaseResponse(true, "You are guest", null);
        }
        //_logger.info("入网查询接口 start... imei=" + imei);

        result.setSuccess(true);
        result.setMsg("已入网并绑定了网点");
        return result;
    }


    @GetMapping("/article")
    public BaseResponse article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new BaseResponse(true, "You are already logged in", null);
        } else {
            return new BaseResponse(true, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public BaseResponse requireAuth() {
        return new BaseResponse(true, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public BaseResponse requireRole() {
        return new BaseResponse(true, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public BaseResponse requirePermission() {
        return new BaseResponse(true, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse unauthorized() {
        return new BaseResponse(true, "Unauthorized", null);
    }

}
