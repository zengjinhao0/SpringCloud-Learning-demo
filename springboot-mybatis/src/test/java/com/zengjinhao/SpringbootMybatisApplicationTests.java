package com.zengjinhao;

import com.zengjinhao.pos.common.dao.entity.User;
import com.zengjinhao.pos.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Resource
    private UserService userService;

    /**
     * 测试增删改查
     */
    @Test
    public void test() {
        User user = new User();
        user.setUsername("xiaoxx");
        user.setName("小星星");
        user.setPassword("222222");
        user.setPhone("13890907676");
        userService.insertUser(user);

        User user1 = userService.findById(user.getId());
        assertThat(user1.getUsername(), is("xiaoxx"));
        assertThat(user1.getName(), is("小星星"));

        user1.setPassword("888888");
        userService.updateUser(user1);
        User user2 = userService.findById(user.getId());
        assertThat(user2.getPassword(), is("888888"));

        userService.deleteUser(user.getId());

        User user3 = userService.findById(user.getId());
        assertThat(user3, nullValue());

    }

}
