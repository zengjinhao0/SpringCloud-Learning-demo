package com.zengjinhao;

import com.zengjinhao.pos.service.SenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitmqApplicationTests {

    @Autowired
    private SenderService senderService;


    //充当生产者发送消息到对应的消息队列中
    @Test
    public void testCache() {
        // 测试广播模式
        senderService.broadcast("同学们集合啦！");
        // 测试Direct模式
        senderService.direct("定点消息");
    }
}
