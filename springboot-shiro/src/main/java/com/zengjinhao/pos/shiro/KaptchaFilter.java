package com.zengjinhao.pos.shiro;

import com.zengjinhao.pos.dao.entity.ManagerInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Description: 验证码过滤器
 * 此过滤器已经在shiro中配置，这里不需要再次配置拦截路径
 */
public class KaptchaFilter extends FormAuthenticationFilter {

    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    private static final Logger _logger = LoggerFactory.getLogger(KaptchaFilter.class);

    //登录验证
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response)
            throws Exception {
        //拓展登陆验证字段
        ////从request中获取数据，然后实例化一个CaptchaUsernamePasswordToken
        CaptchaUsernamePasswordToken token = createToken(request, response);
        try {
            _logger.info("KaptchaFilter.executeLogin");
            /*图形验证码验证*/
            doCaptchaValidate((HttpServletRequest) request, token);
            //getSubject是FormAuthenticationFilter的方法，为了通过token获取一个subject
            Subject subject = getSubject(request, response);
            subject.login(token);//正常验证

            //到这里就算验证成功了,把用户信息放到session中
            ManagerInfo user = ShiroKit.getUser();
            ((HttpServletRequest) request).getSession().setAttribute("user", user);

            return onLoginSuccess(token, subject, request, response);

        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
        issueSuccessRedirect(request, response);
        //we handled the success redirect directly, prevent the chain from continuing:
        return false;
    }
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, "/", null, true);
    }

    // 验证码校验
    protected void doCaptchaValidate(HttpServletRequest request, CaptchaUsernamePasswordToken token) {
        _logger.info("KaptchaFilter.doCaptchaValidate");
        //session中的图形码字符串
        String captcha = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        _logger.info("session中的图形码字符串:" + captcha);

        //获取输入的验证码进行比对
        if (captcha == null || !captcha.equalsIgnoreCase(token.getCaptcha())) {
            //验证码错误，抛出异常
            throw new IncorrectCaptchaException();
        }
    }

    //从request中获取数据，然后实例化一个CaptchaUsernamePasswordToken
    @Override
    protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {

        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        //password是一个字符串数组
        return new CaptchaUsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    //保存异常对象到request
    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

}
