package com.zengjinhao.pos;

import com.zengjinhao.pos.common.dao.entity.User;
import com.zengjinhao.pos.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMultisourceApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(SpringbootMultisourceApplicationTests.class);

    @Resource
    private UserService userService;

    /**
     * 测试增删改查
     */
    @Test
    public void test() {
        // 核心数据库中的用户id=1
        User user = userService.findById(1);
        assertThat(user.getUsername(), is("admin"));

        // biz数据库中的用户id=1
        User user1 = userService.findById1(1);
        assertThat(user1.getUsername(), is("admin1"));
    }
}
