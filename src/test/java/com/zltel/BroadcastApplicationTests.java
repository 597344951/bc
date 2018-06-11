package com.zltel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 设置以随机端口启动,其他测试用例可直接继承此类,以自动注入Spring上下文对象.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BroadcastApplicationTests {
    
    public static final Logger logout = LoggerFactory.getLogger(BroadcastApplicationTests.class);

    @Test
    public void contextLoads() {}

    @Test
    public void others() {
    }

}
