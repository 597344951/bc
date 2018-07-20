package com.zltel.broadcast;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

    private static final Logger logout = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void test() {
        logout.info("info msg");
        logout.error("error msg");
        logout.warn("warn msg");

        Object t = TimeUnit.DAYS.toMillis(1);
        logout.info(t.toString());
    }

}
