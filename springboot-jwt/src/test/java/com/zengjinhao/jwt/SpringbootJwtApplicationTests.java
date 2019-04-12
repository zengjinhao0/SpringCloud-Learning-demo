package com.zengjinhao.jwt;

import com.zengjinhao.jwt.common.util.JWTUtil;
import com.zengjinhao.jwt.shiro.ShiroKit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJwtApplicationTests {

    @Test
    public void testJwt() {
        String username = "admin";
        //随机数
        String salt = ShiroKit.getRandomSalt(16);
        //原密码
        String password = "12345678";
        String encodedPassword = ShiroKit.md5(password, username + salt);
        System.out.println("这个是保存进数据库的随机数:" + salt);
        System.out.println("这个是保存进数据库的加密后密码:" + encodedPassword);
        // 生成token
        String token = JWTUtil.sign(username, encodedPassword);
        System.out.println("token=" + token);
        // 验证token
        JWTUtil.verify(token, username, encodedPassword);
    }

}
