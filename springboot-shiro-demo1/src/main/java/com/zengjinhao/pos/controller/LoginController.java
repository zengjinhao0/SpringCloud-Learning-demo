package com.zengjinhao.pos.controller;


import com.zengjinhao.pos.config.properties.MyProperties;
import com.zengjinhao.pos.dao.entity.ManagerInfo;
import com.zengjinhao.pos.service.ManagerInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Description: 登录验证
 */

// 只用同时具有permission:view和permission:aix权限才能访问
//@RequiresPermissions(value={"permission:view","permission:aix"}, logical= Logical.AND)
//@RequiresPermissions(value={"permission:view","permission:aix"}, logical= Logical.OR)一个就行

@Controller
public class LoginController {

    @Resource
    private ManagerInfoService managerInfoService;
    @Resource
    private MyProperties myProperties;

    private static final Logger _logger = LoggerFactory.getLogger(LoginController.class);



    /**
     * 所有的访问都会经过login去验证
     * get请求，登录页面跳转
     * 登录页(shiro配置需要两个/login 接口,一个是get用来获取登陆页面,一个用post用于登录)
     * @return
     */
    @GetMapping("/login")
    public String login() {
        //如果已经认证通过，直接跳转到首页
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        return "login";
    }



    /**
     * post表单提交，登录
     * @param username
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/login")
    public Object login(String username, String password, Model model) {
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //shiro帮我们匹配密码什么的，我们只需要把东西传给它，它会根据我们在UserRealm里认证方法设置的来验证
            user.login(token);
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            //账号不存在和下面密码错误一般都合并为一个账号或密码错误，这样可以增加暴力破解难度
            model.addAttribute("msg", "账号不存在！");
        } catch (DisabledAccountException e) {
            model.addAttribute("msg", "账号未启用！");
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误！");
        } catch (Throwable e) {
            model.addAttribute("msg", "未知错误！");
        }
        return "login";
    }



    /**
     * 首页，并将登录用户的全名返回前台
     * @param model
     * @return
     */
    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {
        ManagerInfo sysUser = (ManagerInfo) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("userName", sysUser.getName());
        return "index";
    }

    /**
     * 欢迎页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/welcome")
    public String welcome(HttpServletRequest request, Model model) {
        return "modules/common/welcome";
    }


    /**
     * 退出
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "login";
    }



}
